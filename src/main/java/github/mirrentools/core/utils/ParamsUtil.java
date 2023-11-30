package github.mirrentools.core.utils;

import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 获取MultiMap参数的工具
 *
 * @author <a href="https://github.com/shenzhenMirren">Mirren</a> *
 */
public class ParamsUtil {

  /**
   * 获取字符串
   *
   * @param params 请求参数
   * @param name   参数的名称
   */
  public static String getString(MultiMap params, String name) {
    return StringUtil.get(params.get(name));
  }

  /**
   * 获取字符串
   *
   * @param params 请求参数
   * @param name   参数的名称
   * @param def    默认值
   */
  public static String getString(MultiMap params, String name, String def) {
    return StringUtil.get(params.get(name), def);
  }

  /**
   * 获取int
   *
   * @param params 请求参数
   * @param name   参数名称
   */
  public static int getInt(MultiMap params, String name) {
    return StringUtil.getInt(params.get(name));
  }

  /**
   * 获取不低于0的int
   *
   * @param params 请求参数
   * @param name   参数名称
   */
  public static int getIntegerUnsigned(MultiMap params, String name) {
    return StringUtil.getIntegerUnsigned(params.get(name), 0);
  }

  /**
   * 获取Integer
   *
   * @param params 请求参数
   * @param name   参数名称
   */
  public static Integer getInteger(MultiMap params, String name) {
    return StringUtil.getInteger(params.get(name));
  }

  /**
   * 获取Integer
   *
   * @param params 请求参数
   * @param name   参数名称
   * @param def    默认值
   */
  public static Integer getInteger(MultiMap params, String name, Integer def) {
    return StringUtil.getInteger(params.get(name), def);
  }

  /**
   * 获取Long
   *
   * @param params 请求参数
   * @param name   参数名称
   */
  public static long getLongs(MultiMap params, String name) {
    return StringUtil.getlong(params.get(name));
  }

  /**
   * 获取Long
   *
   * @param params 请求参数
   * @param name   参数名称
   */
  public static Long getLong(MultiMap params, String name) {
    return StringUtil.getLong(params.get(name));
  }

  /**
   * 获取Long
   *
   * @param params 请求参数
   * @param name   参数名称
   * @param def    默认值
   */
  public static Long getLong(MultiMap params, String name, Long def) {
    return StringUtil.getLong(params.get(name), def);
  }


  /**
   * 获取基本类型的double
   *
   * @param params 请求参数
   * @param name   参数名称
   */
  public static double getDoubles(MultiMap params, String name) {
    return StringUtil.getDoubles(params.get(name));
  }

  /**
   * 获取Double
   *
   * @param params 参数
   * @param name   名称
   */
  public static Double getDouble(MultiMap params, String name) {
    return StringUtil.getDouble(params.get(name));
  }

  /**
   * 获取Double
   *
   * @param params 参数
   * @param name   名称
   */
  public static Double getDouble(MultiMap params, String name, Double def) {
    return StringUtil.getDouble(params.get(name), def);
  }

    /**
   * 获取JsonObject对象,如果无效就返回null
   *
   * @param params 参数
   * @param name   名称
   */
  public static JsonObject getJsonObject(MultiMap params, String name) {
    try {
      return new JsonObject(params.get(name).trim());
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 获取JsonObject对象,如果指定的无效就返回默认值
   *
   * @param params 参数
   * @param name   名称
   * @param def    默认值
   */
  public static JsonObject getJsonObject(MultiMap params, String name, JsonObject def) {
    try {
      return new JsonObject(params.get(name).trim());
    } catch (Exception e) {
      return def;
    }
  }
  /**
   * 获取JsonArray对象,如果无效就返回null
   *
   * @param params 参数
   * @param name   名称
   */
  public static JsonArray getJsonArray(MultiMap params, String name) {
    try {
      return new JsonArray(params.get(name).trim());
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 获取JsonArray对象,如果指定的无效就返回默认值
   *
   * @param params 参数
   * @param name   名称
   * @param def    默认值
   */
  public static JsonArray getJsonArray(MultiMap params, String name, JsonArray def) {
    try {
      return new JsonArray(params.get(name).trim());
    } catch (Exception e) {
      return def;
    }
  }

}
