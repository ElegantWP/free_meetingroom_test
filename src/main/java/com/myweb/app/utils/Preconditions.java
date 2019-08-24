package com.myweb.app.utils;

import com.myweb.app.core.ServiceException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @author weipan
 * @date 2019/8/24 20:17
 * @description
 */
public final class Preconditions {

  private Preconditions() {
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the calling method.
   *
   * @param expression a boolean expression
   * @param errorMessage the exception message to use if the check fails
   * @throws ServiceException if {@code expression} is false
   */
  public static void checkArgument(boolean expression, String errorMessage) {
    if (!expression) {
      throw new ServiceException(errorMessage);
    }
  }

  /**
   * 判断两个对象是否equals
   *
   * @param object1
   * @param object2
   * @param errorMessage
   * @author
   */
  public static void checkEquals(Object object1, Object object2, String errorMessage) {
    if (Objects.equals(object1, object2)) {
      return;
    }
    throw new ServiceException(errorMessage);
  }

  /**
   * 判断两个对象是否非equals
   *
   * @param object1
   * @param object2
   * @param errorMessage
   * @author
   */
  public static void checkNotEquals(Object object1, Object object2, String errorMessage) {
    if (!Objects.equals(object1, object2)) {
      return;
    }
    throw new ServiceException(errorMessage);
  }

  /**
   * 检查对象是否在给定的对象列表当中
   *
   * @param object1
   * @param errorMessage
   * @param objects
   */
  public static void checkObjectInList(Object object1, String errorMessage, Object... objects) {
    for (Object object : objects) {
      if (Objects.equals(object1, object)) {
        return;
      }
    }
    throw new ServiceException(errorMessage);
  }

  /**
   * Ensures the obj is not null involving one or more parameters to the calling method.
   *
   * @param t the obj to be check
   * @param errorMessage the exception message to use if the check fails
   * @throws ServiceException if {@code expression} is false
   */
  public static <T> T checkNotNull(T t, String errorMessage) {
    if (t == null) {
      throw new ServiceException(errorMessage);
    }
    if (t instanceof String) {
      if (isBlank(t.toString())) {
        throw new ServiceException(errorMessage);
      }
    }
    return t;
  }

  /**
   * Ensures the obj is not null involving one or more parameters to the calling method.
   *
   * @param t the obj to be check
   * @param errorMessage the exception message to use if the check fails
   * @throws ServiceException if {@code expression} is false
   */
  public static <T> T checkNotNull(Optional<T> t, String errorMessage) {
    if (!t.isPresent()) {
      throw new ServiceException(errorMessage);
    }
    if (t.get() instanceof String) {
      if (isBlank(t.get().toString())) {
        throw new ServiceException(errorMessage);
      }
    }
    return t.get();
  }


  /**
   * Ensures the obj is null involving one or more parameters to the calling method.
   *
   * @param t the obj to be check
   * @param errorMessage the exception message to use if the check fails
   * @throws ServiceException if {@code expression} is false
   */
  public static <T> void checkNull(T t, String errorMessage) {
    if (t != null) {
      throw new ServiceException(errorMessage);
    }
  }


  /**
   * Ensures the obj is  null involving one or more parameters to the calling method.
   *
   * @param t the obj to be check
   * @param errorMessage the exception message to use if the check fails
   * @throws ServiceException if {@code expression} is false
   */
  public static <T> void checkNull(Optional<T> t, String errorMessage) {
    if (t.isPresent()) {
      throw new ServiceException(errorMessage);
    }
  }

  /**
   * Checks if a CharSequence is not empty (""), null and whitespace only.
   *
   * @param str the string to be check
   * @param errorMessage the exception message to use if the check fails
   * @throws ServiceException if {@code expression} is false
   */
  public static String checkNotNullOrBlank(String str, String errorMessage) {
    if (isBlank(str)) {
      throw new ServiceException(errorMessage);
    }
    return str;
  }

  /**
   * Checks if a CharSequence is not empty (""), null and whitespace only.
   *
   * @param str the string to be check
   * @param errorMessage the exception message to use if the check fails
   * @throws ServiceException if {@code expression} is false
   */
  public static String checkNotNullOrBlank(Optional<String> str, String errorMessage) {
    if (!str.isPresent() || isBlank(str.get())) {
      throw new ServiceException(errorMessage);
    }
    return str.get();
  }


  /**
   * Checks if a CharSequence is  empty (""), null or whitespace only.
   *
   * @param str the string to be check
   * @param errorMessage the exception message to use if the check fails
   * @throws ServiceException if {@code expression} is false
   */
  public static void checkNullOrBlank(String str, String errorMessage) {
    if (!isBlank(str)) {
      throw new ServiceException(errorMessage);
    }
  }

  /**
   * Checks if a CharSequence is  empty (""), null or whitespace only.
   *
   * @param str the string to be check
   * @param errorMessage the exception message to use if the check fails
   * @throws ServiceException if {@code expression} is false
   */
  public static void checkNullOrBlank(Optional<String> str, String errorMessage) {
    if (str.isPresent() && isBlank(str.get())) {
      throw new ServiceException(errorMessage);
    }
  }


  /**
   * Checks if a list is not empty , null.
   *
   * @param list the list to be check
   * @param errorMessage the exception message to use if the check fails
   * @throws ServiceException if {@code expression} is false
   */
  public static <T> List<T> checkNotEmpty(List<T> list, String errorMessage) {
    if (list == null || list.isEmpty()) {
      throw new ServiceException(errorMessage);
    }
    return list;
  }


  /**
   * Checks if a list is not empty , null.
   *
   * @param list the list to be check
   * @param errorMessage the exception message to use if the check fails
   * @throws ServiceException if {@code expression} is false
   */
  public static <T> List<T> checkNotEmpty(Optional<List<T>> list, String errorMessage) {
    if (!list.isPresent() || list.get().isEmpty()) {
      throw new ServiceException(errorMessage);
    }
    return list.get();
  }


  /**
   * Checks if a list is  empty , null.
   *
   * @param list the list to be check
   * @param errorMessage the exception message to use if the check fails
   * @throws ServiceException if {@code expression} is false
   */
  public static <T> void checkEmpty(List<T> list, String errorMessage) {
    if (list != null && !list.isEmpty()) {
      throw new ServiceException(errorMessage);
    }
  }


  /**
   * Checks if a list is  empty , null.
   *
   * @param list the list to be check
   * @param errorMessage the exception message to use if the check fails
   * @throws ServiceException if {@code expression} is false
   */
  public static <T> void checkEmpty(Optional<List<T>> list, String errorMessage) {
    if (list.isPresent() && !list.get().isEmpty()) {
      throw new ServiceException(errorMessage);
    }
  }

  /**
   * return other if t is not present
   *
   * @param t the param to check
   * @param other the result if t is absent
   * @param <T> the type
   */
  public static <T> T orOther(Optional<T> t, T other) {
    if (!t.isPresent()) {
      return other;
    }
    return t.get();
  }

  /**
   * return other if t is null or empty
   *
   * @param t the param to check
   * @param other the result if t is absent
   */
  public static String orOther(String t, String other) {
    if (isBlank(t)) {
      return other;
    }
    return t;
  }

  private static boolean isBlank(CharSequence cs) {
    int strLen;
    if (cs != null && (strLen = cs.length()) != 0) {
      for (int i = 0; i < strLen; ++i) {
        if (!Character.isWhitespace(cs.charAt(i))) {
          return false;
        }
      }
      return true;
    } else {
      return true;
    }
  }

  public static <T> Set<T> checkNotEmpty(Set<T> inputSet, String errorMessage) {
    if (inputSet == null || inputSet.isEmpty()) {
      throw new ServiceException(errorMessage);
    }
    return inputSet;
  }
}
