package github.mirrentools.core.base;


import io.vertx.core.MultiMap;

/**
 * 数据校验帮助类
 * 可检查字符串是否为空,长度是否超过规定的,以及数字判断是否为空与符合大小
 */
public interface DataValidatorHelper {

  /**
   * 判断内容是否为null或空
   *
   * @param data 数据
   */
  default boolean isNullOrEmpty(String data) {
    return data == null || data.isEmpty();
  }

  /**
   * 判断内容是否为null或空
   *
   * @param data 数据
   */

  default boolean isNullOrEmpty(Object data) {
    return data == null || data.toString().isEmpty();
  }

  /**
   * 检查数据是否为空或null
   *
   * @param data 请求的参数
   */
  default boolean check(String data) {
    return isNullOrEmpty(data);
  }

  /**
   * 检查数据是否为空或null
   *
   * @param data 请求的参数
   */
  default boolean check(Object data) {
    return isNullOrEmpty(data);
  }

  /**
   * 检查数据是否为空或null
   *
   * @param data 请求的参数
   */
  default boolean check(Object... data) {
    if (isNullOrEmpty(data)) {
      return true;
    }
    for (Object d : data) {
      if (isNullOrEmpty(d)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 检查指定的数据是否为空或null
   *
   * @param params   用户请求的参数
   * @param paramKey 要检查的参数名称
   */
  default boolean check(MultiMap params, String... paramKey) {
    for (String key : paramKey) {
      if (isNullOrEmpty(params.get(key))) {
        return true;
      }
    }
    return false;
  }

  /**
   * 检查内容的长度是否小于指定长度
   *
   * @param data 字符串
   * @param len  长度
   * @return 如果data为null返回true, 长度小于len返回true
   */
  default boolean lenLt(String data, int len) {
    return data == null || data.length() < len;
  }

  /**
   * 检查内容的长度是否大于指定长度
   *
   * @param data 字符串
   * @param len  长度
   * @return 如果data为null返回true, 长度大于len返回true
   */
  default boolean lenGt(String data, int len) {
    return data == null || data.length() > len;
  }

  /**
   * 检查内容的长度是否大于或小于指定长度
   *
   * @param data 字符串
   * @param gt   最小长度
   * @param lt   最大长度
   * @return 如果data为null返回true, 长度大于gt或小于lt返回true
   */
  default boolean lenLtOrGt(String data, int lt, int gt) {
    return data == null || (data.length() < lt || data.length() > gt);
  }

  /**
   * 检查数值是否小于指定数值
   *
   * @param data 数字
   * @param len  大小
   * @return 如果data为null返回true, 小于len返回true
   */
  default boolean lenLt(Integer data, int len) {
    return data == null || data < len;
  }

  /**
   * 检查数值是否大于指定数值
   *
   * @param data 数字
   * @param len  大小
   * @return 如果data为null返回true, 大于len返回true
   */
  default boolean lenGt(Integer data, int len) {
    return data == null || data > len;
  }

  /**
   * 检查数值是否大于或小于指定数值
   *
   * @param data 数字
   * @param lt   小于
   * @param gt   大于
   * @return 如果data为null返回true, 小于或大于len返回true
   */
  default boolean lenLtOrGt(Integer data, int lt, int gt) {
    return data == null || (data < lt || data > gt);
  }

}
