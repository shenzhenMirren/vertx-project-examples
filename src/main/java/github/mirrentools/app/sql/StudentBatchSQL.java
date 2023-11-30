package github.mirrentools.app.sql;

import github.mirrentools.app.model.StudentBatchModel;
import github.mirrentools.core.sql.SqlAndParams;
import io.vertx.sqlclient.Tuple;

import java.util.ArrayList;
import java.util.List;


/**
 * StudentBatch数据库相关操作语句
 */
public class StudentBatchSQL {


  /**
   * 新增数据
   */
  public SqlAndParams save(List<StudentBatchModel> models) {
    String sql = "INSERT INTO student_batch (cid,nickname,age) VALUES (?,?,?)";
    List<Tuple> items = new ArrayList<>();
    for (StudentBatchModel model : models) {
      Tuple tuple = Tuple.tuple();
      tuple.addInteger(model.getCid());
      tuple.addString(model.getNickname());
      tuple.addInteger(model.getAge());
      items.add(tuple);
    }
    return new SqlAndParams(sql, items);
  }

}
