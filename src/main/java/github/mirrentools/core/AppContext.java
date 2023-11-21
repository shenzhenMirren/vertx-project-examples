package github.mirrentools.core;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.sqlclient.Pool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 程序的上下文可以获取vert.x实例,数据连接池,路由器等<br>
 * 通过内部类Builder来构造这个类的实例
 */
public class AppContext {
  public static AppContext context;

  /**
   * 通过内部类Builder来构造这个类的实例
   */
  private AppContext() {
    context = this;
  }

  /**
   * Vert.x的实例
   */
  private Vertx vertx;
  /**
   * HTTP客户端
   */
  private WebClient webClient;

  /**
   * 配置文件
   */
  private AppConfig config;

  /**
   * 数据库链接池
   */
  private Map<String, Pool> jdbcPool;

  /**
   * 无状态会话存储器
   */
  private SessionDataStore sessionStore;
  /**
   * 本地数据缓存
   */
  private LocalDataStore localStore;
  /**
   * 共享数据
   */
  private final Map<String, Object> shareData = new ConcurrentHashMap<>();
  /**
   * 计数器
   */
  private final AtomicLong counter = new AtomicLong(0L);

  /**
   * Vert.x的实例
   */
  public Vertx vertx() {
    return vertx;
  }


  /**
   * HTTP客户端
   */
  public WebClient webClient() {
    return webClient;
  }

  /**
   * 获取配置信息
   */
  public AppConfig config() {
    return config;
  }


  /**
   * 获取默认数据库链接池
   */
  public Pool jdbcPool() {
    Pool pool = jdbcPool.get("def");
    if (pool == null && jdbcPool.size() == 1) {
      pool = jdbcPool.values().iterator().next();
    }
    return pool;
  }

  /**
   * 获取指定的数据库连接池
   *
   * @param key 数据池的key
   */
  public Pool jdbcPool(String key) {
    return jdbcPool.get(key);
  }

  /**
   * 获取无状态的会话存储
   */
  public SessionDataStore sessionDataStore() {
    return sessionStore;
  }

  /**
   * 获取应用生命流程的本地数据缓存
   */
  public LocalDataStore localDataStore() {
    return localStore;
  }

  /**
   * 获取自定义的共享数据
   */
  public Object getData(String key) {
    return shareData.get(key);
  }

  /**
   * 设置自定义的共享数据
   */
  public AppContext putDataIfAbsent(String key, Object value) {
    shareData.putIfAbsent(key, value);
    return this;
  }

  /**
   * 获取一个计数器,每次获取都加1
   */
  public long counter() {
    return counter.incrementAndGet();
  }

  /**
   * AppContent的构造类
   */
  public static class Builder {
    private Vertx vertx;
    private WebClient webClient;
    private AppConfig config;
    private Map<String, Pool> jdbcPool;
    private SessionDataStore sessionStore;
    private LocalDataStore localStore;

    public Builder() {
    }

    public Builder withVertx(Vertx vertx) {
      this.vertx = vertx;
      return this;
    }


    public Builder withWebClient(WebClient webClient) {
      this.webClient = webClient;
      return this;
    }

    public Builder withConfig(AppConfig config) {
      this.config = config;
      return this;
    }

    public Builder withJdbcPool(Map<String, Pool> jdbcPool) {
      this.jdbcPool = jdbcPool;
      return this;
    }

    public Builder withSessionStore(SessionDataStore sessionStore) {
      this.sessionStore = sessionStore;
      return this;
    }

    public Builder withLocalStore(LocalDataStore localStore) {
      this.localStore = localStore;
      return this;
    }


    public AppContext build() {
      AppContext appContent = new AppContext();
      appContent.vertx = this.vertx;
      appContent.webClient = this.webClient;
      appContent.config = this.config;
      appContent.jdbcPool = this.jdbcPool;
      appContent.sessionStore = this.sessionStore;
      appContent.localStore = this.localStore;
      return appContent;
    }
  }

}
