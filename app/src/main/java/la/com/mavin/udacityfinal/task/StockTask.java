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

import la.com.mavin.udacityfinal.model.Stock;

/**
 * Created by adsavin on 30/03/15.
 */
public class StockTask extends AsyncTask<String, Void, Void> {
    private final String LOG_TAG = getClass().getSimpleName();
    private final Context context;

    public StockTask(Context context) {
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
            String baseurl = "http://stocx.webatu.com/index.php?r=site/showStock";
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
            String jsonStock = stringBuilder.toString();
            Log.d(LOG_TAG, "JsonString=" + jsonStock);
            JSONArray dailyStock = new JSONArray(jsonStock);

            if (dailyStock.length() > 0) {
                ContentValues[] values = new ContentValues[dailyStock.length()];
                for (int i = 0; i < dailyStock.length(); i++) {
                    JSONObject o = dailyStock.getJSONObject(i);
                    ContentValues value = new ContentValues();
                    value.put(Stock.COL_CHANGED, o.getString(Stock.COL_CHANGED));
                    value.put(Stock.COL_CLOSING, o.getString(Stock.COL_CLOSING));
                    value.put(Stock.COL_CODE, code);
                    value.put(Stock.COL_DATE, o.getLong(Stock.COL_DATE));
                    value.put(Stock.COL_HIGH, o.getString(Stock.COL_HIGH));
                    value.put(Stock.COL_LOW, o.getString(Stock.COL_LOW));
                    value.put(Stock.COL_OPENING, o.getString(Stock.COL_OPENING));
                    value.put(Stock.COL_VALUE, o.getLong(Stock.COL_VALUE));
                    value.put(Stock.COL_VOLUME, o.getLong(Stock.COL_VOLUME));
                    value.put(Stock.COL_MARKET_CAP, o.getLong(Stock.COL_MARKET_CAP));
                    value.put(Stock.COL_LISTED_SHARES, o.getLong(Stock.COL_LISTED_SHARES));
                    values[i] = value;
                }
                int rows = 0;
                if (values.length > 0) {
                    rows = context.getContentResolver().bulkInsert(Stock.getStockUri(), values);
                }
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.toString());
        }
        return null;
    }

}
