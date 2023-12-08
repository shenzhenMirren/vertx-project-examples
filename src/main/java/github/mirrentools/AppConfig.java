package github.mirrentools;


import io.vertx.core.json.JsonObject;

/**
 * App的配置信息<br>
 * 调用AppConfig.get.X就可以得到对应数据<br>
 * 需要在服务启动之前(在MainVerticle中)执行AppConfig.init(JsonObject config)进行初始化数据
 */
public class AppConfig {
  /**
   * 配置实例
   */
  public static AppConfig get;

  /**
   * 通过init方法进行初始化并设置属性
   */
  private AppConfig() {
    get = this;
  }

  /**
   * 初始化配置信息
   *
   * @param config app配置信息
   */
  public static void init(JsonObject config) {
    Boolean testMode = config.getBoolean("testMode");
    new AppConfig()
      .setTestMode(testMode);
  }

  private boolean testMode;

  /**
   * 获取当前运行是否为为测试模式
   *
   * @return 是测试模式返回true, 非返回false
   */
  public boolean testMode() {
    return testMode;
  }

  private AppConfig setTestMode(boolean testMode) {
    this.testMode = testMode;
    return this;
  }

}
