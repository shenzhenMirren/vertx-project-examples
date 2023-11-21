package github.mirrentools.app.service.impl;

import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import github.mirrentools.app.service.ClassesService;
import github.mirrentools.app.model.ClassesModel;
import github.mirrentools.app.sql.ClassesSQL;
import github.mirrentools.core.ValidationResult;
import github.mirrentools.core.base.ServiceBase;
import github.mirrentools.core.enums.ResultDataType;
import github.mirrentools.core.utils.FutureUtil;
import github.mirrentools.core.utils.ParamsUtil;

/**
 * Classes数据服务接口的默认实现
 */
public class ClassesServiceImpl extends ServiceBase implements ClassesService {
	/** SQL操作语句 */
	private final ClassesSQL classesSql;

	/**
	 * 初始化
	 */
	public ClassesServiceImpl() {
		super();
		this.classesSql= new ClassesSQL();
	}

	@Override
	public Future<JsonObject> find(MultiMap params) {
		return responseList("Classes-find",classesSql.findAll());
	}

	@Override
  public Future<JsonObject> save(MultiMap params) {
    ClassesModel model=new ClassesModel(params);
    if (model.checkIsInvalid()) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Classes-save->检查数据不通过:" + model.checkIsInvalidMessage());
      }
      return FutureUtil.resultMissParameterZero();
    }
    return responseZeroOrOne("Classes-save", classesSql.save(model));
  }

	@Override
  public Future<JsonObject> get(MultiMap params) {
    Integer id = ParamsUtil.getInteger(params, "id");
    ValidationResult check = invalidate("TestTable-get", id == null, ResultDataType.JSON);
    if (check.invalid()){
      return check.result();
    }
    return responseObject("TestTable-get",classesSql.getById(id));
  }

  @Override
	public Future<JsonObject> delete(MultiMap params) {
    Integer id = ParamsUtil.getInteger(params, "id");
    ValidationResult check = invalidate("TestTable-delete", id == null);
    if (check.invalid()){
      return check.result();
    }
    return responseZeroOrOne("TestTable-delete",classesSql.deleteById(id));
	}
}
