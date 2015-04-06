package la.com.mavin.udacityfinal.model;

import android.provider.BaseColumns;

/**
 * Created by Adsavin on 3/30/2015.
 */
public class BaseModel implements BaseColumns {
    public static final String COL_CODE = "code";
    public static final String COL_NAME = "name";
    public static final String COL_PREVIOUS_DAY = "previous_day";
    public static final String COL_DATE = "date";
    public static final String COL_OPENING = "opening";
    public static final String COL_CLOSING = "closing";
    public static final String COL_CHANGED = "changed";
    public static final String COL_CHANGED_PERCENT = "changed_percent";
    public static final String COL_HIGH  = "high";
    public static final String COL_LOW = "low";
    public static final String COL_VOLUME = "volume";
    public static final String COL_VALUE = "value";

    public static final int ID = 0;
    public static final int CHANGED = 1;
    public static final int CHANGED_PERCENT = 2;
    public static final int CLOSING = 3;
    public static final int CODE = 4;
    public static final int DATE = 5;
    public static final int HIGH = 6;
    public static final int LOW = 7;
    public static final int NAME = 8;
    public static final int OPENING = 9;
    public static final int PREVIOUS_DAY = 10;
    public static final int VALUE = 11;
    public static final int VOLUME = 12;

    public static final String CREATED_COLUMN =
            COL_CHANGED + " REAL NOT NULL, " +
            COL_CHANGED_PERCENT + " REAL NOT NULL, " +
            COL_CLOSING + " REAL NOT NULL, " +
            COL_CODE + " TEXT NOT NULL, " +
            COL_DATE + " TEXT, " +
            COL_HIGH + " REAL NOT NULL, " +
            COL_LOW + " REAL NOT NULL, " +
            COL_NAME + " TEXT, " +
            COL_OPENING + " REAL NOT NULL, " +
            COL_PREVIOUS_DAY + " REAL, " +
            COL_VALUE + " REAL NOT NULL, " +
            COL_VOLUME + " REAL NOT NULL";

    private String code;
    private String name;
    private long previousDay;
    private String date;
    private long opening;
    private long closing;
    private long changed;
    private long changedPercent;
    private long high;
    private long low;
    private long volume;
    private long value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPreviousDay() {
        return previousDay;
    }

    public void setPreviousDay(long previousDay) {
        this.previousDay = previousDay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getOpening() {
        return opening;
    }

    public void setOpening(long opening) {
        this.opening = opening;
    }

    public long getClosing() {
        return closing;
    }

    public void setClosing(long closing) {
        this.closing = closing;
    }

    public long getChanged() {
        return changed;
    }

    public void setChanged(long changed) {
        this.changed = changed;
    }

    public long getChangedPercent() {
        return changedPercent;
    }

    public void setChangedPercent(long changedPercent) {
        this.changedPercent = changedPercent;
    }

    public long getHigh() {
        return high;
    }

    public void setHigh(long high) {
        this.high = high;
    }

    public long getLow() {
        return low;
    }

    public void setLow(long low) {
        this.low = low;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
