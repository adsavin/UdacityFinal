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

/**
 * Created by Adsavin on 3/30/2015.
 */
public class IndexProvider extends ContentProvider {
    private final String LOG_TAG = getClass().getSimpleName();

    private DbHelper dbHelper;
    private static final UriMatcher URI_MATCHER = buildUriMatcher();
    private static final int INDEX = 100;
    private static final int INDEX_WITH_CODE = 101;
    private static final int INDEX_WITH_STARTDATE = 102;
    private static final int INDEX_WITH_START_ENDDATE = 103;


    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor = null;
        Log.d(LOG_TAG, uri.toString());
        switch (URI_MATCHER.match(uri)) {
            case INDEX_WITH_CODE: // /*
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
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
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
        Uri retUri = null;
        if(URI_MATCHER.match(uri) == INDEX) {
            long id = dbHelper.getWritableDatabase().insert(
                    Index.TABLE_NAME,
                    null,
                    values
            );
            if(id > 0) {
                retUri = Index.getIndexUri(id);
                getContext().getContentResolver().notifyChange(uri, null);
            } else {
                throw new android.database.SQLException("Failed to insert row into " + uri);
            }
        }

        return retUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int count = 0;
        if(URI_MATCHER.match(uri) == INDEX) {
            final SQLiteDatabase db = dbHelper.getWritableDatabase();
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

        } else {
            throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = Contract.CONTENT_AUTHORITY;

        matcher.addURI(authority, Index.PATH, INDEX);
        matcher.addURI(authority, Index.PATH + "/*/#/", INDEX_WITH_STARTDATE);
        matcher.addURI(authority, Index.PATH + "/*/#/#", INDEX_WITH_START_ENDDATE);

        return matcher;
    }

    public static String getIndexCodeFromUri(Uri uri) {
        return uri.getPathSegments().get(1);
    }

    public static long getDateFromUri(Uri uri, int i) {
        return Long.parseLong(uri.getPathSegments().get(i));
    }
}
