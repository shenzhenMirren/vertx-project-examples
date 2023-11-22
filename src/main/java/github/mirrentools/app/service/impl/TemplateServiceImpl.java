package github.mirrentools.app.service.impl;

import github.mirrentools.core.base.ServiceBase;
import github.mirrentools.core.utils.ResultUtil;
import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import github.mirrentools.app.service.TemplateService;
import github.mirrentools.app.sql.TemplateSQL;

import java.util.List;

public class TemplateServiceImpl extends ServiceBase implements TemplateService {
  public final TemplateSQL templateSQL=new TemplateSQL();
  @Override
  public Future<JsonObject> find(MultiMap params) {
    //如果是多数据源,可以在测试后面加上数据源的id
    return responseList("测试", templateSQL.queryAll());
  }

  @Override
  public Future<JsonObject> merge(MultiMap params) {

    Promise<Long> promiseCount = Promise.promise();
    getLong(templateSQL.queryCount(), promiseCount);

    Promise<List<JsonObject>> promiseList = Promise.promise();
    getList(templateSQL.queryAll(), promiseList);

    Promise<JsonObject> result = Promise.promise();
    Future.all(promiseCount.future(), promiseList.future())
      .onSuccess(res -> {
        JsonObject data = new JsonObject();
        data.put("count", promiseCount.future().result());
        data.put("items", promiseList.future().result());
        result.complete(ResultUtil.getSuccess(data));
      })
      .onFailure(
        err -> {
          LOG.error("执行merge合并返回失败-->", err);
          result.complete(ResultUtil.getErrorNewJson());
        }
      );
    return result.future();
  }
}
