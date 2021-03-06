package la.com.mavin.udacityfinal.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import la.com.mavin.udacityfinal.model.Index;
import la.com.mavin.udacityfinal.model.IndexCode;
import la.com.mavin.udacityfinal.model.Stock;
import la.com.mavin.udacityfinal.model.StockCode;

/**
 * Created by Adsavin on 3/30/2015.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 12;
    static final String DATABASE_NAME = "stocx.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + IndexCode.TABLE_NAME + " (" +
                IndexCode._ID + " INTEGER PRIMARY KEY, " +
                IndexCode.COL_CODE + " TEXT NOT NULL, " +
                IndexCode.COL_NAME + " TEXT NOT NULL" +
                " );";
        db.execSQL(sql);

        sql = "CREATE TABLE " + StockCode.TABLE_NAME + " (" +
                StockCode._ID + " INTEGER PRIMARY KEY, " +
                StockCode.COL_CODE + " TEXT NOT NULL, " +
                StockCode.COL_NAME + " TEXT NOT NULL" +
                " );";
        db.execSQL(sql);

        sql = "CREATE TABLE " + Index.TABLE_NAME + " (" +
                Index._ID + " INTEGER PRIMARY KEY," +
                Index.CREATED_COLUMN +
                ", " + Index.COL_CHANGED_PERCENT + " REAL" +
                ", " + Index.COL_PREVIOUS_DAY + " REAL" +
                ", UNIQUE (" + Index.COL_DATE + ") ON CONFLICT REPLACE " +
                ");";
        db.execSQL(sql);

        sql = "CREATE TABLE " + Stock.TABLE_NAME + " (" +
                Stock._ID + " INTEGER PRIMARY KEY," +
                Stock.CREATED_COLUMN + ", " +
                Stock.COL_MARKET_CAP + " REAL NOT NULL, " +
                Stock.COL_LISTED_SHARES + " REAL NOT NULL" +
                ", UNIQUE (" + Stock.COL_DATE + ") ON CONFLICT REPLACE " +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + IndexCode.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + StockCode.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Index.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Stock.TABLE_NAME);
        onCreate(db);
    }
}
