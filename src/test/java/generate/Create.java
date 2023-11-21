package generate;

import org.mirrentools.sd.ScrewDriver;
import org.mirrentools.sd.constant.MySQL;
import org.mirrentools.sd.constant.SdConstant;
import org.mirrentools.sd.models.SdClassContent;
import org.mirrentools.sd.models.SdTemplate;
import org.mirrentools.sd.options.ScrewDriverOptions;
import org.mirrentools.sd.options.SdDatabaseOptions;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 创建代码生成器,一些变量确定后通常只需要修改tableName到对应的表
 */
public class Create {
  public static void main(String[] args) {
    String tableName="classes";
    SdDatabaseOptions databaseOptions = new SdDatabaseOptions(MySQL.MYSQL_8_DERVER, "jdbc:mysql://localhost:3306/local?useUnicode=true&useSSL=false&serverTimezone=UTC");
    databaseOptions.setUser("root");
    databaseOptions.setPassword("miniduhua");
    String packageName = "github.mirrentools.app.";
    String templatePath = "vertx";
    // 初始化执行工具
    ScrewDriver screwDriver = ScrewDriver.instance(new ScrewDriverOptions(databaseOptions));
    // 读取表信息
    SdClassContent content = screwDriver.readTable(tableName);
    String entityName = content.getPascalName();
    Map<String, SdTemplate> templates = new LinkedHashMap<>();
    templates.put("sql", new SdTemplate()
      .setPath(templatePath)
      .setFile("SQL.ftl")
      .setPackageName(packageName + "sql")
      .setClassName(entityName + "SQL"));
    templates.put("model", new SdTemplate()
      .setPath(templatePath)
      .setFile("Model.ftl")
      .setPackageName(packageName + "model")
      .setClassName(entityName + "Model"));
    templates.put("service", new SdTemplate()
      .setPath(templatePath)
      .setFile("Service.ftl")
      .setPackageName(packageName + "service")
      .setClassName(entityName + "Service"));
    templates.put("serviceImpl", new SdTemplate()
      .setPath(templatePath)
      .setFile("ServiceImpl.ftl")
      .setPackageName(packageName + "service.impl")
      .setClassName(entityName + "ServiceImpl"));
    templates.put("router", new SdTemplate()
      .setPath(templatePath)
      .setFile("Router.ftl")
      .setPackageName(packageName + "router")
      .setClassName(entityName + "Router"));
    templates.put("test", new SdTemplate()
      .setPath(templatePath)
      .setFile("Test.ftl")
      .setSourceFolder(SdConstant.MAVEN_TEST)
      .setPackageName(packageName + "test")
      .setClassName(entityName + "Test"));
    // 创建代码
    screwDriver.createCode(content, templates);
  }
}
