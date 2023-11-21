package github.mirrentools.app.router;
import github.mirrentools.app.service.ClassesService;

import github.mirrentools.core.base.RouterBase;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * Classes的接口服务
 *
 */
public class ClassesRouter extends RouterBase {
	/** 数据服务接口 */
	private final ClassesService service;

	private ClassesRouter() {
		super();
		this.service = ClassesService.create();
	}

	/**
	 * 启动服务
	 *
	 * @param router Router
	 */
	public static void startRouter(Router router) {
		ClassesRouter instance = new ClassesRouter();
		// 获取所有数据
		router.get("/private/api/Classes/find").handler(instance::find);
		// 保存数据
		router.post("/private/api/Classes/save").handler(instance::save);
		// 获取指定数据
		router.get("/private/api/Classes/get").handler(instance::get);
		// 删除数据
		router.post("/private/api/Classes/delete").handler(instance::delete);
	}

  private void find(RoutingContext rct) {
    process(rct, service::find);
  }

  private void save(RoutingContext rct) {
    process(rct, service::save);
  }

  private void get(RoutingContext rct) {
    process(rct, service::get);
  }

  private void delete(RoutingContext rct) {
    process(rct, service::delete);
  }

}
