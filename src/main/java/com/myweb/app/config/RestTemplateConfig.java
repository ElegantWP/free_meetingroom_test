package com.myweb.app.config;

import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

/**
 * @author weipan
 * @date 2019/8/22 18:44
 * @description
 */
@Configuration
public class RestTemplateConfig {


  public static final int READ_TIMEOUT_60 = 60;

  public static final int CONN_TIMEOUT_5 = 5;

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder
        .setReadTimeout(Duration.ofSeconds(READ_TIMEOUT_60))
        .setConnectTimeout(Duration.ofSeconds(CONN_TIMEOUT_5))
        .build();
  }
}
