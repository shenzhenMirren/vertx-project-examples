package github.mirrentools.app.router;

import github.mirrentools.app.service.TemplateService;
import github.mirrentools.core.base.RouterBase;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class TemplateRouter extends RouterBase {

  private final TemplateService service;

  private TemplateRouter() {
    service = TemplateService.create();
  }

  public static void startRouter(Router router) {
    TemplateRouter instance = new TemplateRouter();
    router.post("/test").handler(instance::get);
    router.get("/test2").handler(instance::get2);
  }

  private void get(RoutingContext rct) {
    process(rct, service::find);
  }

  private void get2(RoutingContext rct) {
    process(rct, service::merge);
  }


}
