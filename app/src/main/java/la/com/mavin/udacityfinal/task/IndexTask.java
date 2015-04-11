package la.com.mavin.udacityfinal.task;

import android.content.ContentValues;
import android.content.Context;
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

import la.com.mavin.udacityfinal.model.Index;

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
        if (params.length == 0) {
            return null;
        }
        String code = params[0];
        String startDate = "";
        if (params.length > 1) startDate = params[1];
        String endDate = "";
        if (params.length > 2) endDate = params[2];
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        try {
            String baseurl = "http://stocx.webatu.com/index.php?r=site/showIndex";
            Uri uri = Uri.parse(baseurl).buildUpon().appendQueryParameter("code", code).build();

            if (startDate != "") {
                uri = uri.buildUpon().appendQueryParameter("startDate", startDate).build();
                if (endDate != "") {
                    uri = uri.buildUpon().appendQueryParameter("endDate", endDate).build();
                }
            }
            URL url = new URL(uri.toString());
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            if (inputStream == null) {
                return null;
            }
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            if (stringBuilder.length() == 0) {
                return null;
            }
            String jsonIndex = stringBuilder.toString();
            Log.d(LOG_TAG, "JsonString=" + jsonIndex);
            JSONArray dailyIndex = new JSONArray(jsonIndex);

            if (dailyIndex.length() > 0) {
                ContentValues[] values = new ContentValues[dailyIndex.length()];
                for (int i = 0; i < dailyIndex.length(); i++) {
                    JSONObject o = dailyIndex.getJSONObject(i);
                    ContentValues value = new ContentValues();
                    value.put(Index.COL_CHANGED, o.getString(Index.COL_CHANGED));
                    value.put(Index.COL_CHANGED_PERCENT, o.getString(Index.COL_CHANGED_PERCENT));
                    value.put(Index.COL_CLOSING, o.getString(Index.COL_CLOSING));
                    value.put(Index.COL_CODE, code);
                    value.put(Index.COL_DATE, o.getLong(Index.COL_DATE));
                    value.put(Index.COL_HIGH, o.getString(Index.COL_HIGH));
                    value.put(Index.COL_LOW, o.getString(Index.COL_LOW));
                    value.put(Index.COL_OPENING, o.getString(Index.COL_OPENING));
                    value.put(Index.COL_VALUE, o.getLong(Index.COL_VALUE));
                    value.put(Index.COL_VOLUME, o.getLong(Index.COL_VOLUME));
                    values[i] = value;
                }
                int rows = 0;
                if (values.length > 0) {
                    rows = context.getContentResolver().bulkInsert(Index.getIndexUri(), values);
                }
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.toString());
        }
        return null;
    }

}
