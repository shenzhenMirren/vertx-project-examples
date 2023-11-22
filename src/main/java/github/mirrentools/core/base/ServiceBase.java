package github.mirrentools.core.base;

import github.mirrentools.core.AppContext;
import github.mirrentools.core.ValidationResult;
import github.mirrentools.core.enums.ResultDataType;
import github.mirrentools.core.enums.ResultState;
import github.mirrentools.core.sql.SqlAndParams;
import github.mirrentools.core.utils.ResultUtil;
import github.mirrentools.core.utils.ValidationResultUtil;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务的基本实现
 *
 * @author <a href="https://github.com/shenzhenMirren">Mirren</a>
 */
public class ServiceBase extends JdbcBase implements DataValidatorHelper {
  /**
   * 日志工具
   */
  public final Logger LOG = LoggerFactory.getLogger(this.getClass());
  /**
   * 应用的上下文
   */
  public final AppContext content;

  public ServiceBase() {
    this.content = AppContext.context;
  }

  /**
   * 检查数据是否不通过
   *
   * @param log 日志的标题
   * @param exp 表达式加结果,如check(data)
   * @return 不通过=true,返回数据=null,状态=MISS_PARAMETER
   */
  public ValidationResult invalidate(String log, boolean exp) {
    return invalidate(log, exp, ResultState.MISS_PARAMETER);
  }

  /**
   * 检查数据是否不通过
   *
   * @param log  日志的标题
   * @param exp  表达式加结果,如check(data)
   * @param type 返回的数据类型
   * @return 不通过=true,返回数据=null,状态=MISS_PARAMETER
   */
  public ValidationResult invalidate(String log, boolean exp, ResultDataType type) {
    return invalidate(log, exp, ResultState.MISS_PARAMETER, type);
  }

  /**
   * 检查数据是否不通过
   *
   * @param log   日志的标题
   * @param exp   表达式加结果,如check(data)
   * @param state 返回的状态
   * @return 不通过=true,返回数据=null,状态=state
   */
  public ValidationResult invalidate(String log, boolean exp, ResultState state) {
    return invalidate(log, exp, state, ResultDataType.NULL);
  }

  /**
   * 检查数据是否不通过
   *
   * @param log   日志的标题
   * @param exp   表达式加结果,如check(data)
   * @param state 返回的状态
   * @param type  返回的数据类型
   * @return 不通过=true,返回数据=type,状态=state
   */
  public ValidationResult invalidate(String log, boolean exp, ResultState state, ResultDataType type) {
    if (exp) {
      if (LOG.isDebugEnabled()) {
        LOG.debug(String.format("执行数据检查->[%s]-->失败!", log));
      }
      return ValidationResultUtil.get(state, type);
    }
    return new ValidationResult(false, null);
  }

  /**
   * 检查数据是否不通过
   *
   * @param log  日志的标题
   * @param expr 表达式加结果,如check(data)
   * @return 不通过=true,返回数据=null,状态=MISS_PARAMETER
   */
  public ValidationResult invalidate(String log, boolean... expr) {
    return invalidate(log, ResultState.MISS_PARAMETER, expr);
  }

  /**
   * 检查数据是否不通过
   *
   * @param log   日志的标题
   * @param state 返回的状态
   * @param expr  表达式加结果,如check(data)
   * @return 不通过=true,返回数据=null,状态=state
   */
  public ValidationResult invalidate(String log, ResultState state, boolean... expr) {
    return invalidate(log, state, ResultDataType.NULL, expr);
  }

  /**
   * 检查数据是否不通过
   *
   * @param log  日志的标题
   * @param type 返回的数据类型
   * @param expr 表达式加结果,如check(data)
   * @return 不通过=true,返回数据=type,状态=MISS_PARAMETER
   */
  public ValidationResult invalidate(String log, ResultDataType type, boolean... expr) {
    return invalidate(log, ResultState.MISS_PARAMETER, type, expr);
  }

  /**
   * 检查数据是否不通过
   *
   * @param log   日志的标题
   * @param state 返回的状态
   * @param type  返回的数据类型
   * @param expr  表达式加结果,如check(data)
   * @return 不通过=true,返回数据=type,状态=state
   */
  public ValidationResult invalidate(String log, ResultState state, ResultDataType type, boolean... expr) {
    for (boolean exp : expr) {
      if (exp) {
        if (LOG.isDebugEnabled()) {
          LOG.debug(String.format("执行数据检查->[%s]-->失败!", log));
        }
        return ValidationResultUtil.get(state, type);
      }
    }
    return new ValidationResult(false, null);
  }

  /**
   * 查询数量
   *
   * @param log   日志的标题
   * @param query SQL与参数
   * @return 获取数据并格式化为响应类型结果, 成功状态=SUCCESS,失败状态=ERROR
   */
  public Future<JsonObject> responseLong(String log, SqlAndParams query) {
    return responseLong(log, null, query, ResultState.ERROR);
  }

  /**
   * 查询数量
   *
   * @param log   日志的标题
   * @param dbId  指定数据源的id
   * @param query SQL与参数
   * @return 获取数据并格式化为响应类型结果, 成功状态=SUCCESS,失败状态=ERROR
   */
  public Future<JsonObject> responseLong(String log, String dbId, SqlAndParams query) {
    return responseLong(log, dbId, query, ResultState.ERROR);
  }

  /**
   * 查询数量
   *
   * @param log   日志的标题
   * @param query SQL与参数
   * @param state 响应状态码
   * @return 获取数据并格式化为响应类型结果, 成功状态=SUCCESS,失败状态=state
   */
  public Future<JsonObject> responseLong(String log, SqlAndParams query, ResultState state) {
    return responseLong(log, null, query, state);
  }

  /**
   * 查询数量
   *
   * @param log   日志的标题
   * @param dbId  指定数据源的id
   * @param query SQL与参数
   * @param state 响应状态码
   * @return 获取数据并格式化为响应类型结果, 成功状态=SUCCESS,失败状态=state
   */
  public Future<JsonObject> responseLong(String log, String dbId, SqlAndParams query, ResultState state) {
    Promise<JsonObject> promise = Promise.promise();
    getLong(dbId, query, res -> resultLong(log, res, promise, state));
    return promise.future();
  }

  /**
   * 执行查询得到对象结果,如果结果为null则返回{},失败就返回Error状态码
   *
   * @param log   日志的标题
   * @param query SQL与参数
   * @return 获取数据并格式化为响应类型结果, 成功状态=SUCCESS,失败状态=ERROR
   */
  public Future<JsonObject> responseObject(String log, SqlAndParams query) {
    return responseObject(log, null, query, ResultState.ERROR);
  }

  /**
   * 执行查询得到对象结果,如果结果为null则返回{},失败就返回Error状态码
   *
   * @param log   日志的标题
   * @param query SQL与参数
   * @return 获取数据并格式化为响应类型结果, 成功状态=SUCCESS,失败状态=ERROR
   */
  public Future<JsonObject> responseObject(String log, String dbId, SqlAndParams query) {
    return responseObject(log, dbId, query, ResultState.ERROR);
  }

  /**
   * 执行查询得到对象结果,如果结果为null则返回{},失败就返回state状态码
   *
   * @param log   日志的标题
   * @param query SQL与参数
   * @param state 响应状态码
   * @return 获取数据并格式化为响应类型结果, 成功状态=SUCCESS,失败状态=state
   */
  public Future<JsonObject> responseObject(String log, SqlAndParams query, ResultState state) {
    return responseObject(log, null, query, state);
  }

  /**
   * 执行查询得到对象结果,如果结果为null则返回{},失败就返回state状态码
   *
   * @param log   日志的标题
   * @param dbId  指定数据源的id
   * @param query SQL与参数
   * @param state 响应状态码
   * @return 获取数据并格式化为响应类型结果, 成功状态=SUCCESS,失败状态=state
   */
  public Future<JsonObject> responseObject(String log, String dbId, SqlAndParams query, ResultState state) {
    Promise<JsonObject> promise = Promise.promise();
    getObject(dbId, query, res -> resultJsonOrNew(log, res, promise, state));
    return promise.future();
  }


  /**
   * 执行查询得到List结果,如果结果为null则返回[],失败就返回Error状态码
   *
   * @param log   日志的标题
   * @param query SQL与参数
   * @return 获取数据并格式化为响应类型结果, 成功状态=SUCCESS,失败状态=ERROR
   */
  public Future<JsonObject> responseList(String log, SqlAndParams query) {
    return responseList(log, null, query, ResultState.ERROR);
  }

  /**
   * 执行查询得到List结果,如果结果为null则返回[],失败就返回Error状态码
   *
   * @param log   日志的标题
   * @param dbId  指定数据源的id
   * @param query SQL与参数
   * @return 获取数据并格式化为响应类型结果, 成功状态=SUCCESS,失败状态=ERROR
   */
  public Future<JsonObject> responseList(String log, String dbId, SqlAndParams query) {
    return responseList(log, dbId, query, ResultState.ERROR);
  }

  /**
   * 执行查询得到List结果,如果结果为null则返回[],失败就返回Error状态码
   *
   * @param log   日志的标题
   * @param query SQL与参数
   * @param state 响应状态码
   * @return 获取数据并格式化为响应类型结果, 成功状态=SUCCESS,失败状态=ERROR
   */
  public Future<JsonObject> responseList(String log, SqlAndParams query, ResultState state) {
    return responseList(log, null, query, state);
  }

  /**
   * 执行查询得到List结果,如果结果为null则返回[],失败就返回Error状态码
   *
   * @param log   日志的标题
   * @param dbId  指定数据源的id
   * @param query SQL与参数
   * @param state 响应状态码
   * @return 获取数据并格式化为响应类型结果, 成功状态=SUCCESS,失败状态=state
   */
  public Future<JsonObject> responseList(String log, String dbId, SqlAndParams query, ResultState state) {
    Promise<JsonObject> promise = Promise.promise();
    getList(dbId, query, res -> resultArrayOrNew(log, res, promise, state));
    return promise.future();
  }

  /**
   * 执行查询得到对象结果,如果结果为null则返回0,失败就返回Error状态码
   *
   * @param log   日志的标题
   * @param query SQL与参数
   * @return 获取数据并格式化为响应类型, 成功状态=SUCCESS,失败状态=ERROR
   */
  public Future<JsonObject> responseInt(String log, SqlAndParams query) {
    return responseInt(log, null, query, ResultState.ERROR);
  }

  /**
   * 执行查询得到对象结果,如果结果为null则返回0,失败就返回Error状态码
   *
   * @param log   日志的标题
   * @param query SQL与参数
   * @return 获取数据并格式化为响应类型结果, 成功状态=SUCCESS,失败状态=ERROR
   */
  public Future<JsonObject> responseInt(String log, String dbId, SqlAndParams query) {
    return responseInt(log, dbId, query, ResultState.ERROR);
  }


  /**
   * 执行查询得到对象结果,如果结果为null则返回0,失败就返回Error状态码
   *
   * @param log   日志的标题
   * @param query SQL与参数
   * @param state 响应状态码
   * @return 获取数据并格式化为响应类型结果, 成功状态=SUCCESS,失败状态=state
   */
  public Future<JsonObject> responseInt(String log, SqlAndParams query, ResultState state) {
    return responseInt(log, null, query, state);
  }

  /**
   * 执行查询得到对象结果,如果结果为null则返回0,失败就返回Error状态码
   *
   * @param log   日志的标题
   * @param dbId  指定数据源的id
   * @param query SQL与参数
   * @param state 响应状态码
   * @return 获取数据并格式化为响应类型结果, 成功状态=SUCCESS,失败状态=state
   */
  public Future<JsonObject> responseInt(String log, String dbId, SqlAndParams query, ResultState state) {
    Promise<JsonObject> promise = Promise.promise();
    updateInt(dbId, query, res -> resultInt(log, res, promise, state));
    return promise.future();
  }

  /**
   * 执行操作结果返回0或1,返回状态码ERROR
   *
   * @param log   日志的标题
   * @param query SQL与参数
   * @return 获取数据并格式化为响应类型, 成功状态=SUCCESS,失败状态=ERROR
   */
  public Future<JsonObject> responseZeroOrOne(String log, SqlAndParams query) {
    return responseZeroOrOne(log, null, query, ResultState.ERROR);
  }

  /**
   * 执行操作结果返回0或1,返回指定状态码
   *
   * @param log   日志的标题
   * @param query SQL与参数
   * @param state 响应状态码
   * @return 获取数据并格式化为响应类型, 成功状态=SUCCESS,失败状态=state
   */
  public Future<JsonObject> responseZeroOrOne(String log, SqlAndParams query, ResultState state) {
    return responseZeroOrOne(log, null, query, state);
  }


  /**
   * 执行操作结果返回0或1,成功返回状态码ERROR
   *
   * @param log   日志的标题
   * @param dbId  指定数据源的id
   * @param query SQL与参数
   * @return 获取数据并格式化为响应类型结果, 成功状态=SUCCESS,失败状态=ERROR
   */
  public Future<JsonObject> responseZeroOrOne(String log, String dbId, SqlAndParams query) {
    return responseZeroOrOne(log, dbId, query, ResultState.ERROR);
  }

  /**
   * 执行操作结果返回0或1,返回指定状态码
   *
   * @param log   日志的标题
   * @param dbId  指定数据源的id
   * @param query SQL与参数
   * @param state 响应状态码
   * @return 获取数据并格式化为响应类型结果, 成功状态=SUCCESS,失败状态=state
   */
  public Future<JsonObject> responseZeroOrOne(String log, String dbId, SqlAndParams query, ResultState state) {
    Promise<JsonObject> promise = Promise.promise();
    updateInt(dbId, query, res -> resultZeroOrOne(log, res, promise, state));
    return promise.future();
  }


  /**
   * 打印日志并得到返回结果int类型,如果为null则返回0,状态码成功=Success,失败=Error
   *
   * @param log     日志名称的标题(那个操作)
   * @param res     查询结果
   * @param promise 操作结果
   */
  public void resultInt(String log, AsyncResult<Integer> res, Promise<JsonObject> promise) {
    if (res.succeeded()) {
      int result = res.result() == null ? 0 : res.result();
      if (LOG.isDebugEnabled()) {
        LOG.debug("执行[" + log + "]-->结果:" + result);
      }
      promise.complete(ResultUtil.getSuccess(result));
    } else {
      LOG.error("执行[" + log + "]-->失败:", res.cause());
      promise.complete(ResultUtil.getErrorZero());
    }
  }

  /**
   * 打印日志并得到返回结果int类型,如果为null则返回0,状态码成功=Success,失败=state
   *
   * @param log     日志名称的标题(那个操作)
   * @param res     查询结果
   * @param promise 操作结果
   * @param state   失败返回的状态
   */
  public void resultInt(String log, AsyncResult<Integer> res, Promise<JsonObject> promise, ResultState state) {
    if (res.succeeded()) {
      int result = res.result() == null ? 0 : res.result();
      if (LOG.isDebugEnabled()) {
        LOG.debug("执行[" + log + "]-->结果:" + result);
      }
      promise.complete(ResultUtil.getSuccess(result));
    } else {
      LOG.error("执行[" + log + "]-->失败:", res.cause());
      promise.complete(ResultUtil.getZero(state));
    }
  }

  /**
   * 打印日志并得到返回结果int类型,如果为null则返回0,大于1返回1,状态码成功=Success,失败=Error
   *
   * @param log     日志名称的标题(那个操作)
   * @param res     查询结果
   * @param promise 操作结果
   */
  public void resultZeroOrOne(String log, AsyncResult<Integer> res, Promise<JsonObject> promise) {
    if (res.succeeded()) {
      int result = res.result() == null ? 0 : res.result();
      if (LOG.isDebugEnabled()) {
        LOG.debug("执行[" + log + "]-->结果:" + result);
      }
      if (result > 1) {
        result = 1;
      }
      promise.complete(ResultUtil.getSuccess(result));
    } else {
      LOG.error("执行[" + log + "]-->失败:", res.cause());
      promise.complete(ResultUtil.getErrorZero());
    }
  }

  /**
   * 打印日志并得到返回结果int类型,如果为null则返回0,大于1返回1,状态码成功=Success,失败=state状态类
   *
   * @param log     日志名称的标题(那个操作)
   * @param res     查询结果
   * @param promise 操作结果
   * @param state   失败返回的状态
   */
  public void resultZeroOrOne(String log, AsyncResult<Integer> res, Promise<JsonObject> promise, ResultState state) {
    if (res.succeeded()) {
      int result = res.result() == null ? 0 : res.result();
      if (LOG.isDebugEnabled()) {
        LOG.debug("执行[" + log + "]-->结果:" + result);
      }
      if (result > 1) {
        result = 1;
      }
      promise.complete(ResultUtil.getSuccess(result));
    } else {
      LOG.error("执行[" + log + "]-->失败:", res.cause());
      promise.complete(ResultUtil.getZero(state));
    }
  }

  /**
   * 打印日志并得到返回结果long类型,如果为null则返回0,状态码成功=Success,失败=Error
   *
   * @param log     日志名称的标题(那个操作)
   * @param res     查询结果
   * @param promise 操作结果
   */
  public void resultLong(String log, AsyncResult<Long> res, Promise<JsonObject> promise) {
    if (res.succeeded()) {
      long result = res.result() == null ? 0L : res.result();
      if (LOG.isDebugEnabled()) {
        LOG.debug("执行[" + log + "]-->结果:" + result);
      }
      promise.complete(ResultUtil.getSuccess(result));
    } else {
      LOG.error("执行[" + log + "]-->失败:", res.cause());
      promise.complete(ResultUtil.getErrorZero());
    }
  }

  /**
   * 打印日志并得到返回结果long类型,如果为null则返回0,状态码成功=Success,失败=state
   *
   * @param log     日志名称的标题(那个操作)
   * @param res     查询结果
   * @param promise 操作结果
   * @param state   失败返回的状态
   */
  public void resultLong(String log, AsyncResult<Long> res, Promise<JsonObject> promise, ResultState state) {
    if (res.succeeded()) {
      long result = res.result() == null ? 0L : res.result();
      if (LOG.isDebugEnabled()) {
        LOG.debug("执行[" + log + "]-->结果:" + result);
      }
      promise.complete(ResultUtil.getSuccess(result));
    } else {
      LOG.error("执行[" + log + "]-->失败:", res.cause());
      promise.complete(ResultUtil.getZero(state));
    }
  }

  /**
   * 打印日志并得到返回结果json类型,如果为null则返回{},状态码成功=Success,失败=Error
   *
   * @param log     日志名称的标题(那个操作)
   * @param res     查询结果
   * @param promise 操作结果
   */
  public void resultJsonOrNew(String log, AsyncResult<JsonObject> res, Promise<JsonObject> promise) {
    if (res.succeeded()) {
      JsonObject result = res.result() == null ? new JsonObject() : res.result();
      if (LOG.isDebugEnabled()) {
        LOG.debug("执行[" + log + "]-->结果:" + result);
      }
      promise.complete(ResultUtil.getSuccess(result));
    } else {
      LOG.error("执行[" + log + "]-->失败:", res.cause());
      promise.complete(ResultUtil.getErrorNewJson());
    }
  }

  /**
   * 打印日志并得到返回结果json类型,如果为null则返回{},状态码成功=Success,失败=Error
   *
   * @param log     日志名称的标题(那个操作)
   * @param res     查询结果
   * @param promise 操作结果
   * @param state   失败返回的状态
   */
  public void resultJsonOrNew(String log, AsyncResult<JsonObject> res, Promise<JsonObject> promise, ResultState state) {
    if (res.succeeded()) {
      JsonObject result = res.result() == null ? new JsonObject() : res.result();
      if (LOG.isDebugEnabled()) {
        LOG.debug("执行[" + log + "]-->结果:" + result);
      }
      promise.complete(ResultUtil.getSuccess(result));
    } else {
      LOG.error("执行[" + log + "]-->失败:", res.cause());
      promise.complete(ResultUtil.getNewJson(state));
    }
  }


  /**
   * 打印日志并得到返回结果array类型,如果为null则返回[],状态码成功=Success,失败=Error
   *
   * @param log     日志名称的标题(那个操作)
   * @param res     查询结果
   * @param promise 操作结果
   */
  public void resultArrayOrNew(String log, AsyncResult<List<JsonObject>> res, Promise<JsonObject> promise) {
    if (res.succeeded()) {
      List<JsonObject> result = res.result() == null ? new ArrayList<>() : res.result();
      if (LOG.isDebugEnabled()) {
        LOG.debug("执行[" + log + "]-->结果:" + result);
      }
      promise.complete(ResultUtil.getSuccess(result));
    } else {
      LOG.error("执行[" + log + "]-->失败:", res.cause());
      promise.complete(ResultUtil.getErrorNewArray());
    }
  }


  /**
   * 打印日志并得到返回结果array类型,如果为null则返回[],状态码成功=Success,失败=Error
   *
   * @param log     日志名称的标题(那个操作)
   * @param res     查询结果
   * @param promise 操作结果
   * @param state   失败返回的状态
   */
  public void resultArrayOrNew(String log, AsyncResult<List<JsonObject>> res, Promise<JsonObject> promise, ResultState state) {
    if (res.succeeded()) {
      List<JsonObject> result = res.result() == null ? new ArrayList<>() : res.result();
      if (LOG.isDebugEnabled()) {
        LOG.debug("执行[" + log + "]-->结果:" + result);
      }
      promise.complete(ResultUtil.getSuccess(result));
    } else {
      LOG.error("执行[" + log + "]-->失败:", res.cause());
      promise.complete(ResultUtil.getNewArray(state));
    }
  }

}
