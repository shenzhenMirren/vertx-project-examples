package github.mirrentools.app.service.impl;

import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.mysqlclient.MySQLClient;
import github.mirrentools.app.model.StudentBatchModel;
import github.mirrentools.app.service.StudentBatchService;
import github.mirrentools.app.sql.StudentBatchSQL;
import github.mirrentools.core.base.ServiceBase;
import github.mirrentools.core.sql.SqlAndParams;
import github.mirrentools.core.utils.FutureUtil;
import github.mirrentools.core.utils.ParamsUtil;
import github.mirrentools.core.utils.ResultUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * StudentAuto数据服务接口的默认实现
 */
public class StudentBatchServiceImpl extends ServiceBase implements StudentBatchService {
  /**
   * SQL操作语句
   */
  private final StudentBatchSQL studentBatchSql;

  /**
   * 初始化
   */
  public StudentBatchServiceImpl() {
    super();
    this.studentBatchSql = new StudentBatchSQL();
  }

  @Override
  public Future<JsonObject> batch(MultiMap params) {
    JsonArray items = ParamsUtil.getJsonArray(params, "items");
    if (items == null || items.isEmpty()) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("StudentAuto-batch->检查数据不通过:合集为空");
      }
      return FutureUtil.resultMissParameterNull();
    }

    try {
      List<StudentBatchModel> models = new ArrayList<>();
      for (int i = 0; i < items.size(); i++) {
        StudentBatchModel model = new StudentBatchModel(items.getJsonObject(i));
        if (model.checkIsInvalid()) {
          if (LOG.isDebugEnabled()) {
            LOG.debug("StudentAuto-batch->检查用户请求参数-->不通过:" + model.checkIsInvalidMessage());
          }
          return FutureUtil.resultInvalidParameterNull();
        }
        models.add(model);
      }


      SqlAndParams query = studentBatchSql.save(models);

      //如果你们实际情况常常使用批量自增可以考虑将添加到Service
      Promise<JsonObject> promise = Promise.promise();
      super.jdbcExecute().batch(query, MySQLClient.LAST_INSERTED_ID)
        .onSuccess(data -> {
          if (LOG.isDebugEnabled()) {
            LOG.error("StudentAuto-batch执行批量保存数据-->结果:" + data);
          }
          promise.complete(ResultUtil.getSuccess(data));
        })
        .onFailure(err -> {
          LOG.error("[StudentAuto-batch]执行批量保存数据-->失败:", err);
          promise.complete(ResultUtil.getErrorNull());
        });
      return promise.future();

    } catch (Exception e) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("StudentAuto-batch->加载用户请求参数-->失败:", e);
      }
      return FutureUtil.resultInvalidParameterNull();
    }
  }

}
