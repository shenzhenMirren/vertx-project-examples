package github.mirrentools.core;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

/**
 * 数据检查返回结果
 *
 * @param invalid 是否检查不通过,true代表不通过,result得到错误的状态信息,false则代表检查通过
 * @param result  错误响应信息,true才返回,false不需要返回
 * @author <a href="https://github.com/shenzhenMirren">Mirren</a>
 */
public record ValidationResult(boolean invalid, Future<JsonObject> result) {
}
