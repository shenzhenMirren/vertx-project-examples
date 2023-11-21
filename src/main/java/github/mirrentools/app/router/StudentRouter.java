package github.mirrentools.app.router;
import github.mirrentools.app.service.StudentService;

import github.mirrentools.core.base.RouterBase;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * Student的接口服务
 *
 */
public class StudentRouter extends RouterBase {
	/** 数据服务接口 */
	private final StudentService service;

	private StudentRouter() {
		super();
		this.service = StudentService.create();
	}

	/**
	 * 启动服务
	 *
	 * @param router Router
	 */
	public static void startRouter(Router router) {
		StudentRouter instance = new StudentRouter();
		// 获取所有数据
		router.get("/private/api/Student/find").handler(instance::find);
		// 保存数据
		router.post("/private/api/Student/save").handler(instance::save);
		// 获取指定数据
		router.get("/private/api/Student/get").handler(instance::get);
		// 删除数据
		router.post("/private/api/Student/delete").handler(instance::delete);
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
