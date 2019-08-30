package com.myweb.app;

import com.myweb.app.config.AppConfig;
import com.myweb.app.dao.UserMapper;
import com.myweb.app.entity.User;
import com.myweb.app.model.AppCodeMsgModel;
import com.myweb.app.model.YonZoneMsgModel;
import com.myweb.app.service.YonZoneService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FreeMeetingRoomApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private YonZoneService yonZoneService;

	@Autowired
	private AppConfig appConfig;


	@Test
	public void contextLoads() {
		User user = new User();
		user.setSex(12);
		userMapper.insert(user);
	}

	@Test
	public void testAccessToken(){
		String accessToken = yonZoneService.getAccessToken();
	}

	@Test
	public void testSendMsg(){
	  String accesstoken = yonZoneService.getAccessToken();
		AppCodeMsgModel servicesMsgList = yonZoneService
				.getServicesMsgList(accesstoken, appConfig.getCode());
		YonZoneMsgModel model = new YonZoneMsgModel();
		model.setTitle("测试工作通知");
		model.setAppId(servicesMsgList.getYkjId());
		model.setContent("双立人****测试工作通知******测试工作通知");
		model.setTenantId("l6l5x0gg");
		model.setUrl("www.baidu.comn");
		model.setYhtUserIds(Arrays.asList("b-49f2-b93e-9e3d0ba1d0d3"));
    model.setSendScope("list");
    yonZoneService.sendNotifyShare(model, accesstoken);
  }

  @Test
	public void testAppcode(){
		yonZoneService.
				getServicesMsgList(yonZoneService.getAccessToken(),appConfig.getCode());
	}

	@Test
	public void testGetUse(){
		yonZoneService.getUserContentList(yonZoneService.getAccessToken());
	}

	@Test
	public void testLamdba(){
		List<User> users = userMapper.selectList(null);
		List<String> results = users.stream().distinct().filter(item -> null != item.getNickName())
				.map(User::getNickName)
				.collect(Collectors.toList());
		List<String> collect =
				users.stream().distinct().filter(item -> null != item.getPassword())
						.map(User::getPassword).collect(Collectors.toList());
		results.addAll(collect);
		System.out.println(results);
		results.stream().distinct();
		System.out.println(results);
	}
}
