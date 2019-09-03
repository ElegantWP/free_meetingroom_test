package com.myweb.app.scheduled;

import com.myweb.app.config.AppConfig;
import com.myweb.app.dao.MeetingRecordMapper;
import com.myweb.app.entity.MeetingRecord;
import com.myweb.app.model.AppCodeMsgModel;
import com.myweb.app.model.YonZoneMsgModel;
import com.myweb.app.service.YonZoneService;
import com.myweb.app.utils.Preconditions;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author weipan
 * @date 2019/8/22 16:51
 * @description
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "DIWORK")
@Component
public class PushMsgToYouZoneService {

  @Autowired
  private MeetingRecordMapper meetingRecordMapper;

  @Autowired
  private YonZoneService yonZoneService;

  @Autowired
  private AppConfig appConfig;

  @Autowired
  private ApplicationContext context;

  /**
   * 会议开始15分钟发送消息
   */
  @Scheduled(cron = "*/5 * * * * ?")
  public void pushMsg(){
    //重新 获取本类对象 防止AOP无法引入切面
    List<MeetingRecord> allMeetingRecord = context.getBean(PushMsgToYouZoneService.class).getAllMeetingRecord();
    log.info("执行定时任务成功。。。。。。。。。。。。从缓存中获取数据");
    for (MeetingRecord meetingRecord : allMeetingRecord){
      //日期相同 设置时间 其实时间相同发送消息
      Preconditions.checkNotNullOrBlank(meetingRecord.getReservationTime(),"预定时间为空");
      Preconditions.checkNotNullOrBlank(meetingRecord.getStartTime(),"起始时间为空");
      //时间维度进行判断
      log.info("会议的通知时间为{}",generateTimePeriod(meetingRecord.getReservationTime(),meetingRecord.getStartTime()));
      log.info("现在的时间{}",getNowDateMMSS());
      log.info("是否相等{}",
          generateTimePeriod(meetingRecord.getReservationTime(),meetingRecord.getStartTime()).equals(getNowDateMMSS()));
      if (generateTimePeriod(meetingRecord.getReservationTime(),meetingRecord.getStartTime()).equals(getNowDateMMSS())){
        String accesstoken = yonZoneService.getAccessToken();
        AppCodeMsgModel servicesMsgList = yonZoneService
            .getServicesMsgList(accesstoken, appConfig.getCode());
        log.info("执行当前任务的编号{}",meetingRecord);
        YonZoneMsgModel model = new YonZoneMsgModel();
        model.setTitle(meetingRecord.getTopic());
        model.setAppId(servicesMsgList.getYkjId());
        model.setContent(meetingRecord.getMeetingContent());
        model.setTenantId("l6l5x0gg");
        //model.setUrl("暂无");
        model.setYhtUserIds(Arrays.asList(meetingRecord.getCreatorId()));
        model.setSendScope("list");
        yonZoneService.sendNotifyShare(model,accesstoken);
      }
    }
  }

  /**
   * 直接调用此方法会导致无法再次生成切面
   * 原因Spring的动态代理的差异
   * @return
   */
  @Cacheable(value = "record", unless = "#result == null ")
  public List<MeetingRecord> getAllMeetingRecord(){
    log.info("从数据库获取数据");
    return meetingRecordMapper.selectList(null);
  }



  public static String getNowDateMMSS(){
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    return formatter.format(new Date());
  }

  public static String generateTimePeriod( String activity, String startTime){
    //将前后时间先拼成两个yyyy-MM-dd HH:mm 格式的字符串
    String frontTime1 = activity + " " + startTime.replace(".", ":");

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");

    //转换成date
    Date date1 = null;

    try {
      date1 = format.parse(frontTime1);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    //减去15分钟
    Calendar calendar1 = Calendar.getInstance();

    calendar1.setTime(date1);

    calendar1.add(Calendar.MINUTE,-15);


    date1 = calendar1.getTime();

    //将减去15分钟的时间转回yyyy-MM-dd HH:mm格式的字符串
    String after1 = format.format(date1);

    return after1;
  }

}
