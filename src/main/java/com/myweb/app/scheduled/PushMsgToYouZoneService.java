package com.myweb.app.scheduled;

import com.myweb.app.dao.UserMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author weipan
 * @date 2019/8/22 16:51
 * @description
 */
@Service
@Log
public class PushMsgToYouZoneService {

  @Autowired
  private UserMapper userMapper;

  @Scheduled(cron = "*/5 * * * * ?")
  public void pushMsg(){
    System.out.println(userMapper.selectById(1));
    log.info("执行定时任务成功。。。。。。。。。。。。");
  }

}
