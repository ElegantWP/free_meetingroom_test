package com.myweb.app.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author weipan
 * @date 2019/8/23 19:53
 * @description app配置相关信息
 */
@Getter
@Setter
@Component
@ConfigurationProperties("app")
public class AppConfig {


  private String hostAuth;

  private String host;

  private String key;

  private String secret;

  private String code;

  private String tenantId;
}
