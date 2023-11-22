package github.mirrentools;

import github.mirrentools.core.*;
import github.mirrentools.core.utils.AESUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.micrometer.MetricsService;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 程序的入口
 *
 * @author <a href="https://github.com/shenzhenMirren">Mirren</a>
 */
public class MainVerticle extends AbstractVerticle {
  /**
   * 用于在IDE编写代码时从控制台启动方便调试
   */
  public static void main(String[] args) {
    MainLauncher.main(new String[]{"run", MainVerticle.class.getName()});
  }

  @Override
  public void start(Promise<Void> startPromise) {
    try {
      WebClient webClient = WebClient.create(vertx, new WebClientOptions(config().getJsonObject("webClient")).setShared(true));
      //初始化全局配置上下文
      new AppContext.Builder()
        .withVertx(vertx)
        .withConfig(getAppConfig())
        .withJdbcPool(getJdbcPool())
        .withLocalStore(LocalDataStore.instance())
        .withSessionStore(SessionDataStore.instance(vertx))
        .withWebClient(webClient)
        .build();

      //初始化指标监控
      initMicrometerMetricsService();

      //部署verticle,如果不需要部署调度任务的就不要添加Schedule
      List<Future<String>> deploys = new ArrayList<>();
      deploys.add(deployServer());
      deploys.add(deploySchedule());
      Future.all(deploys)
        .onSuccess(suc -> {
          System.out.println("Start Successful");
          startPromise.complete();
        })
        .onFailure(err -> {
          System.out.println("Start fail: " + err);
          err.printStackTrace();
          startPromise.fail(err);
        });
    } catch (Exception e) {
      System.out.println("Start init --> fail: " + e);
      e.printStackTrace();
      startPromise.fail(e);
    }
  }

  /**
   * 初始化监控快照
   */
  private void initMicrometerMetricsService() {
    if (CoreConstants.MONITORING_ENABLED) {
      return;
    }
    MetricsService metricsService = MetricsService.create(vertx);
    vertx.eventBus().consumer(CoreConstants.EVENT_BUS_METRICS, res -> {
      JsonObject http = metricsService.getMetricsSnapshot("vertx.http");
      JsonObject sql = metricsService.getMetricsSnapshot("vertx.sql");
      JsonObject jvm = metricsService.getMetricsSnapshot("jvm.memory");
      res.reply(new JsonObject().put("http", http).put("sql", sql).put("jvm", jvm));
    });
  }

  /**
   * 部署ServerVerticle
   */
  private Future<String> deployServer() {
    Integer instance = config().getInteger("instance");
    DeploymentOptions options = new DeploymentOptions();
    options.setConfig(config());
    if (instance > 0) {
      options.setInstances(instance);
      options.setHa(true);
    }
    return vertx.deployVerticle(ServerVerticle.class.getName(), options);
  }

  /**
   * 部署调度的Verticle
   */
  private Future<String> deploySchedule() {
    return vertx.deployVerticle(new ScheduleVerticle());
  }

  /**
   * 获取全局的配置信息
   */
  private AppConfig getAppConfig() {
    JsonObject config = config().getJsonObject("app");
    Boolean testMode = config.getBoolean("testMode");
    //有更多配置的就在这里获取后添加record的最后面
    return new AppConfig(testMode);
  }

  /**
   * 初始化并获取数据库连接池
   */
  private Map<String, Pool> getJdbcPool() {
    Map<String, Pool> result = new HashMap<>();
    JsonArray dbs = config().getJsonArray("dataSource");
    for (int i = 0; i < dbs.size(); i++) {
      JsonObject db = dbs.getJsonObject(i);
      String dbId = db.getString("id");
      MySQLConnectOptions connectOptions = new MySQLConnectOptions()
        .setHost(db.getString("host"))
        .setPort(db.getInteger("port"))
        .setDatabase(db.getString("database"))
        .setIdleTimeout(db.getInteger("idleTimeout"))
        .setUser(AESUtil.decodeAES(db.getString("user")))
        .setPassword(AESUtil.decodeAES(db.getString("pwd")));
      PoolOptions poolOptions = new PoolOptions()
        .setMaxSize(db.getInteger("maxSize"))
        .setName("dbId")
        .setShared(true);
      Pool pool = MySQLPool.pool(vertx, connectOptions, poolOptions);
      result.put(dbId, pool);
    }
    return result;
  }


}
