package la.com.mavin.udacityfinal.model;

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
        return Contract.BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
    }

}
