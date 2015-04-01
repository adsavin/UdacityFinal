package la.com.mavin.udacityfinal.model;

import android.content.ContentResolver;
import android.content.ContentUris;
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

    public static final Uri CONTENT_URI = Contract.BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + Contract.CONTENT_AUTHORITY + "/" + PATH;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + Contract.CONTENT_AUTHORITY + "/" + PATH;

    public static final String[] COLUMNS = {
            Index.TABLE_NAME + "." + IndexCode._ID,
            Index.COL_CHANGED,
                    Index.COL_CHANGED_PERCENT,
                    Index.COL_CLOSING,
                    Index.COL_CODE,
                    Index.COL_DATE,
                    Index.COL_HIGH,
                    Index.COL_LOW,
                    Index.COL_NAME,
                    Index.COL_OPENING,
                    Index.COL_PREVIOUS_DAY,
                    Index.COL_VALUE,
                    Index.COL_VOLUME
    };

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

    public static Uri getIndexUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    public static Uri getIndexUri() {
        return CONTENT_URI;
    }

    public static Uri getIndexUri(String code) {
        return CONTENT_URI.buildUpon()
                .appendQueryParameter("code", code)
                .build();
    }

    public static Uri getIndexUri(String code, String startDate) {
        return CONTENT_URI.buildUpon()
                .appendQueryParameter("code", code)
                .appendQueryParameter("startDate", startDate)
                .build();
    }

    public static Uri getIndexUri(String code, String startDate, String endDate) {
        return CONTENT_URI.buildUpon()
                .appendQueryParameter("code", code)
                .appendQueryParameter("startDate", startDate)
                .appendQueryParameter("endDate", endDate)
                .build();
    }
}
