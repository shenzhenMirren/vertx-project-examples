package github.mirrentools.app.service;

import github.mirrentools.app.service.impl.StudentBatchServiceImpl;
import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

/**
 * StudentBatch数据服务接口
 */
public interface StudentBatchService {

	/**
	 * 创建一个实例
	 */
	static StudentBatchService create() {
		return new StudentBatchServiceImpl();
	}


	/**
	 * 新增数据
	 *
	 * @param params 请求参数
	 */
	Future<JsonObject> batch(MultiMap params);

}
