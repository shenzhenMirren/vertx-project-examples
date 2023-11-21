package github.mirrentools.core.sql;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PropertyKind;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;

import java.util.List;

/**
 * SQL执行器
 * @author <a href="https://github.com/shenzhenMirren">Mirren</a>
 */
public interface SQLExecute {
  /**
   * 创建客户端实例
   *
   * @param pool 数据库连接池
   */
  static SQLExecute create(Pool pool) {
    return new SQLExecuteImpl(pool);
  }


  /**
   * 获取客户端
   */
  Pool pool();

  /**
   * 执行查询
   *
   * @param query SQL语句与参数
   */
  default Future<RowSet<Row>> execute(SqlAndParams query) {
    Promise<RowSet<Row>> promise = Promise.promise();
    execute(query, promise);
    return promise.future();
  }

  /**
   * 执行查询
   *
   * @param query   SQL语句与参数
   * @param handler 返回结果
   */
  void execute(SqlAndParams query, Handler<AsyncResult<RowSet<Row>>> handler);



  /**
   * 执行查询返回一个数量结果
   *
   * @param query SQL语句与参数
   */
  default Future<Long> selectAsLong(SqlAndParams query) {
    Promise<Long> promise = Promise.promise();
    selectAsLong(query, promise);
    return promise.future();
  }

  /**
   * 执行查询返回一个数量结果
   *
   * @param query SQL语句与参数
   * @param handler 返回结果,为null则返回0L
   */
  void selectAsLong(SqlAndParams query, Handler<AsyncResult<Long>> handler);

  /**
   * 执行查询
   *
   * @param query SQL语句与参数
   */
  default Future<JsonObject> selectAsObject(SqlAndParams query) {
    Promise<JsonObject> promise = Promise.promise();
    selectAsObject(query, promise);
    return promise.future();
  }

  /**
   * 执行查询
   *
   * @param query   SQL语句与参数
   * @param handler 返回结果 查询不到结果的时候返回null
   */
  void selectAsObject(SqlAndParams query, Handler<AsyncResult<JsonObject>> handler);

  /**
   * 执行查询
   *
   * @param query SQL语句与参数
   */
  default Future<List<JsonObject>> selectAsList(SqlAndParams query) {
    Promise<List<JsonObject>> promise = Promise.promise();
    selectAsList(query, promise);
    return promise.future();
  }

  /**
   * 执行查询
   *
   * @param query   SQL语句与参数
   * @param handler 返回结果 查询不到结果的时候返回不为null的空List
   */
  void selectAsList(SqlAndParams query, Handler<AsyncResult<List<JsonObject>>> handler);

  /**
   * 执行更新等操作得到受影响的行数
   *
   * @param query   SQL语句与参数
   */
  default Future<Integer> update(SqlAndParams query) {
    Promise<Integer> promise = Promise.promise();
    update(query, promise);
    return promise.future();
  }

  /**
   * 执行更新等操作得到受影响的行数
   *
   * @param query   SQL语句与参数
   * @param handler 结果处理器
   */
  void update(SqlAndParams query, Handler<AsyncResult<Integer>> handler);

  /**
   * 执行更新并操作结果,比如返回GENERATED_KEYS等:<br>
   * 获取GENERATED_KEYS方法:property传入JDBCPool.GENERATED_KEYS得到结果后判断row!=null就执行row.get对应类型数据
   *
   * @param query   SQL语句与参数
   */
  default <R> Future<R> updateResult(SqlAndParams query, PropertyKind<R> property) {
    Promise<R> promise = Promise.promise();
    updateResult(query, property, promise);
    return promise.future();
  }

  /**
   * 执行更新并操作结果,比如返回GENERATED_KEYS等:<br>
   * 获取GENERATED_KEYS方法:property传入JDBCPool.GENERATED_KEYS得到结果后判断row!=null就执行row.get对应类型数据
   *
   * @param query   SQL语句与参数
   * @param handler 结果处理器
   */
  <R> void updateResult(SqlAndParams query, PropertyKind<R> property, Handler<AsyncResult<R>> handler);

  /**
   * 批量操作
   *
   * @param query   SQL语句与批量参数
   */
  default Future<Integer> batch(SqlAndParams query){
    Promise<Integer> promise = Promise.promise();
    batch(query, promise);
    return promise.future();
  }
  /**
   * 批量操作
   *
   * @param query   SQL语句与批量参数
   * @param handler 返回结果
   */
  void batch(SqlAndParams query, Handler<AsyncResult<Integer>> handler);
}
