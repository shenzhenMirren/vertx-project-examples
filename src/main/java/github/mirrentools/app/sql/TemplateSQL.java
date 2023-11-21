package github.mirrentools.app.sql;


import github.mirrentools.core.sql.SqlAndParams;

public class TemplateSQL {
  public SqlAndParams queryCount(){
    SqlAndParams query = new SqlAndParams();
    query.setSql("SELECT count(*) FROM student");
    return  query;
  }
  public SqlAndParams queryAll(){
    SqlAndParams query = new SqlAndParams();
    query.setSql("SELECT * FROM student");
    return  query;
  }
}
