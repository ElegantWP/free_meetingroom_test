package com.myweb.app.rabbitmq.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myweb.app.config.AppConfig;
import com.myweb.app.dto.PushedListDto;
import com.myweb.app.entity.MeetingRoom;
import com.myweb.app.model.AppCodeMsgModel;
import com.myweb.app.model.YonZoneMsgModel;
import com.myweb.app.service.MeetingRoomService;
import com.myweb.app.service.imp.YonZoneServiceImpl;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;


@Slf4j
public class BaseMsgConsumer implements ChannelAwareMessageListener {


	@Autowired
	MeetingRoomService meetingRoomService;

	@Autowired
	YonZoneServiceImpl yonZoneService;

	@Autowired
	private AppConfig appConfig;

	/**
	 * 端消息接收者，消费消息队列中推送消息，调用个推接口向前端App发送，发送成功后持久化存储（异步）
	 */
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		try {
			String body = new String(message.getBody());
			ObjectMapper mapper = new ObjectMapper();
			PushedListDto pushedListDto = mapper.readValue(body, PushedListDto.class);
			log.info("receive msg : " + body);
			pushedListDto.setIsRead(0);// 是否已读 0：未读，1：已读
			//消费消息
			Integer roomId = pushedListDto.getRoomId();
			String userId = pushedListDto.getUserId();
			String accesstoken = pushedListDto.getAccesstoken();
			String tenantId = pushedListDto.getTenantId();

			MeetingRoom meetingRoom = meetingRoomService.selectById(roomId);
			if(meetingRoom.getState() == 5){//会议室状态仍然为占用
				meetingRoomService.release(roomId);//释放会议室

				AppCodeMsgModel servicesMsgList = yonZoneService
						.getServicesMsgList(accesstoken, appConfig.getCode());
				YonZoneMsgModel model = new YonZoneMsgModel();
				model.setTitle("通知");
				model.setAppId(servicesMsgList.getYkjId());
				model.setContent("由于您未在15分钟内使用会议室，现在会议室已被释放。");
				model.setTenantId(tenantId);
				model.setYhtUserIds(Arrays.asList(userId));
				model.setSendScope("list");

				yonZoneService.sendNotifyShare(model,accesstoken);
			}

			System.out.println(pushedListDto.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 发送回执
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 确认消息成功消费
	}

}
