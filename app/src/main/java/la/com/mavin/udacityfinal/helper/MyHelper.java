package la.com.mavin.udacityfinal.helper;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by Adsavin on 3/30/2015.
 */
public class MyHelper {

    public static String formatDateToShow(String s, String d) {
        Log.d("MyHelper", s);
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

    public static String toString(Object o) {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append(o.getClass().getName());
        result.append(" Object {");
        result.append(newLine);

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = o.getClass().getDeclaredFields();

        //print field names paired with their values
        for (Field field : fields) {
            result.append("  ");
            try {
                result.append(field.getName());
                result.append(": ");
                //requires access to private field:
                result.append(field.get(o));
            } catch (IllegalAccessException ex) {
                Log.d(o.getClass().getSimpleName(), ex.getMessage());
            }
            result.append(newLine);
        }
        result.append("}");

        return result.toString();
    }
}
