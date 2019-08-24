package com.myweb.app.config;

import static com.myweb.app.core.ProjectConstant.DEVELOPMENT;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.myweb.app.core.Result;
import com.myweb.app.core.ResultCode;
import com.myweb.app.core.ServiceException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author weipan
 * @date 2019/8/20 21:21
 * @description
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

  private final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);


  /**
   * 当前激活的配置文件
   */
  @Value("${spring.profiles.active}")
  private String env;

  /**
   * @param converters
   *
   * 使用阿里 FastJson 作为JSON MessageConverter
   */
  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
    FastJsonConfig config = new FastJsonConfig();
    //升级最新版本需加=============================================================
    List<MediaType> supportedMediaTypes = new ArrayList<>();
    supportedMediaTypes.add(MediaType.APPLICATION_JSON);
    supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
    supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
    supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
    supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
    supportedMediaTypes.add(MediaType.APPLICATION_PDF);
    supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
    supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
    supportedMediaTypes.add(MediaType.APPLICATION_XML);
    supportedMediaTypes.add(MediaType.IMAGE_GIF);
    supportedMediaTypes.add(MediaType.IMAGE_JPEG);
    supportedMediaTypes.add(MediaType.IMAGE_PNG);
    supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
    supportedMediaTypes.add(MediaType.TEXT_HTML);
    supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
    supportedMediaTypes.add(MediaType.TEXT_PLAIN);
    supportedMediaTypes.add(MediaType.TEXT_XML);
    converter.setSupportedMediaTypes(supportedMediaTypes);
    //保留空的字段
    config.setSerializerFeatures(SerializerFeature.WriteMapNullValue,
        //String null -> ""
        SerializerFeature.WriteNullStringAsEmpty,
        //Number null -> 0
        SerializerFeature.WriteNullNumberAsZero);
    converter.setFastJsonConfig(config);
    converter.setDefaultCharset(Charset.forName("UTF-8"));
    converters.add(converter);
  }


  /**
   * @param exceptionResolvers
   *
   * 统一异常处理
   */
  @Override
  public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
    exceptionResolvers.add(new HandlerExceptionResolver() {
      @Override
      public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        Result result = new Result();
        //业务失败的异常，如“账号或密码错误”
        if (e instanceof ServiceException) {
          result.setCode(ResultCode.FAIL).setMessage(e.getMessage());
          logger.info(e.getMessage());
        } else if (e instanceof NoHandlerFoundException) {
          result.setCode(ResultCode.NOT_FOUND).setMessage("接口 [" + request.getRequestURI() + "] 不存在");
        } else if (e instanceof ServletException) {
          result.setCode(ResultCode.FAIL).setMessage(e.getMessage());
        } else {
          result.setCode(ResultCode.INTERNAL_SERVER_ERROR).setMessage("接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员");
          String message;
          if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                request.getRequestURI(),
                handlerMethod.getBean().getClass().getName(),
                handlerMethod.getMethod().getName(),
                e.getMessage());
          } else {
            message = e.getMessage();
          }
          logger.error(message, e);
        }
        responseResult(response, result);
        return new ModelAndView();
      }

    });
  }

  /**
   * @param registry
   *
   * 解决跨域问题
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    //暂不考虑
  }

  /**
   * 添加拦截器
   * @param registry
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    //接口签名认证拦截器，该签名认证比较简单，实际项目中可以使用Json Web Token或其他更好的方式替代。
    //开发环境忽略签名认证
    if (!DEVELOPMENT.equals(env)) {
      registry.addInterceptor(new HandlerInterceptorAdapter() {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
          //验证签名
          boolean pass = validateSign(request);
          if (pass) {
            return true;
          } else {
            logger.warn("签名认证失败，请求接口：{}，请求IP：{}，请求参数：{}",
                request.getRequestURI(), getIpAddress(request), JSON.toJSONString(request.getParameterMap()));

            Result result = new Result();
            result.setCode(ResultCode.UNAUTHORIZED).setMessage("签名认证失败");
            responseResult(response, result);
            return false;
          }
        }
      });
    }
  }

  /**
   *@param:
   *@Description:axios的Post请求处理方案
   *@Date:13:10 2018/5/30
   */
  private void responseResult(HttpServletResponse response, Result result) {
    response.setCharacterEncoding("UTF-8");
    response.setHeader("Content-type", "application/json;charset=UTF-8");
    response.setStatus(200);
    try {
      response.getWriter().write(JSON.toJSONString(result));
    } catch (IOException ex) {
      logger.error(ex.getMessage());
    }
  }

  /**
   *
   * 暂时不做使用  接口的签名验证
   * 一个简单的签名认证，规则：
   * 1. 将请求参数按ascii码排序
   * 2. 拼接为a=value&b=value...这样的字符串（不包含sign）
   * 3. 混合密钥（secret）进行md5获得签名，与请求的签名进行比较
   */
  private boolean validateSign(HttpServletRequest request) {
    String requestSign = request.getParameter("sign");//获得请求签名，如sign=19e907700db7ad91318424a97c54ed57
    if (StringUtils.isEmpty(requestSign)) {
      return false;
    }
    List<String> keys = new ArrayList<String>(request.getParameterMap().keySet());
    keys.remove("sign");//排除sign参数
    Collections.sort(keys);//排序

    StringBuilder sb = new StringBuilder();
    for (String key : keys) {
      sb.append(key).append("=").append(request.getParameter(key)).append("&");//拼接字符串
    }
    String linkString = sb.toString();
    linkString = StringUtils.substring(linkString, 0, linkString.length() - 1);//去除最后一个'&'

    String secret = "Potato";//密钥，自己修改
    String sign = DigestUtils.md5Hex(linkString + secret);//混合密钥md5

    return StringUtils.equals(sign, requestSign);//比较
  }

  private String getIpAddress(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    // 如果是多级代理，那么取第一个ip为客户端ip
    if (ip != null && ip.indexOf(",") != -1) {
      ip = ip.substring(0, ip.indexOf(",")).trim();
    }

    return ip;
  }


  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    if (DEVELOPMENT.equals(env)) {
      registry.addResourceHandler("swagger-ui.html")
          .addResourceLocations("classpath:/META-INF/resources/");
      registry.addResourceHandler("/webjars/**")
          .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
  }

}
