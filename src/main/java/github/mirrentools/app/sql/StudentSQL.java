package github.mirrentools.app.sql;

import io.vertx.sqlclient.Tuple;
import github.mirrentools.app.model.StudentModel;
import github.mirrentools.core.sql.SqlAndParams;


/**
 * Student数据库相关操作语句
 */
public class StudentSQL {
  /**默认返回的列*/
  private final String resultColumn=" id,cid,nickname,age ";
  /**
   * 获取数量
   */
  public SqlAndParams count(){
    String sql = "SELECT COUNT(*) FROM student";
    return new SqlAndParams(sql);
  }

  /**
   * 获取所有数据
   */
  public SqlAndParams findAll(){
    String sql = "SELECT "+resultColumn+" FROM student";
    return new SqlAndParams(sql);
  }

  /**
   * 获取所有数据
   * @param offset 从第几行开始获取数据
   * @param limit 每次获取多少行数据
   */
  public SqlAndParams findLimit(int offset,int limit){
    if(offset<0){
      offset=0;
    }
    if(limit<1){
      limit=1;
    }
    String sql = "SELECT "+resultColumn+" FROM student LIMIT ? OFFSET ? ";
    Tuple tuple = Tuple.tuple()
      .addInteger(limit)
      .addInteger(offset);
    return new SqlAndParams(sql,tuple);
  }

  /**
   * 新增数据
   */
  public SqlAndParams save(StudentModel model){
    String sql = "INSERT INTO student (id,cid,nickname,age) VALUES (?,?,?,?)";
    Tuple tuple = Tuple.tuple();
    tuple.addInteger(model.getId());
    tuple.addInteger(model.getCid());
    tuple.addString(model.getNickname());
    tuple.addInteger(model.getAge());
    return new SqlAndParams(sql,tuple);
  }

  /**
   * 通过id获取数据
   */
  public SqlAndParams getById(Integer id){
    String sql = "SELECT "+resultColumn+" FROM student WHERE id = ?";
    Tuple tuple = Tuple.tuple().addInteger(id);
    return new SqlAndParams(sql,tuple).setSucceeded(id!=null);
  }

  /**
   * 通过id删除数据
   */
  public SqlAndParams deleteById(Integer id){
    String sql = "DELETE FROM student WHERE id = ?";
    Tuple tuple = Tuple.tuple().addInteger(id);
    return new SqlAndParams(sql,tuple).setSucceeded(id!=null);
  }
}
