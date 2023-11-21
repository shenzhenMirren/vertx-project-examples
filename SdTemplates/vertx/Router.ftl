package ${content.items.router.packageName};
<#assign assign_ClassName = content.items.router.className>
<#assign assign_ServiceName = content.items.service.className>
import ${content.items.service.packageName}.${content.items.service.className};

import github.mirrentools.core.base.RouterBase;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * ${content.content.pascalName}的接口服务
 *
 */
public class ${assign_ClassName} extends RouterBase {
	/** 数据服务接口 */
	private final ${assign_ServiceName} service;

	private ${assign_ClassName}() {
		super();
		this.service = ${assign_ServiceName}.create();
	}

	/**
	 * 启动服务
	 *
	 * @param router Router
	 */
	public static void startRouter(Router router) {
		${assign_ClassName} instance = new ${assign_ClassName}();
		// 获取所有数据
		router.get("/private/api/${content.content.pascalName}/find").handler(instance::find);
		// 保存数据
		router.post("/private/api/${content.content.pascalName}/save").handler(instance::save);
		<#if content.content.primaryField??>
		// 获取指定数据
		router.get("/private/api/${content.content.pascalName}/get").handler(instance::get);
		// 删除数据
		router.post("/private/api/${content.content.pascalName}/delete").handler(instance::delete);
		</#if>
	}

  private void find(RoutingContext rct) {
    process(rct, service::find);
  }

  private void save(RoutingContext rct) {
    process(rct, service::save);
  }
  <#if content.content.primaryField??>

  private void get(RoutingContext rct) {
    process(rct, service::get);
  }

  private void delete(RoutingContext rct) {
    process(rct, service::delete);
  }
  </#if>

}
