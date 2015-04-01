package la.com.mavin.udacityfinal.model;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import la.com.mavin.udacityfinal.database.Contract;

/**
 * Created by adsavin on 30/03/15.
 */
public class IndexCode implements BaseColumns {
    public static final String TABLE_NAME = "index_code";
    public static final String COL_CODE = "code";
    public static final String COL_NAME = "name";
    public static final int ID = 0;
    public static final int CODE = 1;
    public static final int NAME = 2;
    private String code;
    private String name;
    public static final String PATH = "indexlist";
    public static final Uri CONTENT_URI = Contract.BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + Contract.CONTENT_AUTHORITY + "/" + PATH;

    public static final String[] COLUMNS = {
            IndexCode.TABLE_NAME + "." + IndexCode._ID,
            IndexCode.COL_CODE,
            IndexCode.COL_NAME
    };

    public static IndexCode toObject(Cursor cursor) {
        IndexCode model = new IndexCode();
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


    public static Uri getIndexListUri() {
        Uri uri = CONTENT_URI.buildUpon().appendPath("all").build();
        return uri;
    }

    public static Uri getIndexUri() {
        return CONTENT_URI;
    }

    public static Uri getIndexUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }
}
