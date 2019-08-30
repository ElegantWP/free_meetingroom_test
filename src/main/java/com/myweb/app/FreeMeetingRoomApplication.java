package com.myweb.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author weipan
 */
@SpringBootApplication
@EnableScheduling
@EnableCaching
@MapperScan(basePackages = {"com.myweb.app.dao"})
public class FreeMeetingRoomApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreeMeetingRoomApplication.class, args);
	}

}
