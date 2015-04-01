package la.com.mavin.udacityfinal.task;

import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import la.com.mavin.udacityfinal.database.Contract;
import la.com.mavin.udacityfinal.model.Index;
import la.com.mavin.udacityfinal.model.IndexCode;
import la.com.mavin.udacityfinal.provider.IndexListProvider;

/**
 * Created by adsavin on 30/03/15.
 */
public class IndexTask extends AsyncTask<String, Void, Void> {
    private final String LOG_TAG = getClass().getSimpleName();
    private final Context context;

    public IndexTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        if(params.length == 0) {
            return null;
        }

        String code = params[0];
        String startDate = params[1] == null ? "": params[1];
        String endDate = params[2] == null ? "": params[2];

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        try {
            String baseurl = "http://stocx.webatu.com/index.php?r=site/showIndex";
            Uri uri = Uri.parse(baseurl).buildUpon().appendQueryParameter("code", code).build();
            if(startDate != "") {
                uri = uri.buildUpon().appendQueryParameter("startDate", startDate).build();
                if(endDate != "" ) {
                    uri = uri.buildUpon().appendQueryParameter("endDate", endDate).build();
                }
            }

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

            String jsonIndex = stringBuilder.toString();
            Log.d(LOG_TAG, "JsonString=" + jsonIndex);
            JSONArray dailyIndex = new JSONArray(jsonIndex);
//            JSONArray indexList = indices.getJSONArray("indices");

//            if(cursor.getCount() < indexList.length()) {
            if(dailyIndex.length() > 0) {
                ContentValues[] values = new ContentValues[dailyIndex.length()];
                for (int i = 0; i < dailyIndex.length(); i++) {
                    JSONObject o = dailyIndex.getJSONObject(i);

                    ContentValues value = new ContentValues();
                    value.put(Index.COL_CHANGED, o.getLong(Index.COL_CHANGED));
                    value.put(Index.COL_CHANGED_PERCENT, o.getLong(Index.COL_CHANGED_PERCENT));
                    value.put(Index.COL_CLOSING, o.getLong(Index.COL_CLOSING));
                    value.put(Index.COL_CODE, o.getString(Index.COL_CODE));
                    value.put(Index.COL_DATE, o.getLong(Index.COL_DATE));
                    value.put(Index.COL_HIGH, o.getLong(Index.COL_HIGH));
                    value.put(Index.COL_LOW, o.getLong(Index.COL_LOW));
                    value.put(Index.COL_NAME, o.getString(Index.COL_NAME));
                    value.put(Index.COL_OPENING, o.getLong(Index.COL_OPENING));
                    value.put(Index.COL_PREVIOUS_DAY, o.getLong(Index.COL_PREVIOUS_DAY));
                    value.put(Index.COL_VALUE, o.getLong(Index.COL_VALUE));
                    value.put(Index.COL_VOLUME, o.getLong(Index.COL_VOLUME));

                    values[i] = value;
                }

                int rows = 0;
                if (values.length > 0) {
                    rows = context.getContentResolver().bulkInsert(Index.getIndexUri(), values);
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
