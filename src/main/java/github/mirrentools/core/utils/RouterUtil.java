package github.mirrentools.core.utils;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import github.mirrentools.core.CoreConstants;
import github.mirrentools.core.enums.ResultState;

/**
 * 路由器工具
 *
 * @author <a href="https://github.com/shenzhenMirren">Mirren</a>
 */
public class RouterUtil {
  private final Logger LOG = LogManager.getLogger(RouterUtil.class);

  /**
   * 失败处理器
   *
   * @param rct 上下文
   */
  public void failureHandler(RoutingContext rct) {
    Throwable failure = rct.failure();
    LOG.error("发生了异常:", failure);
    if (rct.response().ended()) {
      return;
    }
    HttpServerResponse response = rct.response().putHeader(CoreConstants.CONTENT_TYPE, CoreConstants.CONTENT_JSON_UTF8);
    if (failure instanceof NullPointerException) {
      response.end(ResultFormat.formatAsNull(ResultState.MISS_PARAMETER).toBuffer());
    } else {
      response.end(ResultFormat.formatAsNull(ResultState.ERROR).toBuffer());
    }
  }

  /**
   * 访问了不存在的路径处理器
   *
   * @param rct 上下文
   */
  public void the404Handler(RoutingContext rct) {
    LOG.info("用户: " + rct.request().remoteAddress().host() + "请求了不存的路径: " + rct.request().method() + ":" + rct.request().path());
    rct.response().putHeader(CoreConstants.CONTENT_TYPE, CoreConstants.CONTENT_JSON_UTF8).end(ResultFormat.formatAsNull(ResultState.NOT_FOUND).toBuffer());
  }

  /**
   * 使用了错误的Method请求
   *
   * @param rct 上下文
   */
  public void the405Handler(RoutingContext rct) {
    LOG.info("用户: " + rct.request().remoteAddress().host() + "请求的Method无效: " + rct.request().method() + ":" + rct.request().path());
    rct.response().putHeader(CoreConstants.CONTENT_TYPE, CoreConstants.CONTENT_JSON_UTF8).end(ResultFormat.formatAsNull(ResultState.METHOD_NOT_ALLOWED).toBuffer());
  }

}
