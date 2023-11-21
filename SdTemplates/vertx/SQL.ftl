<#assign tableName = "${content.content.tableName}">
package ${content.items.sql.packageName};

import io.vertx.sqlclient.Tuple;
import ${content.items.model.packageName}.${content.items.model.className};
import github.mirrentools.core.sql.SqlAndParams;


/**
 * ${content.content.pascalName}数据库相关操作语句
 */
public class ${content.items.sql.className} {
  /**默认返回的列*/
  private final String resultColumn=" <#list content.content.fields as field>${field.name}<#if field.name != field.fieldName> AS ${field.fieldName}</#if><#if field_has_next>,</#if></#list> ";
  /**
   * 获取数量
   */
  public SqlAndParams count(){
    String sql = "SELECT COUNT(*) FROM ${tableName}";
    return new SqlAndParams(sql);
  }

  /**
   * 获取所有数据
   */
  public SqlAndParams findAll(){
    String sql = "SELECT "+resultColumn+" FROM ${tableName}";
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
    String sql = "SELECT "+resultColumn+" FROM ${tableName} LIMIT ? OFFSET ? ";
    Tuple tuple = Tuple.tuple()
      .addInteger(limit)
      .addInteger(offset);
    return new SqlAndParams(sql,tuple);
  }

  /**
   * 新增数据
   */
  public SqlAndParams save(${content.items.model.className} model){
    String sql = "INSERT INTO ${tableName} (<#list content.content.fields as item>${item.name}<#if item_has_next>,</#if></#list>) VALUES (<#list content.content.fields as item>?<#if item_has_next>,</#if></#list>)";
    Tuple tuple = Tuple.tuple();
    <#list content.content.fields as item>
    tuple.add${item.fieldType}(model.<#if item.fieldType == "boolean">is<#else>get</#if>${item.fieldNamePascal}());
    </#list>
    return new SqlAndParams(sql,tuple);
  }

  <#if content.content.primaryField??>
  <#assign assign_idFieldType = content.content.primaryField[0].fieldType>
  <#assign assign_idFieldName = content.content.primaryField[0].fieldName>
  <#assign assign_idName = content.content.primaryField[0].name>
  /**
   * 通过id获取数据
   */
  public SqlAndParams getById(${assign_idFieldType} id){
    String sql = "SELECT "+resultColumn+" FROM ${tableName} WHERE ${assign_idName} = ?";
    Tuple tuple = Tuple.tuple().add${assign_idFieldType}(id);
    return new SqlAndParams(sql,tuple).setSucceeded(id!=null);
  }

  /**
   * 通过id删除数据
   */
  public SqlAndParams deleteById(${assign_idFieldType} id){
    String sql = "DELETE FROM ${tableName} WHERE ${assign_idName} = ?";
    Tuple tuple = Tuple.tuple().add${assign_idFieldType}(id);
    return new SqlAndParams(sql,tuple).setSucceeded(id!=null);
  }
  </#if>
}
