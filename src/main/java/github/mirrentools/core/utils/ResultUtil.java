package github.mirrentools.core.utils;

import io.vertx.core.json.JsonObject;
import github.mirrentools.core.enums.ResultState;

import java.util.List;

/**
 * 返回响应数据结果的工具
 *
 * @author <a href="https://github.com/shenzhenMirren">Mirren</a>
 */
public class ResultUtil {
  /**
   * 返回结果为null的JsonObject
   */
  public static JsonObject getNull(ResultState state) {
    return ResultFormat.formatAsNull(state);
  }

  /**
   * 返回结果为空字符串的JsonObject
   */
  public static JsonObject getEmpty(ResultState state) {
    return ResultFormat.formatAsEmpty(state);
  }

  /**
   * 返回结果为0的JsonObject
   */
  public static JsonObject getZero(ResultState state) {
    return ResultFormat.formatAsZero(state);
  }


  /**
   * 返回结果为1的JsonObject
   */
  public static JsonObject getOne(ResultState state) {
    return ResultFormat.formatAsOne(state);
  }


  /**
   * 返回结果为{}的JsonObject
   */
  public static JsonObject getNewJson(ResultState state) {
    return ResultFormat.formatAsNewJson(state);
  }

  /**
   * 返回结果为[]的JsonObject
   */
  public static JsonObject getNewArray(ResultState state) {
    return ResultFormat.formatAsNewArray(state);
  }


  /**
   * 返回自定义状态码的操作结果
   */
  public static JsonObject get(ResultState state, Integer data) {
    return ResultFormat.format(state, data);
  }

  /**
   * 返回自定义状态码的操作结果
   */
  public static JsonObject get(ResultState state, Long data) {
    return ResultFormat.format(state, data);
  }


  /**
   * 返回自定义状态码的操作结果
   */
  public static JsonObject get(ResultState state, String data) {
    return ResultFormat.format(state, data);
  }


  /**
   * 返回自定义状态码的操作结果
   */
  public static JsonObject get(ResultState state, JsonObject data) {
    return ResultFormat.format(state, data);
  }


  /**
   * 返回自定义状态码的操作结果
   */
  public static JsonObject get(ResultState state, List<JsonObject> data) {
    return ResultFormat.format(state, data);
  }


  /**
   * 返回自定义状态码的操作结果
   */
  public static JsonObject get(ResultState state, Object data) {
    return ResultFormat.format(state, data);
  }


  /**
   * 返回一个状态码为SUCCESS的操作结果
   */
  public static JsonObject getSuccess(Integer data) {
    return get(ResultState.SUCCESS, data);
  }


  /**
   * 返回一个状态码为SUCCESS的操作结果
   */
  public static JsonObject getSuccess(Long data) {
    return get(ResultState.SUCCESS, data);
  }


  /**
   * 返回一个状态码为SUCCESS的操作结果
   */
  public static JsonObject getSuccess(String data) {
    return get(ResultState.SUCCESS, data);
  }


  /**
   * 返回一个状态码为SUCCESS的操作结果
   */
  public static JsonObject getSuccess(JsonObject data) {
    return get(ResultState.SUCCESS, data);
  }


  /**
   * 返回一个状态码为SUCCESS的操作结果
   */
  public static JsonObject getSuccess(List<JsonObject> data) {
    return get(ResultState.SUCCESS, data);
  }


  /**
   * 返回一个状态码为SUCCESS的操作结果
   */
  public static JsonObject getSuccess(Object data) {
    return get(ResultState.SUCCESS, data);
  }


  /**
   * 返回一个状态码为SUCCESS,结果为null的JsonObject
   */
  public static JsonObject getSuccessNull() {
    return getNull(ResultState.SUCCESS);
  }

  /**
   * 返回一个状态码为SUCCESS,结果为空字符串的JsonObject
   */
  public static JsonObject getSuccessEmpty() {
    return getEmpty(ResultState.SUCCESS);
  }


  /**
   * 返回一个状态码为SUCCESS,结果为0的JsonObject
   */
  public static JsonObject getSuccessZero() {
    return getZero(ResultState.SUCCESS);
  }


  /**
   * 返回一个状态码为SUCCESS,结果为1的JsonObject
   */
  public static JsonObject getSuccessOne() {
    return getOne(ResultState.SUCCESS);
  }


  /**
   * 返回一个状态码为SUCCESS,结果为{}的JsonObject
   */
  public static JsonObject getSuccessNewJson() {
    return getNewJson(ResultState.SUCCESS);
  }


  /**
   * 返回一个状态码为SUCCESS,结果为[]JsonObject
   */
  public static JsonObject getSuccessNewArray() {
    return getNewArray(ResultState.SUCCESS);
  }


  /**
   * 返回一个状态码为MissParameter,结果为null的JsonObject
   */
  public static JsonObject getMissParameterNull() {
    return getNull(ResultState.MISS_PARAMETER);
  }


  /**
   * 返回一个状态码为MissParameter,结果为空字符串的JsonObject
   */
  public static JsonObject getMissParameterEmpty() {
    return getEmpty(ResultState.MISS_PARAMETER);
  }


  /**
   * 返回一个状态码为MissParameter,结果为0的JsonObject
   */
  public static JsonObject getMissParameterZero() {
    return getZero(ResultState.MISS_PARAMETER);
  }


  /**
   * 返回一个状态码为MissParameter,结果为1的JsonObject
   */
  public static JsonObject getMissParameterOne() {
    return getOne(ResultState.MISS_PARAMETER);
  }


  /**
   * 返回一个状态码为MissParameter,结果为{}的JsonObject
   */
  public static JsonObject getMissParameterNewJson() {
    return getNewJson(ResultState.MISS_PARAMETER);
  }


  /**
   * 返回一个状态码为MissParameter,结果为[]JsonObject
   */
  public static JsonObject getMissParameterNewArray() {
    return getNewArray(ResultState.MISS_PARAMETER);
  }


  /**
   * 返回一个状态码为INVALID_PARAMETER,结果为null的JsonObject
   */
  public static JsonObject getInvalidParameterNull() {
    return getNull(ResultState.INVALID_PARAMETER);
  }

  /**
   * 返回一个状态码为INVALID_PARAMETER,,结果为空字符串的JsonObject
   */
  public static JsonObject getInvalidParameterEmpty() {
    return getEmpty(ResultState.INVALID_PARAMETER);
  }


  /**
   * 返回一个状态码为INVALID_PARAMETER,结果为0的JsonObject
   */
  public static JsonObject getInvalidParameterZero() {
    return getZero(ResultState.INVALID_PARAMETER);
  }


  /**
   * 返回一个状态码为INVALID_PARAMETER,结果为1的JsonObject
   */
  public static JsonObject getInvalidParameterOne() {
    return getOne(ResultState.INVALID_PARAMETER);
  }


  /**
   * 返回一个状态码为INVALID_PARAMETER,结果为{}的JsonObject
   */
  public static JsonObject getInvalidParameterNewJson() {
    return getNewJson(ResultState.INVALID_PARAMETER);
  }


  /**
   * 返回一个状态码为INVALID_PARAMETER,结果为[]JsonObject
   */
  public static JsonObject getInvalidParameterNewArray() {
    return getNewArray(ResultState.INVALID_PARAMETER);
  }


  /**
   * 返回一个状态码为ERROR,,结果为null的JsonObject
   */
  public static JsonObject getErrorNull() {
    return getNull(ResultState.ERROR);
  }


  /**
   * 返回一个状态码为ERROR,,结果为空字符串的JsonObject
   */
  public static JsonObject getErrorEmpty() {
    return getEmpty(ResultState.ERROR);
  }


  /**
   * 返回一个状态码为ERROR,结果为0的JsonObject
   */
  public static JsonObject getErrorZero() {
    return getZero(ResultState.ERROR);
  }


  /**
   * 返回一个状态码为ERROR,结果为1的JsonObject
   */
  public static JsonObject getErrorOne() {
    return getOne(ResultState.ERROR);
  }


  /**
   * 返回一个状态码为ERROR,结果为{}的Future
   */
  public static JsonObject getErrorNewJson() {
    return getNewJson(ResultState.ERROR);
  }


  /**
   * 返回一个状态码为ERROR,结果为[]Array
   */
  public static JsonObject getErrorNewArray() {
    return getNewArray(ResultState.ERROR);
  }


}
