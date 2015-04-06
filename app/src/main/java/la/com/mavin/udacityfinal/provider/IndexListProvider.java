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
import la.com.mavin.udacityfinal.model.Index;
import la.com.mavin.udacityfinal.model.IndexCode;

/**
 * Created by Adsavin on 3/31/2015.
 */
public class IndexListProvider extends ContentProvider {
    private final String LOG_TAG = getClass().getSimpleName();
    private DbHelper dbHelper;
    private static final int INDEX = 10;
    private static final int INDEX_WITH_CODE = 11;
    private static final int INDEX_WITH_STARTDATE = 12;
    private static final int INDEX_WITH_START_ENDDATE = 13;
    public static final int INDEX_LIST = 20;
    public static final int INDEX_GETBYID = 21;
    public static final int INDEX_LISTALL = 22;
    private static final UriMatcher URI_MATCHER = buildUriMatcher();

    @Override
    public boolean onCreate() {
        this.dbHelper = new DbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (URI_MATCHER.match(uri)) {
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
                retCursor = dbHelper.getReadableDatabase().query(
                        Index.TABLE_NAME,
                        projection,
                        Index.COL_CODE + "=?",
                        new String[] {getIndexCodeFromUri(uri)},
                        null,
                        null,
                        sortOrder
                );
                break;
            case INDEX_WITH_STARTDATE:
                retCursor = dbHelper.getReadableDatabase().query(
                        Index.TABLE_NAME,
                        projection,
                        Index.COL_CODE + " = ? AND " + Index.COL_DATE + " > ?",
                        new String[]{
                                getIndexCodeFromUri(uri),
                                Long.toString(getDateFromUri(uri, 2))
                        },
                        null,
                        null,
                        sortOrder
                );
                break;
            case INDEX_WITH_START_ENDDATE:
                retCursor = dbHelper.getReadableDatabase().query(
                        Index.TABLE_NAME,
                        projection,
                        Index.COL_CODE + " = ? AND " + Index.COL_DATE + " > ? AND " + Index.COL_DATE + " < ?",
                        new String[]{
                                getIndexCodeFromUri(uri),
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
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(LOG_TAG, "Insert...");
        long id;
        switch (URI_MATCHER.match(uri)) {
            case INDEX_LIST:
                id = dbHelper.getWritableDatabase().insert(
                        IndexCode.TABLE_NAME,
                        null,
                        values
                );
                if(id > 0) {
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
                if(id > 0) {
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
        Log.d(LOG_TAG, "DB is open" + db.isOpen());
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
                        Cursor cursor = db.query(Index.TABLE_NAME, Index.COLUMNS, "date=?",new String[]{value.getAsString(Index.COL_DATE)},null,null,null);
                        if(cursor.getCount() == 0) {
                            long id = db.insert(Index.TABLE_NAME, null, value);
                            if (id != -1) {
                                count++;
                            }
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

        if(count > 0) {
            Log.d(LOG_TAG, "count=" + count + ", notify");
            getContext().getContentResolver().notifyChange(uri, null);
        }
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

        return matcher;
    }

    public static String getIndexCodeFromUri(Uri uri) {
//        return uri.getPathSegments().get(1).toString();
        return "001";
    }

    public static long getDateFromUri(Uri uri, int i) {
        return Long.parseLong(uri.getPathSegments().get(i));
    }
}
