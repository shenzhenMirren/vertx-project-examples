package github.mirrentools.core.utils;

/**
 * token工具,<br>
 * 为了节省鉴权耗时可以将用户的token在会话中(下称:系统token),<br>
 * 当判断到用户token与系统token不一致时可调用本工具的isNewVersion方法判断用户的token是不是比系统token新,<br>
 * 如果比系统新则系统需要重新获取token并更新缓存,反则用户的token过期或无效
 *
 * @author <a href="https://github.com/shenzhenMirren">Mirren</a>
 */
public class TokenUtil {
  /**
   * 编码token的混淆秘钥
   */
  private static final String SECRET = "org.your.teams";

  /**
   * 生成一个token
   */
  public static String getToken(String userId) {
    String version = String.format("%015x", System.currentTimeMillis());
    String code = MD5Util.encode(SECRET + version + userId);
    assert code != null;
    String prefix = code.substring(0, 7);
    String suffix = code.substring(12);
    return (prefix + version + suffix).substring(0, 32);
  }

  /**
   * 获得token中的版本号
   */
  public static long getTokenVersion(String token) {
    if (token == null || token.length() < 23) {
      return 0L;
    }
    try {
      return Long.parseLong(token.substring(7, 22), 16);
    } catch (Exception e) {
      return 0L;
    }
  }

  /**
   * 判断用户的token版本号是否比系统缓存的版本号大
   *
   * @param sysToken  系统的token
   * @param userToken 用户的token
   * @return true代表用户的token比系统的token大, false代表版本相同或比系统小伙无效的token
   */
  public static boolean isNewVersion(String sysToken, String userToken) {
    long sys = getTokenVersion(sysToken);
    long user = getTokenVersion(userToken);
    return user > sys;
  }

}
