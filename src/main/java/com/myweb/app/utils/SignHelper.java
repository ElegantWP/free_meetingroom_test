package com.myweb.app.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 请求开放平台套件授权相关接口的加签类
 */
public class SignHelper {

  /**
   * 按参数名排序后依次拼接参数名称与数值，之后对该字符串使用 HmacSHA256 加签，加签结果进行 base 64 返回
   *
   * @param params 请求参数 map
   * @param suiteSecret 套件密钥，用作 mac key
   * @return 签名
   */
  public static String sign(Map<String, Object> params, String suiteSecret)
      throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
    // use tree map to sort params by name
    Map<String, Object> treeMap;
    if (params instanceof TreeMap) {
      treeMap = params;
    } else {
      treeMap = new TreeMap<>(params);
    }

    StringBuilder stringBuilder = new StringBuilder();
    for (Map.Entry<String, Object> entry : treeMap.entrySet()) {
      stringBuilder.append(entry.getKey()).append(entry.getValue());
    }

    Mac mac = Mac.getInstance("HmacSHA256");
    mac.init(new SecretKeySpec(suiteSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
    byte[] signData = mac.doFinal(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
    String base64String = Base64.getEncoder().encodeToString(signData);
    return URLEncoder.encode(base64String, "UTF-8");
  }


}
