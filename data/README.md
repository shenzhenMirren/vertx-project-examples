# config.json配置文件说明

## instance

ServerVerticle的实例数量,通常要参考CPU是多少核心,建议是CPU的2倍减1,比如机器虚拟8核就设置实例为7

## httpPort

服务器的端口号

## monitoringEnabled

是否开启指标监控,如果true则开启并且可以访问 host:port/CoreConstants.MONITORING_ROUTE_PATH得到监控数据,返回为{http,sql,jvm}具体参考 [Vert.x 指标监控](https://vertx-china.github.io/docs/vertx-micrometer-metrics/java/#_vert_x_core_tools_metrics)

## log4j2Config

Log4j2日志打印格式配置信息的所在path,启动器会在这个paths上添加System.getProperty("user.dir")

## webClient

http客户端的配置信息,具体参考 WebClientOptions类

## dataSource

数据库链接配置,其中id是指哪一个实例,如果作为默认实例就将id设置为def

## app

全局会使用到的配置信息,这个配置信息要在全局可以使用需要做下面的步骤:

在 AppConfig 类里面定义属性并添加设置私有set方法跟公开获取方法,并在init里面获取配置信息进行初始化
* testMode: 当前是否为测试模式,true=测试模式
