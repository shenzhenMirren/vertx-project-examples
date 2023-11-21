package github.mirrentools.core.enums;

/**
 * 状态码与状态信息的枚举类,<br>
 * 请注意这个状态码并不是HTTP的响应状态码,而是统一的自定义协议,以便于调用者同意处理<br>
 * 调用者应该根据code做提示以及,而不是直接提示msg除非调用者原因不考虑加入其他语言
 *
 * @author <a href="https://github.com/shenzhenMirren">Mirren</a>
 */
public enum ResultState {
  /**
   * 成功
   */
  SUCCESS(200, "成功！"),
  /**
   * 登录验证凭证无效
   */
  UNAUTHORIZED(401, "登录验证凭证无效"),
  /**
   * 无效的URL请求,或者指定URL不存在
   */
  NOT_FOUND(404, "无效的URL请求,或者指定URL不存在"),
  /**
   * 无效的请求方法
   */
  METHOD_NOT_ALLOWED(405, "无效的请求方式!"),
  /**
   * 数据中缺少了必填的项或存在空值！
   */
  MISS_PARAMETER(412, "数据中缺少了必填的项或存在空值！"),

  /**
   * 数据不符合规定范围
   */
  INVALID_PARAMETER(413, "数据不符合规定范围"),
  /**
   * 操作失败，请稍后重试！
   */
  ERROR(500, "操作失败，请稍后重试！"),


  ;

  // 状态码
  private final int code;
  private final String msg;

  ResultState(int code, String msg) {
    this.msg = msg;
    this.code = code;
  }

  /**
   * 状态码
   */
  public int getCode() {
    return code;
  }

  /**
   * 状态信息
   */
  public String getMsg() {
    return msg;
  }

}
