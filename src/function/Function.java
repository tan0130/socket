package function;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * create by 1311230692@qq.com on 2018/8/13 15:27
 * 一些方法实现
 **/
public class Function {
    // 获取当前时间
    public static String getCurrentTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        return localDateTime.format(dateTimeFormatter);
    }
}
