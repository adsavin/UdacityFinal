package la.com.mavin.udacityfinal.model;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import la.com.mavin.udacityfinal.database.Contract;

/**
 * Created by Adsavin on 3/28/2015.
 */
public class Index extends BaseModel implements BaseColumns {
    public static final String TABLE_NAME = "lsx_index";
    public static final String PATH = "index";

    public static Index toObject(Cursor cursor) {
        Index model = new Index();
        model.setChanged(cursor.getLong(CHANGED));
        model.setChangedPercent(cursor.getLong(CHANGED_PERCENT));
        model.setClosing(cursor.getLong(CLOSING));
        model.setCode(cursor.getString(CODE));
        model.setDate(cursor.getString(DATE));
        model.setHigh(cursor.getLong(HIGH));
        model.setLow(cursor.getLong(LOW));
        model.setName(cursor.getString(NAME));
        model.setOpening(cursor.getLong(OPENING));
        model.setPreviousDay(cursor.getLong(PREVIOUS_DAY));
        model.setValue(cursor.getLong(VALUE));
        model.setVolume(cursor.getLong(VOLUME));

        return model;
    }

    public static Uri getIndexUri(String code) {
        return Contract.BASE_CONTENT_URI.buildUpon().appendPath(PATH)
                .appendQueryParameter("code", code)
                .build();
    }

    public static Uri getIndexUri(String code, String startdate, String enddate) {
        return Contract.BASE_CONTENT_URI.buildUpon().appendPath(PATH)
                .appendQueryParameter("code", code)
                .appendQueryParameter("fromDate", startdate)
                .appendQueryParameter("toDate", enddate)
                .build();
    }
}
