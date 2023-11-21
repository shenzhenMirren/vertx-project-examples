package github.mirrentools.core;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 在app生命周期的数据缓存
 * @author <a href="https://github.com/shenzhenMirren">Mirren</a>
 */
public class LocalDataStore {
  private final Logger LOG = LogManager.getLogger(LocalDataStore.class);

  /**
   * 创建一个本地数据存储
   */
  public static LocalDataStore instance() {
    if (INSTANCE == null) {
      synchronized (LocalDataStore.class) {
        if (INSTANCE == null) {
          INSTANCE = new LocalDataStore();
        }
      }
    }
    return INSTANCE;
  }
  /** 对象单例 */
  private volatile static LocalDataStore INSTANCE;
  private LocalDataStore(){};

  /**对象的数据缓存*/
  private final Map<String, JsonObject> objectData = new ConcurrentHashMap<>();
  /**数据的数据缓存*/
  private final Map<String, JsonArray> arrayData = new ConcurrentHashMap<>();
  /**字符串的数据缓存*/
  private final Map<String, String> stringData = new ConcurrentHashMap<>();
  /**int的数据缓存*/
  private final Map<String, Integer> integerData = new ConcurrentHashMap<>();
  /**int的数据缓存*/
  private final Map<String, Double> doubleData = new ConcurrentHashMap<>();
  /**版本号缓存*/
  private final Map<String, Long> versionData = new ConcurrentHashMap<>();

  /**
   * 获取数据
   *
   * @param key 数据的key
   */
  public JsonObject get(String key) {
    return objectData.get(key);
  }

  /**
   * 获取数据
   *
   * @param key 数据的key
   */
  public JsonArray getArray(String key) {
    return arrayData.get(key);
  }

  /**
   * 获取字符串数据
   *
   * @param key 数据的key
   */
  public String getString(String key) {
    return stringData.get(key);
  }

  /**
   * 获取字符串数据
   *
   * @param key 数据的key
   * @param def 默认值
   */
  public String getString(String key, String def) {
    return stringData.get(key) == null ? def : stringData.get(key);
  }

  /**
   * 获取int数据
   *
   * @param key 数据的key
   */
  public Integer getInteger(String key) {
    return integerData.get(key);
  }

  /**
   * 获取int数据
   *
   * @param key 数据的key
   * @param def 默认值
   */
  public Integer getInteger(String key, Integer def) {
    return integerData.get(key) == null ? def : integerData.get(key);
  }
  /**
   * 获取double数据
   *
   * @param key 数据的key
   */
  public Double getDouble(String key) {
    return doubleData.get(key);
  }

  /**
   * 获取double数据
   *
   * @param key 数据的key
   * @param def 默认值
   */
  public Double getDouble(String key, Double def) {
    return doubleData.get(key) == null ? def : doubleData.get(key);
  }

  /**
   * 获取版本号
   *
   * @param key 数据的key
   */
  public long getVersion(String key) {
    return versionData.get(key) == null ? 0L : versionData.get(key);
  }

  /**
   * 添加数据
   *
   * @param key     数据的key
   * @param value   数据
   */
  public void put(String key, JsonArray value) {
   put(key,value,0L);
  }
  /**
   * 添加数据
   *
   * @param key     数据的key
   * @param value   数据
   * @param version 数据的版本号
   */
  public void put(String key, JsonArray value, long version) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("执行添加缓存-->key: {} , version: {}", key, version);
      LOG.debug("values: " + value.encode());
    }
    arrayData.put(key, value);
    versionData.put(key, version);
  }

  /**
   * 添加数据
   *
   * @param key     数据的key
   * @param value   数据
   */
  public void put(String key,  JsonObject value) {
    put(key,value,0L);
  }
  /**
   * 添加数据
   *
   * @param key     数据的key
   * @param value   数据
   * @param version 数据的版本号
   */
  public void put(String key, JsonObject value, long version) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("执行添加缓存-->key: {} , version: {}", key, version);
      LOG.debug("values: " + value.encode());
    }
    objectData.put(key, value);
    versionData.put(key, version);
  }

  /**
   * 添加字符串数据
   *
   * @param key     数据的key
   * @param version 数据的版本号
   * @param value   数据
   */
  public void put(String key, long version, String value) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("执行添加缓存-->key: {} , version: {}", key, version);
      LOG.debug("values: " + value);
    }
    stringData.put(key, value);
    versionData.put(key, version);
  }

  /**
   * 添加int数据
   *
   * @param key     数据的key
   * @param version 数据的版本号
   * @param value   数据
   */
  public void put(String key, long version, Integer value) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("执行添加缓存-->key: {} , version: {}", key, version);
      LOG.debug("values: " + value);
    }
    integerData.put(key, value);
    versionData.put(key, version);
  }
  /**
   * 添加double数据
   *
   * @param key     数据的key
   * @param version 数据的版本号
   * @param value   数据
   */
  public void put(String key, long version, Double value) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("执行添加缓存-->key: {} , version: {}", key, version);
      LOG.debug("values: " + value);
    }
    doubleData.put(key, value);
    versionData.put(key, version);
  }

  /**
   * 删除数据与版本号
   *
   * @param key 数据的key
   */
  public void remove(String key) {
    objectData.remove(key);
    arrayData.remove(key);
    stringData.remove(key);
    integerData.remove(key);
    doubleData.remove(key);
    versionData.remove(key);
  }

}
