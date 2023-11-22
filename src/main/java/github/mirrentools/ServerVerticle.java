package github.mirrentools;

import github.mirrentools.app.router.ClassesRouter;
import github.mirrentools.app.router.StudentRouter;
import github.mirrentools.app.router.TemplateRouter;
import github.mirrentools.app.service.AuthenticatorHandler;
import github.mirrentools.core.CoreConstants;
import github.mirrentools.core.enums.ResultState;
import github.mirrentools.core.utils.ResultUtil;
import github.mirrentools.core.utils.RouterUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.FaviconHandler;

import java.util.HashSet;
import java.util.Set;

/**
 * 应用程序逻辑,这个Verticle可以被多实例部署
 *
 * @author <a href="https://github.com/shenzhenMirren">Mirren</a>
 */
public class ServerVerticle extends AbstractVerticle {
  /**
   * body处理器最大允许接受多少内容
   */
  public static final Integer BODY_LIMIT = 65535;

  /**
   * 不需要鉴权的路由(开放API)
   */
  private void initPublicRouter(Router router) {
    TemplateRouter.startRouter(router);
  }

  /**
   * 认证路由器
   */
  private void initAuthRouter(Router router) {
    //这里做了如果是测试模式则不需要进入鉴权,方便调试
    Set<String> excludedPaths = new HashSet<>();
    AuthenticatorHandler handler = AuthenticatorHandler.create(excludedPaths);
    router.route("/private/api/*").handler(handler::verify);
  }

  /**
   * 需要鉴权的处理器(私有API)
   */
  private void initPrivateRouter(Router router) {
    StudentRouter.startRouter(router);
    ClassesRouter.startRouter(router);
  }


  @Override
  public void start(Promise<Void> startPromise) {
    Router router = Router.router(vertx);
    router.route().handler(CorsHandler.create());
    router.route().handler(BodyHandler.create().setBodyLimit(BODY_LIMIT));
    router.route().handler(FaviconHandler.create(vertx, "favicon.ico"));

    //这里添加了数据监控路由
    initMicrometerMetrics(router);
    //开放的API
    initPublicRouter(router);
    //鉴权处理器
    initAuthRouter(router);
    //私有的API
    initPrivateRouter(router);

    //异常跟失败的router
    RouterUtil routerUtil = new RouterUtil();
    // 统一失败处理
    router.route("/*").failureHandler(routerUtil::failureHandler);
    // 如果进入这一步代表用户请求了不存在的路径
    router.errorHandler(404, routerUtil::the404Handler);
    router.errorHandler(405, routerUtil::the405Handler);
    router.errorHandler(500, routerUtil::failureHandler);
    Integer httpPort = config().getInteger("httpPort", 80);
    HttpServerOptions serverOptions = new HttpServerOptions();
    if (vertx.isNativeTransportEnabled()) {
      serverOptions.setTcpFastOpen(true).setTcpCork(true).setTcpQuickAck(true).setReusePort(true);
    }
    serverOptions.setMaxFormAttributeSize(BODY_LIMIT);
    Future<HttpServer> serverFuture = vertx.createHttpServer(serverOptions).requestHandler(router).listen(httpPort);
    serverFuture.onSuccess(r -> {
      System.out.println("Server running port: 8080");
      startPromise.complete();
    }).onFailure(err -> {
      System.out.println("Start Server Fail:" + err);
      startPromise.fail(err);
    });
  }

  /**
   * 初始化监控数据
   *
   * @param router 路由器
   */
  private void initMicrometerMetrics(Router router) {
    if (CoreConstants.MONITORING_ENABLED) {
      return;
    }
    router.route(CoreConstants.MONITORING_ROUTE_PATH).blockingHandler(rct ->
      vertx.eventBus().<JsonObject>request(CoreConstants.EVENT_BUS_METRICS, "",
        res -> {
          rct.response().putHeader(CoreConstants.CONTENT_TYPE, CoreConstants.CONTENT_JSON_UTF8);
          try {
            if (res.succeeded()) {
              JsonObject body = res.result().body() == null ? new JsonObject() : res.result().body();
              rct.response().end(ResultUtil.getSuccess(body).toBuffer());
            } else {
              rct.response().end(ResultUtil.get(ResultState.ERROR, res.cause().toString()).toBuffer());
            }
          } catch (Exception e) {
            rct.response().end(ResultUtil.get(ResultState.ERROR, e.toString()).toBuffer());
          }
        }
      )
    );
  }

}
