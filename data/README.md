# config.json配置文件说明

## instance

ServerVerticle的实例数量,通常要参考CPU是多少核心,比如机器虚拟8核就设置实例为7

## httpPort

服务器的端口号

## monitoringEnabled

是否开启指标监控,如果true则开启并且可以访问 host:port/CoreConstants.MONITORING_ROUTE_PATH得到结果

## log4j2Config

Log4j2日志打印格式配置信息的所在path,启动器会在这个paths上添加System.getProperty("user.dir")

## webClient

http客户端的配置信息,具体参考 WebClientOptions类

## dataSource

数据库链接配置,其中id是指哪一个实例,如果作为默认实例就将id设置为def

## app

全局会使用到的配置信息,这个配置信息要在全局可以使用需要做下面的步骤:

1. 在 AppConfig Record类的最后添加这个参数,
2. 在 MainVerticle getConfig读取解析并添加这个参数
3. 使用时直接调用AppConfig.xxx
