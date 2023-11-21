package github.mirrentools.core.base;

import github.mirrentools.core.utils.ResultUtil;
import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import github.mirrentools.core.AppContext;
import github.mirrentools.core.CoreConstants;

import java.util.function.Function;

/**
 * 路由的基本实现
 *
 * @author <a href="https://github.com/shenzhenMirren">Mirren</a>
 */
public class RouterBase {
  /**
   * 日志工具
   */
  public final Logger LOG = LoggerFactory.getLogger(this.getClass());
  /**
   * 这个示例的id
   */
  private final long routerId;
  /**
   * 每个请求的自增id
   */
  private long counter = 0L;

  public RouterBase() {
    this.routerId = AppContext.context.counter();
  }

  /**
   * 请求的id
   */
  public String requestId() {
    return routerId + "-" + (counter++);
  }

  /**
   * 打印日志并调用响应服务,默认的日志id是当前请求的[method:path]
   *
   * @param rct     请求的上下文
   * @param handler 处理器
   */
  public void process(RoutingContext rct, Function<MultiMap, Future<JsonObject>> handler) {
    String id = "[" + rct.request().method() + ":" + rct.request().path() + "]";
    process(id, rct, handler);
  }

  /**
   * 打印日志并调用响应服务
   *
   * @param logId   日志的id
   * @param rct     请求的上下文
   * @param handler 处理器
   */
  public void process(String logId, RoutingContext rct, Function<MultiMap, Future<JsonObject>> handler) {
    MultiMap params = rct.request().params();
    String requestId = requestId();
    long requestTime = System.currentTimeMillis();
    LOG.info(requestId + "->执行" + logId + "-->请求参数:\n" + params);
    handler.apply(params).onComplete(res -> {
      rct.response().putHeader(CoreConstants.CONTENT_TYPE, CoreConstants.CONTENT_JSON_UTF8);
      try {
        rct.response().end(res.result().toBuffer());
      } catch (Exception e) {
        LOG.error("执行" + logId + "-->响应异常:", e);
        rct.response().end(ResultUtil.getErrorNull().toBuffer());
      }
      LOG.info(requestId + "->执行时间-->[" + (System.currentTimeMillis() - requestTime) + "]");
    });
  }

  /**
   * 打印日志并调用响应服务,默认的日志id是当前请求的[method:path]
   *
   * @param rct     请求的上下文
   * @param handler 处理器
   */
  public void processWithRC(RoutingContext rct, Function<RoutingContext, Future<JsonObject>> handler) {
    String id = "[" + rct.request().method() + ":" + rct.request().path() + "]";
    processWithRC(id, rct, handler);
  }

  /**
   * 打印日志并调用响应服务
   *
   * @param logId   日志的id
   * @param rct     请求的上下文
   * @param handler 处理器
   */
  public void processWithRC(String logId, RoutingContext rct, Function<RoutingContext, Future<JsonObject>> handler) {
    MultiMap params = rct.request().params();
    String requestId = requestId();
    long requestTime = System.currentTimeMillis();
    LOG.info(requestId + "->执行" + logId + "-->请求参数:\n" + params);
    handler.apply(rct).onComplete(res -> {
      rct.response().putHeader(CoreConstants.CONTENT_TYPE, CoreConstants.CONTENT_JSON_UTF8);
      try {
        rct.response().end(res.result().toBuffer());
      } catch (Exception e) {
        LOG.error("执行" + logId + "-->响应异常:", e);
        rct.response().end(ResultUtil.getErrorNull().toBuffer());
      }
      LOG.info(requestId + "->执行时间-->[" + (System.currentTimeMillis() - requestTime) + "]");
    });
  }
}
