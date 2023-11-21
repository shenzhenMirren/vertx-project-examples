package github.mirrentools.core.utils;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.sql.Date;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 字符串工具
 *
 * @author <a href="https://github.com/shenzhenMirren">Mirren</a>
 */
public class StringUtil {
  /**
   * 将字符串首字母大写其后小写
   */
  public static String firstToUpCaseLaterToLoCase(String str) {
    if (str != null && !str.isEmpty()) {
      str = (str.substring(0, 1).toUpperCase()) + (str.substring(1).toLowerCase());
    }
    return str;
  }

  /**
   * 将字符串首字母小写其后大写
   */
  public static String firstToLoCaseLaterToUpCase(String str) {
    if (str != null && !str.isEmpty()) {
      str = (str.substring(0, 1).toLowerCase()) + (str.substring(1).toUpperCase());

    }
    return str;
  }

  /**
   * 将字符串首字母大写
   */
  public static String firstToUpCase(String str) {
    if (str != null && !str.isEmpty()) {
      str = str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    return str;
  }

  /**
   * 将字符串首字母小写
   */
  public static String firstToLoCase(String str) {
    if (str != null && !str.isEmpty()) {
      str = str.substring(0, 1).toLowerCase() + str.substring(1);
    }
    return str;
  }

  /**
   * 检查字符串里面是否包含指定字符,包含返回true
   *
   * @param regex 指定字符
   * @param str   字符串
   */
  public static boolean indexOf(String regex, String... str) {
    if (str == null) {
      return false;
    }
    for (String temp : str) {
      if (!temp.contains(regex)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 将字符串str中带有集合中rep[0]的字符串,代替为rep[1]的中的字符串
   */
  public static String replace(String str, List<String[]> rep) {
    for (String[] item : rep) {
      if (item[1] == null) {
        item[1] = "";
      }
      str = str.replace(item[0], item[1]);
    }
    return str;
  }

  /**
   * 创建字符串数组
   */
  public static String[] asStrArray(String... str) {
    return str;
  }

  /**
   * 判断字符串是否为null或者空,如果是返回true
   */
  public static boolean isNullOrEmpty(String str) {
    if (str == null || "".equals(str.trim())) {
      return true;
    }
    return false;
  }

  /**
   * 判断字符串是否为null或者空,如果是返回true
   */
  public static boolean isNullOrEmpty(Object str) {
    if (str == null) {
      return true;
    }
    return str.toString().trim().isEmpty();
  }

  /**
   * 判断字符串是否为null或者空,如果是返回true
   */
  public static boolean isNullOrEmpty(Object str, Object str1) {
    if (str == null || str1 == null) {
      return true;
    }
    return str.toString().trim().isEmpty() || str1.toString().trim().isEmpty();
  }

  /**
   * 判断字符串是否为null或者空,如果是返回true
   */
  public static boolean isNullOrEmpty(Object str, Object str1, Object str2) {
    if (str == null || str1 == null || str2 == null) {
      return true;
    }
    return str.toString().trim().isEmpty() || str1.toString().trim().isEmpty() || str2.toString().trim().isEmpty();
  }

  /**
   * 判断字符串是否为null或者空,如果是返回true
   */
  public static boolean isNullOrEmpty(Object str, Object str1, Object str2, Object str3) {
    if (str == null || str1 == null || str2 == null || str3 == null) {
      return true;
    }
    return str.toString().trim().isEmpty() || str1.toString().trim().isEmpty() || str2.toString().trim().isEmpty() || str3.toString().trim().isEmpty();
  }

  /**
   * 判断字符串是否为null或者空,如果是返回true
   */
  public static boolean isNullOrEmpty(Object... str) {
    if (str == null || str.length == 0) {
      return true;
    }
    for (int i = 0; i < str.length; i++) {
      if (str[i] == null || str[i].toString().trim().isEmpty()) {
        return true;
      }
    }
    return false;
  }

  /**
   * 判断字符串是否为null
   */
  public static boolean isNull(Object... str) {
    if (str == null || str.length == 0) {
      return true;
    }
    for (int i = 0; i < str.length; i++) {
      if (str[i] == null) {
        return true;
      }
    }
    return false;
  }

  /**
   * 将毫秒按指定格式转换为 年日时分秒,如果如果格式中不存在年则将年装换为天
   *
   * @param time    毫秒
   * @param pattern 正则:$y=年,$d=日,$h=小时,$m分钟,$s=秒 <br>
   *                示例:$y年$d日 = 10年12天
   */
  public static String millisToDateTime(long time, String pattern) {
    long day = time / 86400000;
    long hour = (time % 86400000) / 3600000;
    long minute = (time % 86400000 % 3600000) / 60000;
    long second = (time % 86400000 % 3600000 % 60000) / 1000;
    if (!pattern.contains("$y")) {
      pattern = pattern.replace("$d", Long.toString(day));
    } else {
      pattern = pattern.replace("$y", Long.toString(day / 365)).replace("$d", Long.toString(day % 365));
    }
    return pattern.replace("$h", Long.toString(hour)).replace("$m", Long.toString(minute)).replace("$s", Long.toString(second));
  }

  /**
   * 字符串数组大小写字母与数字,该数组的规则为0-9,a-z,A-Z
   */
  public final static char[] ALPHANUMERIC = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

  /**
   * 生成指定程度随机字符串
   *
   * @param len 字符串长度
   */
  public static String randomString(int len) {
    StringBuilder result = new StringBuilder(len);
    Random random = new Random();
    for (int i = 0; i < len; i++) {
      result.append(ALPHANUMERIC[random.nextInt(62)]);
    }
    return result.toString();
  }

  /**
   * 生成指定程度随机字符串
   *
   * @param len  字符串长度
   * @param mode 生成的类型:<br>
   *             0=0-9,<br>
   *             1=a-Z,<br>
   *             2=a-z,<br>
   *             3=A-Z,<br>
   *             其他返回0-Z
   */
  public static String randomString(int len, int mode) {
    StringBuilder result = new StringBuilder(len);
    Random random = new Random();
    if (mode == 0) {
      for (int i = 0; i < len; i++) {
        result.append(ALPHANUMERIC[random.nextInt(9)]);
      }
    } else if (mode == 1) {
      for (int i = 0; i < len; i++) {
        result.append(ALPHANUMERIC[random.nextInt(52) + 10]);
      }
    } else if (mode == 2) {
      for (int i = 0; i < len; i++) {
        result.append(ALPHANUMERIC[random.nextInt(26) + 10]);
      }
    } else if (mode == 3) {
      for (int i = 0; i < len; i++) {
        result.append(ALPHANUMERIC[random.nextInt(26) + 36]);
      }
    } else {
      for (int i = 0; i < len; i++) {
        result.append(ALPHANUMERIC[random.nextInt(62)]);
      }
    }
    return result.toString();
  }

  /**
   * 获得随机UUID
   *
   * @param mode true=带下划线,false不带下划线
   */
  public static String randomUUID(boolean mode) {
    if (mode) {
      return UUID.randomUUID().toString();
    } else {
      return UUID.randomUUID().toString().replace("-", "");
    }
  }

  /**
   * 判断是否为正确的用户id
   *
   * @param id 用户id
   * @return 正确的id返回true, 错误返回false
   */
  public static boolean isRightUserId(String id) {
    return id != null && id.matches("^[a-z][a-z0-9_]{4,18}$");
  }

  /**
   * 判断一个字符串是否为指定对象
   *
   * @param str  字符串
   * @param type 对象类型
   * @return 是返回true, 反则返回false
   */
  public static boolean isType(String str, JavaType type) {
    try {
      if (type == JavaType.Boolean) {
        return str.equals("true") || str.equals("false");
      } else if (type == JavaType.Integer) {
        Integer.parseInt(str);
      } else if (type == JavaType.Long) {
        Long.parseLong(str);
      } else if (type == JavaType.Float) {
        Float.parseFloat(str);
      } else if (type == JavaType.Double) {
        Double.parseDouble(str);
      } else if (type == JavaType.JsonObject) {
        new JsonObject(str);
      } else if (type == JavaType.JsonArray) {
        new JsonArray(str);
      } else if (type == JavaType.Date) {
        Date.valueOf(str);
      } else if (type == JavaType.Instant) {
        Instant.parse(str);
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * java的数据类型枚举类
   */
  public static enum JavaType {
    String, Integer, Date, Instant, Long, Float, Double, Boolean, JsonObject, JsonArray;
  }

  /**
   * 获取一个字符串,如果不为null就去掉左右空格
   */
  public static String get(String str) {
    if (isNullOrEmpty(str)) {
      return null;
    }
    return str.trim();
  }

  /**
   * 获取一个字符串,如果为null就返回默认值,不为null就去掉左右空格
   */
  public static String get(String str, String def) {
    if (isNullOrEmpty(str)) {
      return def;
    }
    return str.trim();
  }

  /**
   * 将一个字符串转换为int,如果字符串为null或者""返回0
   */
  public static int getInt(String str) {
    if (isNullOrEmpty(str)) {
      return 0;
    }
    try {
      return Integer.parseInt(str.trim());
    } catch (Exception e) {
      return 0;
    }
  }

  /**
   * 将一个字符串转换为Integer,如果字符串为null或者""返回null
   */
  public static Integer getInteger(String str) {
    if (isNullOrEmpty(str)) {
      return null;
    }
    try {
      return Integer.parseInt(str.trim());
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 将一个字符串转换为Integer,如果字符串为null或者""返回null
   */
  public static Integer getInteger(String str, Integer def) {
    if (isNullOrEmpty(str)) {
      return def;
    }
    try {
      return Integer.parseInt(str.trim());
    } catch (Exception e) {
      return def;
    }
  }

  /**
   * 将一个字符串转换为Integer,如果字符串为null或者""返回null
   */
  public static Integer getIntegerUnsigned(String str, Integer def) {
    if (isNullOrEmpty(str)) {
      return def;
    }
    try {
      int res = Integer.parseInt(str.trim());
      return (res < 0) ? 0 : res;
    } catch (Exception e) {
      return def;
    }
  }

  /**
   * 将一个字符串转换为Integer,如果字符串为null或者""返回null
   */
  public static Integer getInteger(Integer val, Integer def) {
    if (val == null) {
      return def;
    } else {
      return val;
    }
  }

  /**
   * 将一个字符串转换为long,如果字符串为null或者""返回0
   */
  public static long getlong(String str) {
    if (isNullOrEmpty(str)) {
      return 0L;
    }
    try {
      return Long.parseLong(str.trim());
    } catch (Exception e) {
      return 0L;
    }
  }

  /**
   * 将一个字符串转换为Long,如果字符串为null或者""返回null
   */
  public static Long getLong(String str) {
    if (isNullOrEmpty(str)) {
      return null;
    }
    try {
      return Long.parseLong(str.trim());
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 将一个Long,如果为null或者""返回null
   */
  public static Long getLong(Long str, Long def) {
    if (str == null) {
      return def;
    } else {
      return str;
    }
  }

  /**
   * 将一个字符串转换为Long,如果字符串为null或者""返回null
   */
  public static Long getLong(String str, Long def) {
    if (isNullOrEmpty(str)) {
      return def;
    }
    try {
      return Long.parseLong(str.trim());
    } catch (Exception e) {
      return def;
    }
  }

  /**
   * 将一个字符串转换为float,如果字符串为null或者""返回0.0f
   */
  public static float getFloats(String str) {
    if (isNullOrEmpty(str)) {
      return 0.0f;
    }
    try {
      return Float.parseFloat(str.trim());
    } catch (NumberFormatException e) {
      return 0.0f;
    }
  }

  /**
   * 将一个字符串转换为float,如果字符串为null或者""返回null
   */
  public static Float getFloat(String str) {
    if (isNullOrEmpty(str)) {
      return null;
    }
    try {
      return Float.parseFloat(str.trim());
    } catch (NumberFormatException e) {
      return null;
    }
  }

  /**
   * 将一个字符串转换为double,如果字符串为null或者""返回0.0
   */
  public static double getDoubles(String str) {
    if (isNullOrEmpty(str)) {
      return 0.0;
    }
    try {
      return Double.parseDouble(str.trim());
    } catch (NumberFormatException e) {
      return 0.0;
    }
  }

  /**
   * 将一个字符串转换为Double,如果字符串为null或者""返回null
   */
  public static Double getDouble(String str) {
    if (isNullOrEmpty(str)) {
      return null;
    }
    try {
      return Double.parseDouble(str.trim());
    } catch (NumberFormatException e) {
      return null;
    }
  }

  public static Double getDouble(String str, Double def) {
    if (isNullOrEmpty(str)) {
      return def;
    }
    try {
      return Double.parseDouble(str.trim());
    } catch (NumberFormatException e) {
      return def;
    }
  }

  /**
   * 将一个字符串转换为Date,如果字符串为null或者""返回null
   */
  public static Date getDate(String str) {
    if (isNullOrEmpty(str)) {
      return null;
    }
    try {
      return Date.valueOf(str.trim());
    } catch (Exception e) {
      return new Date(Long.parseLong(str));
    }
  }

  /**
   * 将一个字符串转换为Instant,如果字符串为null或者""返回null
   *
   * @param str 字符串
   */
  public static Instant getInstant(String str) throws NumberFormatException, DateTimeParseException, RuntimeException {
    if (isNullOrEmpty(str)) {
      return null;
    }
    try {
      return Instant.parse(str);
    } catch (Exception e) {
      return Instant.ofEpochMilli(Long.parseLong(str));
    }
  }

  /**
   * 将一个字符串转换为JsonObject,如果字符串为null或者""返回null
   */
  public static JsonObject getJsonObject(String str) {
    if (isNullOrEmpty(str)) {
      return null;
    }
    try {
      return new JsonObject(str);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 将一个字符串转换为JsonArray,如果字符串为null或者""返回null
   */
  public static JsonArray getJsonArray(String str) {
    if (isNullOrEmpty(str)) {
      return null;
    }
    try {
      return new JsonArray(str);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 手机号码格式化 将+区号-手机号改为00区号手机号,如果为+86-或者0086则将其去除
   */
  public static String phoneFormat(String phone) {
    if (phone == null) {
      return null;
    }
    phone = phone.trim();
    if (phone.startsWith("0086")) {
      phone = phone.substring(4);
    }
    if (phone.startsWith("+86-")) {
      phone = phone.replace("+86-", "");
    }
    phone = phone.replace("+", "00").replace("-", "");
    return phone;
  }

  /**
   * 手机号码格式化 如果为+86-或者0086则将其去除
   */
  public static String phoneFormatChina(String phone) {
    if (phone == null) {
      return null;
    }
    phone = phone.trim();
    if (phone.startsWith("0086")) {
      phone = phone.substring(4);
    }
    if (phone.startsWith("+86-")) {
      phone = phone.replace("+86-", "");
    }
    return phone;
  }

}
