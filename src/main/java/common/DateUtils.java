package common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by teacher ZHANG on 2020/2/21
 */
public class DateUtils {
    //获取下一周的日期
    public static List<String> getNextWeek() {
        List<String> days = new ArrayList<>();

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar = Calendar.getInstance(); //获取日历（单例模式）

        for(int i = 0; i < 7; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);

            days.add(fmt.format(calendar.getTime()));
        }

        return days;
    }

    //获取下一天的日期
    public static Date getNextDate() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar = Calendar.getInstance(); //获取日历（单例模式）

        calendar.add(Calendar.DAY_OF_MONTH, 1);

        return calendar.getTime();
    }

    //判断是否当天休息
    public static Boolean isDayOff(Date date, Integer off) {
        Calendar calendar = Calendar.getInstance();

        //日期转为日历
        calendar.setTime(date);

        //取星期几
        Integer day = calendar.get(Calendar.DAY_OF_WEEK);

        System.out.println(day);

        if (day.equals(off)) {
            return true;
        }
        else {
            return false;
        }
    }

    public static String getDateString(Date date) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        return fmt.format(date);
    }
}
