package github.mirrentools.app.router;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import github.mirrentools.app.service.StudentBatchService;
import github.mirrentools.core.base.RouterBase;

/**
 * StudentBatch的接口服务
 *
 */
public class StudentBatchRouter extends RouterBase {
	/** 数据服务接口 */
	private final StudentBatchService service;

	private StudentBatchRouter() {
		super();
		this.service = StudentBatchService.create();
	}

	/**
	 * 启动服务
	 *
	 * @param router Router
	 */
	public static void startRouter(Router router) {
		StudentBatchRouter instance = new StudentBatchRouter();
		// 批量保存数据
		router.post("/public/api/StudentAuto/save/batch").handler(instance::batch);

	}

  private void batch(RoutingContext rct) {
    process(rct, service::batch);
  }

}
