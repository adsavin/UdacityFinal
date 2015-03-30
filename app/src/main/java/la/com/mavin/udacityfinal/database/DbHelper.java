package la.com.mavin.udacityfinal.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import la.com.mavin.udacityfinal.model.Index;
import la.com.mavin.udacityfinal.model.Stock;

/**
 * Created by Adsavin on 3/30/2015.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "weather.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + Index.TABLE_NAME + " (" +
                Index.CREATED_COLUMN +
                " );";
        db.execSQL(sql);

        sql = "CREATE TABLE " + Stock.TABLE_NAME + " (" +
                Stock.CREATED_COLUMN + ", " +
                Stock.COL_MARKET_CAP + " REAL NOT NULL, " +
                Stock.COL_LISTED_SHARES + " REAL NOT NULL" +
                " );";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Index.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Stock.TABLE_NAME);
        onCreate(db);
    }
}