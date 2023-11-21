package github.mirrentools;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

/**
 * 定时任务,这个Verticle只会被单实例部署
 *
 * @author <a href="https://github.com/shenzhenMirren">Mirren</a>
 */
public class ScheduleVerticle extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) {
    startPromise.complete();
  }
}
