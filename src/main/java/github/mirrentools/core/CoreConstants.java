package github.mirrentools.core;

/**
 * EventBus的地址常量
 */
public class CoreConstants {
  /**
   * 响应类型: Content-Type
   */
  public static final String CONTENT_TYPE = "Content-Type";
  /**
   * 响应结果: application/json;charset=UTF-8
   */
  public static final String CONTENT_JSON_UTF8 = "application/json;charset=UTF-8";
  /**
   * 会话存储用户的token
   */
  public static final String SESSION_KEY_USER_TOKEN = "session_key_user_token";

  /**
   * 监控指标的EventBus地址
   */
  public static final String EVENT_BUS_METRICS = "event-bus-metrics";

  /**
   * 是否启动监控指标,该变量在MainLauncher的beforeStartingVertx方法中被改变
   */
  public static boolean MONITORING_ENABLED = true;
  /**
   * 监控指标的路由地址
   */
  public static final String MONITORING_ROUTE_PATH = "/public/api/micrometer/metrics/get/snapshot";

}
