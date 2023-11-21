package github.mirrentools.app.service.impl;

import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import github.mirrentools.app.service.StudentService;
import github.mirrentools.app.model.StudentModel;
import github.mirrentools.app.sql.StudentSQL;
import github.mirrentools.core.ValidationResult;
import github.mirrentools.core.base.ServiceBase;
import github.mirrentools.core.enums.ResultDataType;
import github.mirrentools.core.utils.FutureUtil;
import github.mirrentools.core.utils.ParamsUtil;

/**
 * Student数据服务接口的默认实现
 */
public class StudentServiceImpl extends ServiceBase implements StudentService {
	/** SQL操作语句 */
	private final StudentSQL studentSql;

	/**
	 * 初始化
	 */
	public StudentServiceImpl() {
		super();
		this.studentSql= new StudentSQL();
	}

	@Override
	public Future<JsonObject> find(MultiMap params) {
		return responseList("Student-find",studentSql.findAll());
	}

	@Override
  public Future<JsonObject> save(MultiMap params) {
    StudentModel model=new StudentModel(params);
    if (model.checkIsInvalid()) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Student-save->检查数据不通过:" + model.checkIsInvalidMessage());
      }
      return FutureUtil.resultMissParameterZero();
    }
    return responseZeroOrOne("Student-save", studentSql.save(model));
  }

	@Override
  public Future<JsonObject> get(MultiMap params) {
    Integer id = ParamsUtil.getInteger(params, "id");
    ValidationResult check = invalidate("TestTable-get", id == null, ResultDataType.JSON);
    if (check.invalid()){
      return check.result();
    }
    return responseObject("TestTable-get",studentSql.getById(id));
  }

  @Override
	public Future<JsonObject> delete(MultiMap params) {
    Integer id = ParamsUtil.getInteger(params, "id");
    ValidationResult check = invalidate("TestTable-delete", id == null);
    if (check.invalid()){
      return check.result();
    }
    return responseZeroOrOne("TestTable-delete",studentSql.deleteById(id));
	}
}
