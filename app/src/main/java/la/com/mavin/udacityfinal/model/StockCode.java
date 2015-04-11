package la.com.mavin.udacityfinal.model;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import la.com.mavin.udacityfinal.database.Contract;

/**
 * Created by adsavin on 30/03/15.
 */
public class StockCode implements BaseColumns {
    public static final String TABLE_NAME = "stock_code";
    public static final String COL_CODE = "code";
    public static final String COL_NAME = "name";
    public static final int ID = 0;
    public static final int CODE = 1;
    public static final int NAME = 2;
    private String code;
    private String name;
    public static final String PATH = "stocklist";
    public static final Uri CONTENT_URI = Contract.BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + Contract.CONTENT_AUTHORITY + "/" + PATH;

    public static final String[] COLUMNS = {
            StockCode.TABLE_NAME + "." + StockCode._ID,
            StockCode.COL_CODE,
            StockCode.COL_NAME
    };

    public static StockCode toObject(Cursor cursor) {
        StockCode model = new StockCode();
        model.setName(cursor.getString(NAME));
        model.setCode(cursor.getString(CODE));
        return model;
    }

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

    public static Uri getStockListUri() {
        Uri uri = CONTENT_URI.buildUpon().appendPath("all").build();
        Log.d("StockCode", uri.toString());
        return uri;
    }

    public static Uri getStockUri() {
        return CONTENT_URI;
    }

    public static Uri getStockUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }
}
