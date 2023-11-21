package github.mirrentools.core.utils;

import io.vertx.core.MultiMap;

/**
 * 获取Nginx的IP工具
 * @author <a href="https://github.com/shenzhenMirren">Mirren</a>
 */
public class NginxIpUtil {
  /**
   * 获取不到则返回null,获取为本机ip6则转为ip4
   */
  public static String getIpAddress(MultiMap headers) {
    if (headers == null || headers.isEmpty()) {
      return null;
    }
    String ip = headers.get("x-forwarded-for");
    if (ip != null && ip.contains(",")) {
      try {
        ip = ip.split(",")[0];
      } catch (Exception ignored) {
      }
    }
    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
      ip = headers.get("Proxy-Client-IP");
    }
    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
      ip = headers.get("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
      ip = headers.get("HTTP_CLIENT_IP");
    }
    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
      ip = headers.get("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
      ip = headers.get("X-Real-IP");
    }
    if ("0:0:0:0:0:0:0:1".equals(ip) || "localhost".equals(ip)) {
      return "127.0.0.1";
    }
    return ip;
  }
}
