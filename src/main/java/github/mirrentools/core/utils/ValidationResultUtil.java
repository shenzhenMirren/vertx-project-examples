package github.mirrentools.core.utils;

import io.vertx.core.Future;
import github.mirrentools.core.ValidationResult;
import github.mirrentools.core.enums.ResultDataType;
import github.mirrentools.core.enums.ResultState;

/**
 * 验证数据结果工具类
 */
public class ValidationResultUtil {

  /**
   * 返回结果指定结果的数据,
   *
   * @param state 响应状态码
   * @param type 响应类型,只识别:EMPTY="",ZERO=0,JSON={},ARRAY=[],其他的返回null
   */
  public static ValidationResult get(ResultState state, ResultDataType type) {
    if (type == ResultDataType.EMPTY) {
      return getEmpty(state);
    } else if (type == ResultDataType.ZERO) {
      return getZero(state);
    } else if (type == ResultDataType.JSON) {
      return getNewJson(state);
    } else if (type == ResultDataType.ARRAY) {
      return getNewArray(state);
    }
    return getNull(state);
  }

  /**
   * 返回结果为null的数据
   *
   * @param state 响应状态
   */
  public static ValidationResult getNull(ResultState state) {
    return new ValidationResult(true, Future.succeededFuture(ResultUtil.getNull(state)));
  }

  /**
   * 返回结果为空字符的数据
   *
   * @param state 响应状态
   */
  public static ValidationResult getEmpty(ResultState state) {
    return new ValidationResult(true, Future.succeededFuture(ResultUtil.getEmpty(state)));
  }

  /**
   * 返回结果为0的数据
   *
   * @param state 响应状态
   */
  public static ValidationResult getZero(ResultState state) {
    return new ValidationResult(true, Future.succeededFuture(ResultUtil.getZero(state)));
  }

  /**
   * 返回结果为{}的数据
   *
   * @param state 响应状态
   */
  public static ValidationResult getNewJson(ResultState state) {
    return new ValidationResult(true, Future.succeededFuture(ResultUtil.getNewJson(state)));
  }

  /**
   * 返回结果为[]的数据
   *
   * @param state 响应状态
   */
  public static ValidationResult getNewArray(ResultState state) {
    return new ValidationResult(true, Future.succeededFuture(ResultUtil.getNewArray(state)));
  }


}
