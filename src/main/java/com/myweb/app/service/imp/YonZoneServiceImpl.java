package com.myweb.app.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.myweb.app.config.AppConfig;
import com.myweb.app.core.ServiceException;
import com.myweb.app.dto.AuthTokenResponseDto;
import com.myweb.app.model.YonZoneMsgModel;
import com.myweb.app.service.YonZoneService;
import com.myweb.app.utils.Preconditions;
import com.myweb.app.utils.SignHelper;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * @author weipan
 * @date 2019/8/23 20:16
 * @description
 */
@Service
@Slf4j
public class YonZoneServiceImpl implements YonZoneService {

  @Autowired
  private AppConfig appConfig;

  @Value("${authtokenUrl}")
  private String authUrl;

  @Value("${notifyUrl")
  private String notifyUrl;

  @Autowired
  private RestTemplate restTemplate;

  @Override
  public String getAccessToken() {
    Map<String, Object> params = new HashMap<>();
    // 除签名外的其他参数
    params.put("appKey", appConfig.getKey());
    params.put("timestamp", String.valueOf(System.currentTimeMillis()));
    ;
    // 计算签名
    String signature = null;
    try {
      signature = SignHelper.sign(params, appConfig.getSecret());
    } catch (Exception e) {
      log.error("sign 签名错误 请重新生成 原因{}", e.getMessage());
      throw new ServiceException("签名成成错误");
    }
    params.put("signature", signature);

    String param = SignHelper.getSignatureContent(params);
    String url = appConfig.getHost() + authUrl + "?" + param;
    AuthTokenResponseDto data = null;
    try {
      JSONObject result = restTemplate.getForObject(url, JSONObject.class);
      data = (AuthTokenResponseDto) result.get("data");
    } catch (RestClientException e) {
      log.error("请求异常{}", e.getMessage());
      throw new ServiceException("授权令牌异常 请重新再试");
    }
    return data.getAccessToken();
  }

  @Override
  public String sendNotifyShare(YonZoneMsgModel model, String accessToken) {
    String url = appConfig.getHost() + notifyUrl + "?access_token=" + accessToken;
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity =
        new HttpEntity<>(JSON.toJSONString(model),headers);
    ResponseEntity<String> exchange = restTemplate
        .exchange(url, HttpMethod.POST, entity, String.class);
    Preconditions.checkNotNullOrBlank(exchange.getBody(), "请求失败 重新再试");
    return null;
  }



}
