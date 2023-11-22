# vertx-project-examples
作者自Vert.x 3.2开始使用Vert.x到今天踩过很多坑,编写过很多垃圾代码后总结下来一些经验,这个示例主要面向非后台管理的客户端，旨在提供一个结构清晰、易于维护的项目结构!希望透过这个示例能帮助你从其他编程框架或入门Vert.x提供参考

示例地址: [vertx-project-examples](https://github.com/shenzhenMirren/vertx-project-examples)

## 项目结构
```
📄 MainLauncher.java
📄 MainVerticle.java
📄 ServerVerticle.java
📄 ScheduleVerticle.java
＞ 📁 app
　　 📁 router
　　 📁 service
　　 📁 model
　　 📁 sql
＞ 📁 core
　　 📁 base
　　 📁 enums
　　 📁 sql
　　 📁 utils
＞ 📁 test
　　 📁 generate
　　 📁 package.app.test
```

## 结构说明
在这个结构中作者已经封装好很多功能到core中,使用者可以clone这个项目并修改为自己的包名,实际使用中只需要在app包中编写自己的逻辑,并在ServerVerticle中启用路由

提示: 测试文件夹的generate包中create可以生成基础代码,生成代码使用的工具是: [screw-driver](https://github.com/MirrenTools/screw-driver)

### MainLauncher.java 启动器
这个类继承了Vert.x的Launcher,使用者通常无需关心,因为这个类只是定义了配置文件的加载以及vert.x等基础的配置

### MainVerticle.java 启动程序
这个类是启动类,它有2个作用

1. 当在IDE编辑代码时要运行程序就直接调用main方法启动
2. 当项目需要使用到自定义配置文件时,使用者需要在getAppConfig()中添加app中需要使用到的配置信息

### ServerVerticle.java 路由服务
这个类是Server服务,对外提供路由,这个类使用者需要关注的只有3个方法:

1. initPublicRouter 用于发布不需要鉴权的API
2. initAuthRouter 用于添加鉴权处理器
3. initPrivateRouter 用于发布需要鉴权的API

提示: 因为多实例的原因控制台会打印实例数量的Server running port: ****,这属于正常情况,也属于vert.x的魔法

### ScheduleVerticle.java 调度服务
由于ServerVerticle会被多实例部署,如果在里面添加调度器会存在被多次调用的情况,所以当你有调度任务的时候,你可以在ScheduleVerticle中添加,
典型的做法是在这个ScheduleVerticle里面添加方法,并在方法中调用Vert.x的setPeriodic进行时间判断,
可参考: [Vert.x 周期性计时器](https://vertx-china.github.io/docs/vertx-core/java/#_periodic_timers),最后在start中调用这个方法

### app.router 定义API路由地址
router包下的类引用Service并在静态方法startRouter中定义了路由地址,并调用对应的服务
``` java
// 代码示例
public class TemplateRouter extends RouterBase {
  private final TemplateService service;
  private TemplateRouter() {
    service = TemplateService.create();
  }
  public static void startRouter(Router router) {
    TemplateRouter instance = new TemplateRouter();
    router.post("/test").handler(instance::get);
  }
  private void get(RoutingContext rct) {
    process(rct, service::find);
  }
}
```
API服务绑定方法里面的process继承自core.base.RouterBase,主要是记录日志并响应service返回的结果;

当需要启动这个路由器时就在ServerVerticle的initPrivateRouter启动服务TemplateRouter.startRouter(router);

### app.service 提供业务处理
service实现类引用sql类,并在方法中调用core.base.ServiceBase类提供的response方法
``` java
public class StudentServiceImpl extends ServiceBase implements StudentService {
  private final StudentSQL studentSql;
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
}

```
### app.model 数据模型
数据模型类用于在service调用sql生成SQL时作为参数传输,同时这个model也做数据检验,可以get数据是否检查不通过,检查不通过的原因
``` java
  public boolean checkIsInvalid(){
    return modelCheckIsInvalid;
  }
  public String checkIsInvalidMessage(){
    return modelCheckIsInvalidMessage;
  }
```

### app.sql SQL语句与参数
sql类主要用于生成SQL语句与执行参数,生成后由Service调用执行得到结果
``` java
public class StudentSQL {
  public SqlAndParams save(StudentModel model){
    String sql = "INSERT INTO student (id,cid,nickname,age) VALUES (?,?,?,?)";
    Tuple tuple = Tuple.tuple();
    tuple.addInteger(model.getId());
    tuple.addInteger(model.getCid());
    tuple.addString(model.getNickname());
    tuple.addInteger(model.getAge());
    return new SqlAndParams(sql,tuple);
  }
}
```

## 如何部署启动
1. 执行maven的package 比如: mvn clean package,你会在target里面得到一个XXX-fat.jar
2. 复制data文件夹与XXX-fat.jar到你想要的路径
3. 执行 java -jar XXX-fat.jar 启动项目

## 一些解读
1. 鉴于大多数人都习惯了Controller、Service、Dao、Entity的设计架构,这样的设计是否不妥? 其实我一开始也是经典的3层结构,但是vert.x的执行单位是Verticle,如果我每个API业务用一个Verticle,我需要多个Verticle,并且很多功能需要使用Vert.x的EventBus来调用!
2. 为什么不用Entity? 因为Vert.x自带的Json非常舒服,并且我们最终响应的基本是Json,所以没有必要再做一次转换!
3. 本示例为作者主观设计,有不完善的地方欢迎指正

## 使用说明
可以通过查看源码的方式就能获取完整的项目逻辑

查看的逻辑流程可以是:
1. MainLauncher(启动器)
2. MainVerticle(启动服务)
3. ServerVerticle(发布API)
4. app.router(定义API)
5. app.service(业务处理)

### 统一响应结果格式
core.enums.ResultState定义了数据结果, 返回的结果统一为:{"code":状态码, "msg":状态信息, "data":数据信息}

### RouterBase 路由基础类
* process(...) 适用于Service参数为MultiMap的方法引用
* processWithRC(...) 适用于Service参数为RoutingContext的方法引用

### ServiceBase
* invalidate(...) 验证数据是否不符合要求
* responseLong(...) 查询数据库的数据后将结果格式化为响应数据: Long 类型
* responseObject(...) 查询数据库的数据后将结果格式化为响应数据: JsonObject 类型
* responseList(...) 查询数据库的数据后将结果格式化为响应数据: List<JsonObject> 类型
* responseInt(...) 执行数据库操作后将结果格式化为响应数据: int 受影响的数量
* responseZeroOrOne(...) 执行数据库操作后将结果格式化为响应数据: int 受影响数量,如果大于1就改为1
* resultInt(...) 将调用数据库的结果,封装为int
* resultZeroOrOne(...) 将调用数据库的结果,封装为0或者1
* resultLong(...) 将调用数据库的结果,封装为 Long
* resultJsonOrNew(...) 将调用数据库的结果,封装为 JsonObject
* resultArrayOrNew(...) 将调用数据库的结果,封装为 List<JsonObject>

### JdbcBase 数据库基础类
* jdbcExecute() 获取默认数据源执行器
* jdbcExecute(String dbId) 获取指定数据源执行器
* getLong(...) 执行获取Long结果(通常是只有一个结果并且是数值)
* getList(...) 执行获取List<JsonObject>结果
* getObject(...) 执行获取JsonObject结果
* updateInt(...) 执行更新并得到int结果
* batchInt(...) 批量执行更新并得到总操作int结果

### 其他说明
1. Service提供了调用不同的数据源的方法,一般只需要在调用response或get方法时传入dbId就可以了,
2. 需要使用WebClient或Vert.x等实例可以使用AppContext.context就能得到对应的服务
3. 服务架构个人推荐: 负载均衡-->API Gateway(具备负载均衡及注册服务)-->Services
