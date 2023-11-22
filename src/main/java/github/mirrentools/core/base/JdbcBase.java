package github.mirrentools.core.base;

import github.mirrentools.core.AppContext;
import github.mirrentools.core.sql.SQLExecute;
import github.mirrentools.core.sql.SqlAndParams;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库操作的基本实现
 *
 * @author <a href="https://github.com/shenzhenMirren">Mirren</a>
 */
public class JdbcBase {
  /**
   * 日志工具
   */
  public final Logger LOG = LoggerFactory.getLogger(this.getClass());
  /**
   * 应用的上下文
   */
  private final AppContext content;
  /**
   * 默认的数据库链接
   */
  private SQLExecute jdbcExecute;
  /**
   * 其他数据库链接合集
   */
  private final Map<String, SQLExecute> jdbcExecuteMap = new ConcurrentHashMap<>();

  public JdbcBase() {
    this.content = AppContext.context;
  }

  /**
   * 获取默认的数据库操作类
   */
  public SQLExecute jdbcExecute() {
    if (jdbcExecute == null) {
      jdbcExecute = SQLExecute.create(content.jdbcPool());
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug("SQL Execute -> Pool --> 使用默认数据源");
    }
    return jdbcExecute;
  }

  /**
   * 获取指定的数据库操作类
   *
   * @param id 数据库的id,如果id为空就返回默认的数据库操作类
   */
  public SQLExecute jdbcExecute(String id) {
    if (id == null) {
      return jdbcExecute();
    }
    SQLExecute pool = jdbcExecuteMap.get(id);
    if (pool == null) {
      pool = jdbcExecuteMap.computeIfAbsent(id, key -> SQLExecute.create(content.jdbcPool(key)));
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug("SQL Execute -> Pool -> 使用指定数据源 --> " + id);
    }
    return pool;
  }

  /**
   * 查询数量
   *
   * @param query SQL与参数
   */
  public Future<Long> getLong(SqlAndParams query) {
    return getLong(null, query);
  }

  /**
   * 查询数量
   *
   * @param query SQL与参数
   */
  public void getLong(SqlAndParams query, Handler<AsyncResult<Long>> handler) {
    getLong(null, query, handler);
  }


  /**
   * 查询数量
   *
   * @param dbId  指定数据源的id
   * @param query SQL与参数
   */
  public Future<Long> getLong(String dbId, SqlAndParams query) {
    Promise<Long> promise = Promise.promise();
    getLong(dbId, query, promise);
    return promise.future();
  }

  /**
   * 查询数量
   *
   * @param dbId    指定数据源的id
   * @param query   SQL与参数
   * @param handler 操作结果
   */
  public void getLong(String dbId, SqlAndParams query, Handler<AsyncResult<Long>> handler) {
    SQLExecute pool = jdbcExecute(dbId);
    if (pool == null) {
      handler.handle(Future.failedFuture("数据源不存在" + (dbId == null ? "" : "-->" + dbId)));
      return;
    }
    pool.selectAsLong(query, handler);
  }


  /**
   * 查询Object结果
   *
   * @param query SQL与参数
   */
  public Future<JsonObject> getObject(SqlAndParams query) {
    return getObject(null, query);
  }

  /**
   * 查询Object结果
   *
   * @param query   SQL与参数
   * @param handler 响应结果
   */
  public void getObject(SqlAndParams query, Handler<AsyncResult<JsonObject>> handler) {
    getObject(null, query, handler);
  }

  /**
   * 查询Object结果
   *
   * @param dbId  指定数据源的id
   * @param query SQL与参数
   */
  public Future<JsonObject> getObject(String dbId, SqlAndParams query) {
    Promise<JsonObject> promise = Promise.promise();
    getObject(dbId, query, promise);
    return promise.future();
  }

  /**
   * 查询Object结果
   *
   * @param dbId    指定数据源的id
   * @param query   SQL与参数
   * @param handler 响应结果
   */
  public void getObject(String dbId, SqlAndParams query, Handler<AsyncResult<JsonObject>> handler) {
    SQLExecute pool = jdbcExecute(dbId);
    if (pool == null) {
      handler.handle(Future.failedFuture("数据源不存在" + (dbId == null ? "" : "-->" + dbId)));
      return;
    }
    pool.selectAsObject(query, handler);
  }

  /**
   * 查询List结果
   *
   * @param query SQL与参数
   */
  public Future<List<JsonObject>> getList(SqlAndParams query) {
    return getList(null, query);
  }

  /**
   * 查询List结果
   *
   * @param query   SQL与参数
   * @param handler 响应结果
   */
  public void getList(SqlAndParams query, Handler<AsyncResult<List<JsonObject>>> handler) {
    getList(null, query, handler);
  }

  /**
   * 查询List结果
   *
   * @param dbId  指定数据源的id
   * @param query SQL与参数
   */
  public Future<List<JsonObject>> getList(String dbId, SqlAndParams query) {
    Promise<List<JsonObject>> promise = Promise.promise();
    getList(dbId, query, promise);
    return promise.future();
  }

  /**
   * 查询List结果
   *
   * @param dbId    指定数据源的id
   * @param query   SQL与参数
   * @param handler 响应结果
   */
  public void getList(String dbId, SqlAndParams query, Handler<AsyncResult<List<JsonObject>>> handler) {
    SQLExecute pool = jdbcExecute(dbId);
    if (pool == null) {
      handler.handle(Future.failedFuture("数据源不存在" + (dbId == null ? "" : "-->" + dbId)));
      return;
    }
    pool.selectAsList(query, handler);
  }

  /**
   * 执行操作
   *
   * @param query SQL与参数
   */
  public Future<Integer> updateInt(SqlAndParams query) {
    return updateInt(null, query);
  }

  /**
   * 执行操作
   *
   * @param query   SQL与参数
   * @param handler 响应结果
   */
  public void updateInt(SqlAndParams query, Handler<AsyncResult<Integer>> handler) {
    updateInt(null, query, handler);
  }

  /**
   * 执行操作
   *
   * @param dbId  指定数据源的id
   * @param query SQL与参数
   */
  public Future<Integer> updateInt(String dbId, SqlAndParams query) {
    Promise<Integer> promise = Promise.promise();
    updateInt(dbId, query, promise);
    return promise.future();
  }

  /**
   * 执行操作
   *
   * @param dbId    数据源id
   * @param query   SQL与参数
   * @param handler 响应结果
   */
  public void updateInt(String dbId, SqlAndParams query, Handler<AsyncResult<Integer>> handler) {
    SQLExecute pool = jdbcExecute(dbId);
    if (pool == null) {
      handler.handle(Future.failedFuture("数据源不存在" + (dbId == null ? "" : "-->" + dbId)));
      return;
    }
    pool.update(query, handler);
  }

  /**
   * 批量执行数据更新
   *
   * @param query SQL与参数
   */
  public Future<Integer> batchInt(SqlAndParams query) {
    return batchInt(null, query);
  }

  /**
   * 批量执行数据更新
   *
   * @param query SQL与参数
   */
  public void batchInt(SqlAndParams query, Handler<AsyncResult<Integer>> handler) {
    batchInt(null, query, handler);
  }

  /**
   * 批量执行数据更新
   *
   * @param dbId  指定数据源的id
   * @param query SQL与参数
   */
  public Future<Integer> batchInt(String dbId, SqlAndParams query) {
    Promise<Integer> promise = Promise.promise();
    batchInt(dbId, query, promise);
    return promise.future();
  }

  /**
   * 批量执行数据更新
   *
   * @param dbId    指定数据源的id
   * @param query   SQL与参数
   * @param handler 响应结果
   */
  public void batchInt(String dbId, SqlAndParams query, Handler<AsyncResult<Integer>> handler) {
    SQLExecute pool = jdbcExecute(dbId);
    if (pool == null) {
      handler.handle(Future.failedFuture("数据源不存在" + (dbId == null ? "" : "-->" + dbId)));
      return;
    }
    pool.batch(query, handler);
  }


}
