package github.mirrentools.app.service;

import github.mirrentools.app.service.impl.TemplateServiceImpl;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;

public interface TemplateService {
  static TemplateService create(){
    return new TemplateServiceImpl();
  }
  /**
   *
   * @param params 请求参数
   */
  Future<JsonObject> find(MultiMap params);

  /**
   *
   * @param params 请求参数
   */
  Future<JsonObject> merge(MultiMap params);
}
