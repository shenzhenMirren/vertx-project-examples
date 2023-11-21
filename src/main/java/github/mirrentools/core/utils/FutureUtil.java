package github.mirrentools.core.utils;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import github.mirrentools.core.enums.ResultState;

/**
 * 返回响应结果结果为Future的工具
 *
 * @author <a href="https://github.com/shenzhenMirren">Mirren</a>
 */
public class FutureUtil {
  /**
   * 返回结果为null,状态码自定义
   *
   * @param state 状态码
   */
  public static Future<JsonObject> resultNull(ResultState state) {
    return Future.succeededFuture(ResultUtil.getNull(state));
  }

  /**
   * 返回结果为空字符串,状态码自定义
   *
   * @param state 状态码
   */
  public static Future<JsonObject> resultEmpty(ResultState state) {
    return Future.succeededFuture(ResultUtil.getEmpty(state));
  }

  /**
   * 返回结果为0,状态码自定义
   *
   * @param state 状态码
   */
  public static Future<JsonObject> resultZero(ResultState state) {
    return Future.succeededFuture(ResultUtil.getZero(state));
  }


  /**
   * 返回结果为1,状态码自定义
   *
   * @param state 状态码
   */
  public static Future<JsonObject> resultOne(ResultState state) {
    return Future.succeededFuture(ResultUtil.getOne(state));
  }

  /**
   * 返回结果为{},状态码自定义
   *
   * @param state 状态码
   */
  public static Future<JsonObject> resultNewJson(ResultState state) {
    return Future.succeededFuture(ResultUtil.getNewJson(state));
  }


  /**
   * 返回结果为[],状态码自定义
   *
   * @param state 状态码
   */
  public static Future<JsonObject> resultNewArray(ResultState state) {
    return Future.succeededFuture(ResultUtil.getNewArray(state));
  }

  /**
   * 返回结果为null,状态码SUCCESS
   */
  public static Future<JsonObject> resultSuccessNull() {
    return resultNull(ResultState.SUCCESS);
  }

  /**
   * 返回结果为空字符串,状态码SUCCESS
   */
  public static Future<JsonObject> resultSuccessEmpty() {
    return resultEmpty(ResultState.SUCCESS);
  }

  /**
   * 返回结果为0,状态码SUCCESS
   */
  public static Future<JsonObject> resultSuccessZero() {
    return resultZero(ResultState.SUCCESS);
  }


  /**
   * 返回结果为1,状态码SUCCESS
   */
  public static Future<JsonObject> resultSuccessOne() {
    return resultOne(ResultState.SUCCESS);
  }

  /**
   * 返回结果为{},状态码SUCCESS
   */
  public static Future<JsonObject> resultSuccessNewJson() {
    return resultNewJson(ResultState.SUCCESS);
  }


  /**
   * 返回结果为[],状态码SUCCESS
   */
  public static Future<JsonObject> resultSuccessNewArray() {
    return resultNewArray(ResultState.SUCCESS);
  }
  /**
   * 返回结果为null,状态码MISS_PARAMETER
   */
  public static Future<JsonObject> resultMissParameterNull() {
    return resultNull(ResultState.MISS_PARAMETER);
  }

  /**
   * 返回结果为空字符串,状态码MISS_PARAMETER
   */
  public static Future<JsonObject> resultMissParameterEmpty() {
    return resultEmpty(ResultState.MISS_PARAMETER);
  }

  /**
   * 返回结果为0,状态码MISS_PARAMETER
   */
  public static Future<JsonObject> resultMissParameterZero() {
    return resultZero(ResultState.MISS_PARAMETER);
  }


  /**
   * 返回结果为1,状态码MISS_PARAMETER
   */
  public static Future<JsonObject> resultMissParameterOne() {
    return resultOne(ResultState.MISS_PARAMETER);
  }

  /**
   * 返回结果为{},状态码MISS_PARAMETER
   */
  public static Future<JsonObject> resultMissParameterNewJson() {
    return resultNewJson(ResultState.MISS_PARAMETER);
  }


  /**
   * 返回结果为[],状态码MISS_PARAMETER
   */
  public static Future<JsonObject> resultMissParameterNewArray() {
    return resultNewArray(ResultState.MISS_PARAMETER);
  }
  /**
   * 返回结果为null,状态码INVALID_PARAMETER
   */
  public static Future<JsonObject> resultInvalidParameterNull() {
    return resultNull(ResultState.INVALID_PARAMETER);
  }

  /**
   * 返回结果为空字符串,状态码INVALID_PARAMETER
   */
  public static Future<JsonObject> resultInvalidParameterEmpty() {
    return resultEmpty(ResultState.INVALID_PARAMETER);
  }

  /**
   * 返回结果为0,状态码INVALID_PARAMETER
   */
  public static Future<JsonObject> resultInvalidParameterZero() {
    return resultZero(ResultState.INVALID_PARAMETER);
  }


  /**
   * 返回结果为1,状态码INVALID_PARAMETER
   */
  public static Future<JsonObject> resultInvalidParameterOne() {
    return resultOne(ResultState.INVALID_PARAMETER);
  }

  /**
   * 返回结果为{},状态码INVALID_PARAMETER
   */
  public static Future<JsonObject> resultInvalidParameterNewJson() {
    return resultNewJson(ResultState.INVALID_PARAMETER);
  }


  /**
   * 返回结果为[],状态码INVALID_PARAMETER
   */
  public static Future<JsonObject> resultInvalidParameterNewArray() {
    return resultNewArray(ResultState.INVALID_PARAMETER);
  }

  /**
   * 返回结果为null,状态码ERROR
   */
  public static Future<JsonObject> resultErrorNull() {
    return resultNull(ResultState.ERROR);
  }

  /**
   * 返回结果为空字符串,状态码ERROR
   */
  public static Future<JsonObject> resultErrorEmpty() {
    return resultEmpty(ResultState.ERROR);
  }

  /**
   * 返回结果为0,状态码ERROR
   */
  public static Future<JsonObject> resultErrorZero() {
    return resultZero(ResultState.ERROR);
  }


  /**
   * 返回结果为1,状态码ERROR
   */
  public static Future<JsonObject> resultErrorOne() {
    return resultOne(ResultState.ERROR);
  }

  /**
   * 返回结果为{},状态码ERROR
   */
  public static Future<JsonObject> resultErrorNewJson() {
    return resultNewJson(ResultState.ERROR);
  }


  /**
   * 返回结果为[],状态码ERROR
   */
  public static Future<JsonObject> resultErrorNewArray() {
    return resultNewArray(ResultState.ERROR);
  }
}
