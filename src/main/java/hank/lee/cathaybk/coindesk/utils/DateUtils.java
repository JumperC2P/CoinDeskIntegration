package hank.lee.cathaybk.coindesk.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateUtils {
    public static final String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";

    public static String convertTimestampToString(Timestamp timestamp) {
        return new SimpleDateFormat(DATE_TIME_FORMAT).format(timestamp);
    }
}
