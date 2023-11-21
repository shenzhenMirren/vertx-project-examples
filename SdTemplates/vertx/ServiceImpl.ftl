package ${content.items.serviceImpl.packageName};
<#assign assign_ClassName = content.items.serviceImpl.className>
<#assign assign_SqlName = content.items.sql.camelName>

import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import ${content.items.service.packageName}.${content.items.service.className};
import ${content.items.model.packageName}.${content.items.model.className};
import ${content.items.sql.packageName}.${content.items.sql.className};
import github.mirrentools.core.ValidationResult;
import github.mirrentools.core.base.ServiceBase;
import github.mirrentools.core.enums.ResultDataType;
import github.mirrentools.core.utils.FutureUtil;
import github.mirrentools.core.utils.ParamsUtil;

/**
 * ${content.content.pascalName}数据服务接口的默认实现
 */
public class ${assign_ClassName} extends ServiceBase implements ${content.items.service.className} {
	/** SQL操作语句 */
	private final ${content.items.sql.className} ${assign_SqlName};

	/**
	 * 初始化
	 */
	public ${assign_ClassName}() {
		super();
		this.${assign_SqlName}= new ${content.items.sql.className}();
	}

	@Override
	public Future<JsonObject> find(MultiMap params) {
		return responseList("${content.content.pascalName}-find",${assign_SqlName}.findAll());
	}

	@Override
  public Future<JsonObject> save(MultiMap params) {
    ${content.items.model.className} model=new ${content.items.model.className}(params);
    if (model.checkIsInvalid()) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("${content.content.pascalName}-save->检查数据不通过:" + model.checkIsInvalidMessage());
      }
      return FutureUtil.resultMissParameterZero();
    }
    return responseZeroOrOne("${content.content.pascalName}-save", ${assign_SqlName}.save(model));
  }
  <#if content.content.primaryField??>
  <#assign assign_idFieldType = content.content.primaryField[0].fieldType>
  <#assign assign_idFieldName = content.content.primaryField[0].fieldName>
  <#assign assign_idName = content.content.primaryField[0].name>

	@Override
  public Future<JsonObject> get(MultiMap params) {
    ${assign_idFieldType} id = ParamsUtil.get${assign_idFieldType}(params, "id");
    ValidationResult check = invalidate("TestTable-get", <#if assign_idFieldType=="String">check(id)<#else>id == null</#if>, ResultDataType.JSON);
    if (check.invalid()){
      return check.result();
    }
    return responseObject("TestTable-get",${assign_SqlName}.getById(id));
  }

  @Override
	public Future<JsonObject> delete(MultiMap params) {
    ${assign_idFieldType} id = ParamsUtil.get${assign_idFieldType}(params, "id");
    ValidationResult check = invalidate("TestTable-delete", <#if assign_idFieldType=="String">check(id)<#else>id == null</#if>);
    if (check.invalid()){
      return check.result();
    }
    return responseZeroOrOne("TestTable-delete",${assign_SqlName}.deleteById(id));
	}
  </#if>
}
