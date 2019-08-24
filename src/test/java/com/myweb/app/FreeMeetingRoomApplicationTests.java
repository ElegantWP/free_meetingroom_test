package com.myweb.app;

import com.myweb.app.dao.UserMapper;
import com.myweb.app.entity.User;
import com.myweb.app.service.YonZoneService;
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

}