package com.myweb.app.scheduled;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.myweb.app.dao.UserMapper;
import com.myweb.app.entity.User;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author weipan
 * @date 2019/8/22 16:51
 * @description
 */
@Service
@Slf4j
public class PushMsgToYouZoneService {

  @Autowired
  private UserMapper userMapper;

  @Scheduled(cron = "*/5 * * * * ?")
  public void pushMsg(){
//    List<User> username = userMapper.selectList(new QueryWrapper<User>()
//        .eq("username", "4@qq.com"));
    log.info("执行定时任务成功。。。。。。。。。。。。");
  }

}
