package com.myweb.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
		List<User> users = userMapper.selectList(null);
		System.out.println(users);
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		int page = 1;
		int pageSize = 4;
		IPage<User> pageUsers = userMapper.selectPage(new Page<>(page, pageSize)
				, new QueryWrapper<User>().eq("email","1"));
		System.out.println(pageUsers);
		System.out.println(pageUsers.getRecords());
		System.out.println(pageUsers.getSize());
		System.out.println(pageUsers.getCurrent());
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
		List<String> results = users.stream().distinct().filter(item -> null != item.getUnknown())
				.map(User::getUnknown)
				.collect(Collectors.toList());
		List<String> collect =
				users.stream().distinct().filter(item -> null != item.getUserCode())
						.map(User::getUserCode).collect(Collectors.toList());
		results.addAll(collect);
		System.out.println(results);
		results.stream().distinct();
		System.out.println(results);
	}
}
