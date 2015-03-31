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
import la.com.mavin.udacityfinal.model.IndexCode;

/**
 * Created by Adsavin on 3/31/2015.
 */
public class IndexListProvider extends ContentProvider {
    private final String LOG_TAG = getClass().getSimpleName();
    private DbHelper dbHelper;
    private static final int INDEX_LIST = 101;
    private static final int INDEX_GET = 111;
    private static final int INDEX_LISTBY = 121;
    private static final UriMatcher URI_MATCHER = buildUriMatcher();

    @Override
    public boolean onCreate() {
        this.dbHelper = new DbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        Log.d(LOG_TAG, uri.toString());
        if (URI_MATCHER.match(uri) == INDEX_LISTBY) {
            retCursor = dbHelper.getReadableDatabase().query(
                    IndexCode.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
            );
        } else {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case INDEX_LIST:
                return IndexCode.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (URI_MATCHER.match(uri) == INDEX_LIST) {
            long id = dbHelper.getWritableDatabase().insert(
                    IndexCode.TABLE_NAME,
                    null,
                    values
            );
            return IndexCode.getIndexUri(id);
        } else {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
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
        Log.d(LOG_TAG, "bulkInsert...");
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = URI_MATCHER.match(uri);
        int count = 0;
        if (match == INDEX_LIST) {
            db.beginTransaction();

            try {
                for (ContentValues value : values) {
                    long _id = db.insert(IndexCode.TABLE_NAME, null, value);
                    if (_id != -1) {
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

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = Contract.CONTENT_AUTHORITY;
        matcher.addURI(authority, IndexCode.PATH, INDEX_LIST);
        matcher.addURI(authority, IndexCode.PATH + "/all", INDEX_LISTBY);
        matcher.addURI(authority, IndexCode.PATH + "/#", INDEX_GET);
        return matcher;
    }

}