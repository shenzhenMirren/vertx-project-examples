package github.mirrentools.core.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ID 生成器
 *
 * @author <a href="https://github.com/shenzhenMirren">Mirren</a>
 * @version 创建时间：2021-5-10 16:13:27
 */
public class IdUtil {
  /**
   * 获取一个根据EpochDay加上时分秒与6位随机数形成的id

   */
  public static String id() {
    long day = LocalDate.now().toEpochDay();
    String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmsss"));
    String suffix = StringUtil.randomString(7).toLowerCase();
    return day + time + suffix;
  }

}
