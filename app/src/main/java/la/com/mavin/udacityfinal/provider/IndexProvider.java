package la.com.mavin.udacityfinal.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import la.com.mavin.udacityfinal.database.Contract;
import la.com.mavin.udacityfinal.database.DbHelper;
import la.com.mavin.udacityfinal.model.Index;
import la.com.mavin.udacityfinal.model.IndexCode;

/**
 * Created by Adsavin on 3/30/2015.
 */
public class IndexProvider extends ContentProvider {
    private DbHelper dbHelper;
    private static final UriMatcher URI_MATCHER = buildUriMatcher();
    private static final int INDEX = 100;
    private static final int INDEX_LIST = 101;
    private static final int INDEX_WITH_DATE = 102;
    private static final int STOCK = 200;
    private static final int STOCK_LIST = 201;


    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
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

        matcher.addURI(authority, IndexCode.PATH, INDEX_LIST);
        matcher.addURI(authority, Index.PATH, INDEX);
        matcher.addURI(authority, Index.PATH + "/*/", INDEX_WITH_DATE);// /index/001/startdate=&enddate=

        matcher.addURI(authority, Contract.PATH_STOCK, STOCK);

        return matcher;
    }
}
