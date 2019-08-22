package com.myweb.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author weipan
 * @date 2019/8/22 18:44
 * @description
 */
@Configuration
public class RestTemplateConfig {


  @Bean
  public RestTemplate getTemplate(){
    return new RestTemplate();
  }
}
