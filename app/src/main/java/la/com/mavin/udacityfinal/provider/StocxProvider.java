package la.com.mavin.udacityfinal.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import la.com.mavin.udacityfinal.database.Contract;
import la.com.mavin.udacityfinal.database.DbHelper;
import la.com.mavin.udacityfinal.helper.MyHelper;
import la.com.mavin.udacityfinal.model.Index;
import la.com.mavin.udacityfinal.model.IndexCode;
import la.com.mavin.udacityfinal.model.Stock;
import la.com.mavin.udacityfinal.model.StockCode;

/**
 * Created by Adsavin on 3/31/2015.
 */
public class StocxProvider extends ContentProvider {
    private final String LOG_TAG = getClass().getSimpleName();
    private DbHelper dbHelper;
    private static final int INDEX = 10;
    private static final int INDEX_WITH_CODE = 11;
    private static final int INDEX_WITH_STARTDATE = 12;
    private static final int INDEX_WITH_START_ENDDATE = 13;
    public static final int INDEX_LIST = 20;
    public static final int INDEX_GETBYID = 21;
    public static final int INDEX_LISTALL = 22;

    private static final int STOCK = 30;
    private static final int STOCK_WITH_CODE = 31;
    private static final int STOCK_WITH_STARTDATE = 32;
    private static final int STOCK_WITH_START_ENDDATE = 33;
    public static final int STOCK_LIST = 40;
    public static final int STOCK_GETBYID = 41;
    public static final int STOCK_LISTALL = 42;

    private static final UriMatcher URI_MATCHER = buildUriMatcher();

    @Override
    public boolean onCreate() {
        this.dbHelper = new DbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        int match = URI_MATCHER.match(uri);
        Log.d(LOG_TAG, "ProviderMatch: " + match);
        String indexcode = getIndexCodeFromUri(uri);
        String stockcode = getStockCodeFromUri(uri);
        switch (match) {
            case INDEX_LISTALL:
                retCursor = dbHelper.getReadableDatabase().query(
                        IndexCode.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case INDEX_WITH_CODE:
                sortOrder = "date desc";
                retCursor = dbHelper.getReadableDatabase().query(
                        Index.TABLE_NAME,
                        projection,
                        Index.COL_CODE + "=?",
                        new String[]{getIndexCodeFromUri(uri)},
                        null,
                        null,
                        sortOrder
                );
                break;
            case INDEX_WITH_STARTDATE:
                sortOrder = "date desc";
                retCursor = dbHelper.getReadableDatabase().query(
                        Index.TABLE_NAME,
                        projection,
                        Index.COL_CODE + " = ? AND " + Index.COL_DATE + " > ?",
                        new String[]{
                                indexcode,
                                Long.toString(getDateFromUri(uri, 2))
                        },
                        null,
                        null,
                        sortOrder
                );
                break;
            case INDEX_WITH_START_ENDDATE:
                sortOrder = "date desc";
                retCursor = dbHelper.getReadableDatabase().query(
                        Index.TABLE_NAME,
                        projection,
                        Index.COL_CODE + " = ? AND " + Index.COL_DATE + " > ? AND " + Index.COL_DATE + " < ?",
                        new String[]{
                                indexcode,
                                Long.toString(getDateFromUri(uri, 2)),
                                Long.toString(getDateFromUri(uri, 3))
                        },
                        null,
                        null,
                        sortOrder
                );
                break;
            case STOCK_LISTALL:
                retCursor = dbHelper.getReadableDatabase().query(
                        StockCode.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case STOCK_WITH_CODE:
                sortOrder = "date desc";
                retCursor = dbHelper.getReadableDatabase().query(
                        Stock.TABLE_NAME,
                        projection,
                        Stock.COL_CODE + "=?",
                        new String[]{getIndexCodeFromUri(uri)},
                        null,
                        null,
                        sortOrder
                );
                break;
            case STOCK_WITH_STARTDATE:
                sortOrder = "date desc";
                retCursor = dbHelper.getReadableDatabase().query(
                        Stock.TABLE_NAME,
                        projection,
                        Stock.COL_CODE + " = ? AND " + Stock.COL_DATE + " > ?",
                        new String[]{
                                stockcode,
                                Long.toString(getDateFromUri(uri, 2))
                        },
                        null,
                        null,
                        sortOrder
                );
                break;
            case STOCK_WITH_START_ENDDATE:
                sortOrder = "date desc";
                retCursor = dbHelper.getReadableDatabase().query(
                        Stock.TABLE_NAME,
                        projection,
                        Stock.COL_CODE + " = ? AND " + Stock.COL_DATE + " > ? AND " + Stock.COL_DATE + " < ?",
                        new String[]{
                                stockcode,
                                Long.toString(getDateFromUri(uri, 2)),
                                Long.toString(getDateFromUri(uri, 3))
                        },
                        null,
                        null,
                        sortOrder
                );
                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri.toString());
        }

        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case INDEX_LIST:
                return IndexCode.CONTENT_TYPE;
            case INDEX:
                return Index.CONTENT_ITEM_TYPE;
            case INDEX_WITH_CODE:
                return Index.CONTENT_TYPE;
            case INDEX_WITH_STARTDATE:
                return Index.CONTENT_TYPE;
            case INDEX_WITH_START_ENDDATE:
                return Index.CONTENT_TYPE;

            case STOCK_LIST:
                return StockCode.CONTENT_TYPE;
            case STOCK:
                return Stock.CONTENT_ITEM_TYPE;
            case STOCK_WITH_CODE:
                return Stock.CONTENT_TYPE;
            case STOCK_WITH_STARTDATE:
                return Stock.CONTENT_TYPE;
            case STOCK_WITH_START_ENDDATE:
                return Stock.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id;
        switch (URI_MATCHER.match(uri)) {
            case INDEX_LIST:
                id = dbHelper.getWritableDatabase().insert(
                        IndexCode.TABLE_NAME,
                        null,
                        values
                );
                if (id > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return IndexCode.getIndexUri(id);
                } else {
                    return null;
                }
            case INDEX:
                id = dbHelper.getWritableDatabase().insert(
                        Index.TABLE_NAME,
                        null,
                        values
                );
                if (id > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return Index.getIndexUri(id);
                } else {
                    return null;
                }
            case STOCK_LIST:
                id = dbHelper.getWritableDatabase().insert(
                        StockCode.TABLE_NAME,
                        null,
                        values
                );
                if (id > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return StockCode.getStockUri(id);
                } else {
                    return null;
                }
            case STOCK:
                id = dbHelper.getWritableDatabase().insert(
                        Stock.TABLE_NAME,
                        null,
                        values
                );
                if (id > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return Index.getIndexUri(id);
                } else {
                    return null;
                }
            default:
                throw new android.database.SQLException("Failed to insert row into " + uri);

        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = URI_MATCHER.match(uri);
        int count = 0;
        switch (match) {
            case INDEX_LIST:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long id = db.insert(IndexCode.TABLE_NAME, null, value);
                        if (id != -1) {
                            count++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                    db.close();
                }
                break;
            case INDEX:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long id = db.insert(Index.TABLE_NAME, null, value);
                        if (id != -1) {
                            count++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                    db.close();
                }
                break;

            case STOCK_LIST:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long id = db.insert(StockCode.TABLE_NAME, null, value);
                        if (id != -1) {
                            count++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                    db.close();
                }
                break;
            case STOCK:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long id = db.insert(Stock.TABLE_NAME, null, value);
                        if (id != -1) {
                            count++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                    db.close();
                }
                break;

            default:
                return super.bulkInsert(uri, values);
        }
        Log.d(LOG_TAG, "count=" + count);
//        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
//        }
        return count;
    }

    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = Contract.CONTENT_AUTHORITY;
        matcher.addURI(authority, IndexCode.PATH, INDEX_LIST);
        matcher.addURI(authority, IndexCode.PATH + "/all", INDEX_LISTALL);
        matcher.addURI(authority, IndexCode.PATH + "/#", INDEX_GETBYID);

        matcher.addURI(authority, Index.PATH, INDEX);
        matcher.addURI(authority, Index.PATH + "/*", INDEX_WITH_CODE);
        matcher.addURI(authority, Index.PATH + "/#", INDEX_WITH_STARTDATE);
        matcher.addURI(authority, Index.PATH + "/#/#", INDEX_WITH_START_ENDDATE);

        matcher.addURI(authority, StockCode.PATH, STOCK_LIST);
        matcher.addURI(authority, StockCode.PATH + "/all", STOCK_LISTALL);
        matcher.addURI(authority, StockCode.PATH + "/#", STOCK_GETBYID);

        matcher.addURI(authority, Stock.PATH, STOCK);
        matcher.addURI(authority, Stock.PATH + "/*", STOCK_WITH_CODE);
        matcher.addURI(authority, Stock.PATH + "/#", STOCK_WITH_STARTDATE);
        matcher.addURI(authority, Stock.PATH + "/#/#", STOCK_WITH_START_ENDDATE);

        return matcher;
    }

    public static String getIndexCodeFromUri(Uri uri) {
        return uri.getPathSegments().get(1).toString();
    }

    public static String getStockCodeFromUri(Uri uri) {
        return uri.getPathSegments().get(1).toString();
    }

    public static long getDateFromUri(Uri uri, int i) {
        return Long.parseLong(uri.getPathSegments().get(i));
    }
}
