package github.mirrentools.app.service;

import github.mirrentools.app.service.impl.AuthenticatorHandlerImpl;
import io.vertx.ext.web.RoutingContext;

import java.util.Set;

/**
 * 权限验证处理器
 */
public interface AuthenticatorHandler {
  /**
   * 创建一个token鉴权处理器
   *
   * @param excludedPaths 不需要鉴权的path
   */
  static AuthenticatorHandler create(Set<String> excludedPaths) {
    return new AuthenticatorHandlerImpl(excludedPaths);
  }

  /**
   * 如果认证通过就调用rct.next()放行,如果认证不通过就响应结果给用户
   *
   * @param rct 路由器的上下文
   */
  void verify(RoutingContext rct);
}
