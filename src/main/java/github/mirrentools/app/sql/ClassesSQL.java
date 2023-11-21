package github.mirrentools.app.sql;

import io.vertx.sqlclient.Tuple;
import github.mirrentools.app.model.ClassesModel;
import github.mirrentools.core.sql.SqlAndParams;


/**
 * Classes数据库相关操作语句
 */
public class ClassesSQL {
  /**默认返回的列*/
  private final String resultColumn=" id,class_rooms AS classRooms ";
  /**
   * 获取数量
   */
  public SqlAndParams count(){
    String sql = "SELECT COUNT(*) FROM classes";
    return new SqlAndParams(sql);
  }

  /**
   * 获取所有数据
   */
  public SqlAndParams findAll(){
    String sql = "SELECT "+resultColumn+" FROM classes";
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
    String sql = "SELECT "+resultColumn+" FROM classes LIMIT ? OFFSET ? ";
    Tuple tuple = Tuple.tuple()
      .addInteger(limit)
      .addInteger(offset);
    return new SqlAndParams(sql,tuple);
  }

  /**
   * 新增数据
   */
  public SqlAndParams save(ClassesModel model){
    String sql = "INSERT INTO classes (id,class_rooms) VALUES (?,?)";
    Tuple tuple = Tuple.tuple();
    tuple.addInteger(model.getId());
    tuple.addString(model.getClassRooms());
    return new SqlAndParams(sql,tuple);
  }

  /**
   * 通过id获取数据
   */
  public SqlAndParams getById(Integer id){
    String sql = "SELECT "+resultColumn+" FROM classes WHERE id = ?";
    Tuple tuple = Tuple.tuple().addInteger(id);
    return new SqlAndParams(sql,tuple).setSucceeded(id!=null);
  }

  /**
   * 通过id删除数据
   */
  public SqlAndParams deleteById(Integer id){
    String sql = "DELETE FROM classes WHERE id = ?";
    Tuple tuple = Tuple.tuple().addInteger(id);
    return new SqlAndParams(sql,tuple).setSucceeded(id!=null);
  }
}
