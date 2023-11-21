package ${content.items.service.packageName};

import ${content.items.serviceImpl.packageName}.${content.items.serviceImpl.className};

import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

/**
 * ${content.content.pascalName}数据服务接口
 */
public interface ${content.items.service.className} {

	/**
	 * 创建一个实例
	 */
	static ${content.items.service.className} create() {
		return new ${content.items.serviceImpl.className}();
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
  <#if content.content.primaryField??>

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
  </#if>
}
