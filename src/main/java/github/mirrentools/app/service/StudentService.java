package github.mirrentools.app.service;

import github.mirrentools.app.service.impl.StudentServiceImpl;

import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

/**
 * Student数据服务接口
 */
public interface StudentService {

	/**
	 * 创建一个实例
	 */
	static StudentService create() {
		return new StudentServiceImpl();
	}

	/**
	 * 获取所有数据
	 *
	 * @param params 请求参数
	 */
	Future<JsonObject> find(MultiMap params);

	/**
	 * 新增数据
	 *
	 * @param params 请求参数
	 */
	Future<JsonObject> save(MultiMap params);

	/**
	 * 获取指定数据
	 *
	 * @param params 请求参数
	 */
	Future<JsonObject> get(MultiMap params);

	/**
	 * 删除指定数据
	 *
	 * @param params 请求参数
	 */
	Future<JsonObject> delete(MultiMap params);
}
