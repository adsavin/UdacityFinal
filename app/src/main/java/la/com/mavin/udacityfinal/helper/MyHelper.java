package la.com.mavin.udacityfinal.helper;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Adsavin on 3/30/2015.
 */
public class MyHelper {
    public static String formatDateToShow(String s, String d) {
        return s.substring(6, 8) + d + s.substring(4, 6) + d + s.substring(0, 4);
    }

    public static String formatDateToDb(String s) {
        s = s.replace("/","");
        return s.substring(4, 8) + s.substring(2, 4) + s.substring(0, 2);
    }

    public static String formatDate(int year, int month, int day) {
        String s = Integer.toString(year);
        s += month < 10 ? "0" : "";
        s += month;
        s += day < 10 ? "0" : "";
        s += day;
        return s;
    }

    public static String formatCurrentDate() {
        return formatDate(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
    }
}
