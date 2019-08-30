package com.myweb.app.utils;

import com.myweb.app.core.ServiceException;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.CollectionUtils;

/**
 * bean工具类
 */
@Slf4j
public class BeanUtils {

  /**
   * <pre>
   *     List<UserBean> userBeans = userDao.queryUsers();
   *     List<UserDTO> userDTOs = BeanUtil.batchTransform(UserDTO.class, userBeans);
   * </pre>
   */
  public static <T> List<T> batchTransform(final Class<T> clazz, List<? extends Object> srcList) {
    if (CollectionUtils.isEmpty(srcList)) {
      return Collections.emptyList();
    }

    List<T> result = new ArrayList<>(srcList.size());
    for (Object srcObject : srcList) {
      result.add(transfrom(clazz, srcObject));
    }
    return result;
  }

  /**
   * 封装{@link
   * org.springframework.beans.BeanUtils}
   * ，惯用与直接将转换结果返回
   *
   * <pre>
   *      UserBean userBean = new UserBean("username");
   *      return BeanUtil.json(UserDTO.class, userBean);
   * </pre>
   */
  public static <T> T transfrom(Class<T> clazz, Object src) {
    if (src == null) {
      return null;
    }
    T instance;
    try {
      instance = clazz.newInstance();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new ServiceException(e.getMessage(), e);
    }
    org.springframework.beans.BeanUtils.copyProperties(src, instance, getNullPropertyNames(src));
    return instance;
  }

  public static void copyProperties(Object src, Object target){
    if (src == null) {
      return;
    }
    org.springframework.beans.BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
  }


  private static String[] getNullPropertyNames(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    PropertyDescriptor[] pds = src.getPropertyDescriptors();

    Set<String> emptyNames = new HashSet<String>();
    for (PropertyDescriptor pd : pds) {
      Object srcValue = src.getPropertyValue(pd.getName());
      if (srcValue == null) {
        emptyNames.add(pd.getName());
      }
    }
    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }
}
