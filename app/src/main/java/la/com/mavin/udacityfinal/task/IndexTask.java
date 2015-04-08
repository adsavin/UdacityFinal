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
        Log.d(LOG_TAG, "params=" + params.length);
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
                    Log.d(LOG_TAG, "sdate:" + startDate);
                    Log.d(LOG_TAG, "edate:" + endDate);
                }
            }
            Log.d(LOG_TAG, uri.toString());
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
//            String jsonIndex;
//            jsonIndex = "[{\"current\":\"1\",\"date\":\"20150401\",\"opening\":\"1545.36\",\"closing\":\"1544.48\",\"changed\":\" 8.78\",\"changed_percent\":\" 0.57\",\"high\":\"1545.36\",\"low\":\"1544.48\",\"volume\":\"19545\",\"value\":\"136029550\"},{\"date\":\"20150401\",\"opening\":\"1545.36\",\"closing\":\"1544.48\",\"changed\":\" 8.78\",\"changed_percent\":\" 0.57\",\"high\":\"1545.36\",\"low\":\"1544.48\",\"volume\":\"19545\",\"value\":\"136029550\"},{\"date\":\"20150331\",\"opening\":\"1534.42\",\"closing\":\"1553.26\",\"changed\":\" 3.03\",\"changed_percent\":\" 0.20\",\"high\":\"1553.26\",\"low\":\"1486.99\",\"volume\":\"27455\",\"value\":\"157729000\"},{\"date\":\"20150330\",\"opening\":\"1566.92\",\"closing\":\"1550.23\",\"changed\":\" 16.69\",\"changed_percent\":\" 1.07\",\"high\":\"1566.92\",\"low\":\"1550.23\",\"volume\":\"19368\",\"value\":\"139519500\"},{\"date\":\"20150327\",\"opening\":\"1475.09\",\"closing\":\"1566.92\",\"changed\":\" 91.83\",\"changed_percent\":\" 6.23\",\"high\":\"1569.95\",\"low\":\"1475.09\",\"volume\":\"15090\",\"value\":\"68112000\"},{\"date\":\"20150326\",\"opening\":\"1475.09\",\"closing\":\"1475.09\",\"changed\":\" 0.00\",\"changed_percent\":\" 0.00\",\"high\":\"1475.09\",\"low\":\"1475.09\",\"volume\":\"4455\",\"value\":\"33412500\"},{\"date\":\"20150325\",\"opening\":\"1459.28\",\"closing\":\"1475.09\",\"changed\":\" 23.71\",\"changed_percent\":\" 1.63\",\"high\":\"1475.09\",\"low\":\"1459.28\",\"volume\":\"54315\",\"value\":\"391441400\"},{\"date\":\"20150324\",\"opening\":\"1451.38\",\"closing\":\"1451.38\",\"changed\":\" 7.90\",\"changed_percent\":\" 0.55\",\"high\":\"1451.38\",\"low\":\"1451.38\",\"volume\":\"438486\",\"value\":\"3214680900\"},{\"date\":\"20150323\",\"opening\":\"1443.48\",\"closing\":\"1443.48\",\"changed\":\" 0.00\",\"changed_percent\":\" 0.00\",\"high\":\"1451.38\",\"low\":\"1443.48\",\"volume\":\"79594\",\"value\":\"544263000\"},{\"date\":\"20150320\",\"opening\":\"1445.48\",\"closing\":\"1443.48\",\"changed\":\" 2.00\",\"changed_percent\":\" 0.14\",\"high\":\"1445.48\",\"low\":\"1441.96\",\"volume\":\"2614\",\"value\":\"18263900\"}]";
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
                    value.put(Index.COL_NAME, "");
                    value.put(Index.COL_OPENING, o.getString(Index.COL_OPENING));
//                    if(o.getString(Index.COL_PREVIOUS_DAY) == null) {
//                        value.put(Index.COL_PREVIOUS_DAY,  0);
//                    }
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

}
