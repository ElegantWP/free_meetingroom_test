package com.myweb.app.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.myweb.app.config.AppConfig;
import com.myweb.app.core.ServiceException;
import com.myweb.app.dto.AuthTokenResponseDto;
import com.myweb.app.dto.YouZoneUser;
import com.myweb.app.model.AppCodeMsgModel;
import com.myweb.app.model.RequestUserModel;
import com.myweb.app.model.UserContent;
import com.myweb.app.model.YonZoneMsgModel;
import com.myweb.app.service.YonZoneService;
import com.myweb.app.utils.HttpClientUtil;
import com.myweb.app.utils.Preconditions;
import com.myweb.app.utils.SignHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author weipan
 * @date 2019/8/23 20:16
 * @description
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "DIWORK")
public class YonZoneServiceImpl implements YonZoneService {

  @Autowired
  private AppConfig appConfig;

  @Value("${authtokenUrl}")
  private String authUrl;

  @Value("${notifyUrl}")
  private String notifyUrl;

  @Autowired
  private RestTemplate restTemplate;

  private static final String ACCESS_KEY = "access:key";

  private static final String GET_CODE_MSG = "/open/diwork/app/list_by_app_code";

  private static final String GET_USER_LIST = "/open/diwork/users/user_page_list";

  private final static String URL_FREE_LOGIN = "/open/diwork/freelogin/base_info_by_code";
  private static final String USER_INDEX = "1";

  private static final String USER_SIZE = "20";

  @Override
  @Cacheable(value = ACCESS_KEY, unless = "#result == null ")
  public String getAccessToken() {
    log.info("从网页中获取ACCESS KEY");
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
    AuthTokenResponseDto responseDto = new AuthTokenResponseDto();
    try {
      String result = HttpClientUtil.get(appConfig.getHostAuth() + authUrl, params);
      JSONObject jsonObject = JSON.parseObject(result);
      Preconditions.checkNotNull(jsonObject.get("data"),"返回token数据为空");
      responseDto =
          JSONObject.toJavaObject((JSON) jsonObject.get("data"),AuthTokenResponseDto.class);
    } catch (Exception e) {
      log.error("请求异常{}", e.getMessage());
      throw new ServiceException("授权令牌异常 请重新再试");
    }
    return responseDto.getAccessToken();
  }

  @Override
  public ResponseEntity<String> sendNotifyShare(YonZoneMsgModel model, String accessToken) {
    String url = appConfig.getHost() + notifyUrl + "?access_token=" + accessToken;
    ResponseEntity<String> result = null;
    try {
      result = restTemplate.postForEntity(url,model,String.class);
      log.info("测试工作通知");
    } catch (Exception e) {
      log.error("工作通知推送异常  原因{}",e.getMessage());
      throw new ServiceException("推送异常 请稍后再试");
    }
    log.info("sendNotifyShare result:{}", result);
    return result;
  }

  @Override
  public AppCodeMsgModel getServicesMsgList(String token, String appcode) {
    String suffix = String.format("?access_token=%s&appCode=%s",token,appcode);
    String url = appConfig.getHost()+GET_CODE_MSG+suffix;
    List<AppCodeMsgModel> datas = null;
    try {
      String result = restTemplate.getForObject(url, String.class);
      JSONObject jsonObject = JSON.parseObject(result);
      Preconditions.checkNotNull(jsonObject.get("data"),"返回数据为空");
      datas =
          JSONObject.parseArray(jsonObject.get("data").toString(), AppCodeMsgModel.class);
      Preconditions.checkNotEmpty(datas,"异常返回数据");
    } catch (Exception e) {
      log.error("获取应用的APPcode异常 请稍后再试");
      throw new ServiceException("获取应用的APPID异常 联系管理员重试");
    }
    return datas.get(0);
  }

  @Override
  public List<UserContent> getUserContentList(String accessToken) {
    String url = appConfig.getHost()+GET_USER_LIST+"?access_token="+accessToken;
    RequestUserModel requestUserModel =
        RequestUserModel.builder()
            .accessToken(accessToken)
            .index(USER_INDEX)
            .size(USER_SIZE)
            .build();
    List<UserContent> contents =null;
    try {
      String result = restTemplate.postForObject(url, requestUserModel, String.class);
      JSONObject jsonObject = JSON.parseObject(result);
      JSONObject data = (JSONObject) jsonObject.get("data");
      contents = JSONObject
          .parseArray(data.get("content").toString(), UserContent.class);
    } catch (Exception e) {
      log.error("处理数据异常 请重新再试 原因{}",e.getMessage());
      throw new ServiceException("返回租户可用的用户异常 请重新再试");
    }
    return contents;
  }

  @Override
  public YouZoneUser getUserContent(String code, String accessToken) {
    Map<String, Object> params = new HashMap<>();
    params.put("access_token", accessToken);
    params.put("code", code);
    String result = null;
    try {
      result = HttpClientUtil.get(appConfig.getHost() + URL_FREE_LOGIN, params);
    } catch (Exception e) {
      log.error("获取失败",e.getMessage());
      throw new RuntimeException("失败");
    }
    JSONObject jsonObject = JSON.parseObject(result);
    YouZoneUser data = JSONObject.parseObject(jsonObject.get("data").toString(), YouZoneUser.class);
    log.info("用户数据{}", data);
    return data;
  }


}
