package la.com.mavin.udacityfinal.task;

import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.HttpURLConnection;

import la.com.mavin.udacityfinal.database.Contract;
import la.com.mavin.udacityfinal.model.IndexCode;
import la.com.mavin.udacityfinal.provider.IndexListProvider;

/**
 * Created by adsavin on 30/03/15.
 */
public class IndexListTask extends AsyncTask<Void, Void, Void> {
    private final String LOG_TAG = getClass().getSimpleName();
    private final Context context;

    public IndexListTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        try {
<<<<<<< HEAD
//            String baseurl = "http://stocx.webatu.com/index.php?r=site/listIndex";
//            Uri uri = Uri.parse(baseurl).buildUpon().build();
//            URL url = new URL(uri.toString());
//            httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setRequestMethod("GET");
//            httpURLConnection.connect();
//
//            InputStream inputStream = httpURLConnection.getInputStream();
//            if(inputStream == null) {
//                return null;
//            }
//
//            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            String line;
//            StringBuilder stringBuilder = new StringBuilder();
//            while ((line = bufferedReader.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//
//            if(stringBuilder.length() == 0) {
//                return null;
//            }

//            String jsonIndexList = stringBuilder.toString();
            String jsonIndexList = "{\"indices\":[{\"code\":\"001\",\"name\":\"LSX Composite\"},{\"code\":\"002\",\"name\":\"Nikei\"}]}";
=======
            String baseurl = "http://stocx.webatu.com/index.php?r=site/listIndex";
            Uri uri = Uri.parse(baseurl).buildUpon().build();
            Log.d(LOG_TAG, uri.toString());
            URL url = new URL(uri.toString());
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            if(inputStream == null) {
                return null;
            }

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            if(stringBuilder.length() == 0) {
                return null;
            }

            String jsonIndexList = stringBuilder.toString();
>>>>>>> origin/master
            JSONObject indices = new JSONObject(jsonIndexList);
            JSONArray indexList = indices.getJSONArray("indices");

            Cursor cursor = context.getContentResolver().query(
                    IndexCode.getIndexListUri(),
                    IndexCode.COLUMNS,
                    null,
                    null,
                    null
            );

            Log.d(LOG_TAG, "Cursor=" + cursor.getCount() + ",Json=" + indexList.length());

            if(cursor.getCount() < indexList.length()) {
                ContentValues[] values = new ContentValues[indexList.length() - cursor.getCount()];
                int j = 0;
                if(cursor.getCount() > 0) {
                    j = cursor.getCount();
                }
                for (int i = j; i < indexList.length(); i++) {
                    JSONObject o = indexList.getJSONObject(i);

                    ContentValues value = new ContentValues();
                    value.put(IndexCode.COL_CODE, o.getString(IndexCode.COL_CODE));
                    value.put(IndexCode.COL_NAME, o.getString(IndexCode.COL_NAME));
                    values[i] = value;
                }

                int rows = 0;
                if (values.length > 0) {
                    rows = context.getContentResolver().bulkInsert(IndexCode.getIndexUri(), values);
                }

                Log.d(LOG_TAG, "Inserted " + rows + " row(s).");
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.toString());
        }

        return null;
    }

    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = Contract.CONTENT_AUTHORITY;
        matcher.addURI(authority, IndexCode.PATH, IndexListProvider.INDEX_LIST);
        matcher.addURI(authority, IndexCode.PATH + "/all", IndexListProvider.INDEX_LISTALL);
        matcher.addURI(authority, IndexCode.PATH + "/#", IndexListProvider.INDEX_GETBYID);
        return matcher;
    }
}
