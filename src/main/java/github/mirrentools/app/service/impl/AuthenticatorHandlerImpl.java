package github.mirrentools.app.service.impl;

import github.mirrentools.core.CoreConstants;
import github.mirrentools.core.SessionDataStore;
import github.mirrentools.core.base.ServiceBase;
import github.mirrentools.core.enums.ResultState;
import github.mirrentools.core.sql.SqlAndParams;
import github.mirrentools.core.utils.ResultUtil;
import github.mirrentools.core.utils.TokenUtil;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.sqlclient.Tuple;
import github.mirrentools.app.service.AuthenticatorHandler;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 认证处理器的Token版默认实现
 * 需要注意的是获取用户的userId与userToken的参数要跟实际的一样
 */
public class AuthenticatorHandlerImpl extends ServiceBase implements AuthenticatorHandler {
  /**
   * 不需要进行认证,直接放行的path
   */
  private final Set<String> excludedPaths;
  /**
   * 会话数据管理器
   */
  private final SessionDataStore sessionDataStore = content.sessionDataStore();

  public AuthenticatorHandlerImpl(Set<String> excludedPaths) {
    this.excludedPaths = excludedPaths == null ? new HashSet<>() : excludedPaths;
  }

  @Override
  public void verify(RoutingContext rct) {
    String path = rct.request().path();
    LOG.debug("执行权限认证-->:path:" + path);
    LOG.debug("\n" + rct.request().params());
    if (excludedPaths.contains(path)) {
      LOG.debug("执行权限认证-->成功:在过滤名单中...");
      rct.next();
    } else {
      HttpServerResponse response = rct.response().putHeader(CoreConstants.CONTENT_TYPE, CoreConstants.CONTENT_JSON_UTF8);
      String userId = rct.request().getParam("uid");// 用户携带的id
      String userToken = rct.request().getParam("token");// 用户携带的token
      if (check(userId, userToken)) {
        LOG.debug("执行权限认证-->失败:用户的id或者token有空");
        response.end(ResultUtil.getMissParameterNull().toBuffer());
        return;
      }
      Object sysToken = sessionDataStore.get(userId, CoreConstants.SESSION_KEY_USER_TOKEN);
      if (sysToken != null) {
        if (Objects.equals(userToken, sysToken.toString())) {
          LOG.debug("执行权限认证-->成功:从session中获取token:" + sysToken);
          rct.next();
          return;
        } else {
          boolean check = TokenUtil.isNewVersion(sysToken.toString(), userToken);
          if (!check) {
            LOG.debug("执行权限认证->检查用户的token比系统的token小或无效!");
            response.end(ResultUtil.getNull(ResultState.UNAUTHORIZED).toBuffer());
            return;
          }
        }
      }
      getToken(userId)
        .onSuccess(token -> {
          LOG.debug("执行权限认证->获取用户token-->结果:" + token);
          if (token == null) {
            LOG.debug("执行权限认证-->不通过:从数据库获取token为空!");
            response.end(ResultUtil.getNull(ResultState.UNAUTHORIZED).toBuffer());
          } else {
            if (!Objects.equals(userToken, token)) {
              LOG.debug("执行权限认证-->不通过:token不匹配!");
              response.end(ResultUtil.getNull(ResultState.UNAUTHORIZED).toBuffer());
            } else {
              LOG.debug("执行权限认证-->认证通过!");
              sessionDataStore.put(userId, CoreConstants.SESSION_KEY_USER_TOKEN, token);
              rct.next();
            }
          }
        }).onFailure(err -> {
          LOG.error("执行权限认证->获取用户token-->失败:", err);
          response.end(ResultUtil.getErrorNull().toBuffer());
        });
    }
  }

  /**
   * 获取用户的token
   *
   * @param userId 用户的id
   * @return 获取不到或失败就返回null
   */
  private Future<String> getToken(String userId) {
    Promise<String> promise = Promise.promise();
    String sql = "SELECT token FROM user_data where uid=?";
    getObject(new SqlAndParams(sql, Tuple.tuple().addString(userId)))
      .onSuccess(data -> {
        JsonObject result = data == null ? new JsonObject() : data;
        promise.complete(result.getString("token"));
      })
      .onFailure(promise::fail);
    return promise.future();
  }

}
