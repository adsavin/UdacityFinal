package la.com.mavin.udacityfinal.task;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

import la.com.mavin.udacityfinal.model.StockCode;

/**
 * Created by Adsavin on 4/11/2015.
 */
public class StockListTask extends AsyncTask<Void, Void, Void> {
    private final String LOG_TAG = getClass().getSimpleName();
    private final Context context;

    public StockListTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        try {
            String baseurl = "http://stocx.webatu.com/index.php?r=site/listStock";
            Uri uri = Uri.parse(baseurl).buildUpon().build();
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

            String jsonStockList = stringBuilder.toString();
            JSONObject stocks = new JSONObject(jsonStockList);
            JSONArray stockList = stocks.getJSONArray("stocks");

            Cursor cursor = context.getContentResolver().query(
                    StockCode.getStockListUri(),
                    StockCode.COLUMNS,
                    null,
                    null,
                    null
            );

            Log.d(LOG_TAG, "Cursor=" + cursor.getCount() + ",Json=" + stockList.length());

            if(cursor.getCount() < stockList.length()) {
                ContentValues[] values = new ContentValues[stockList.length() - cursor.getCount()];
                int j = 0;
                if(cursor.getCount() > 0) {
                    j = cursor.getCount();
                }
                for (int i = j; i < stockList.length(); i++) {
                    JSONObject o = stockList.getJSONObject(i);

                    ContentValues value = new ContentValues();
                    value.put(StockCode.COL_CODE, o.getString(StockCode.COL_CODE));
                    value.put(StockCode.COL_NAME, o.getString(StockCode.COL_NAME));
                    values[i] = value;
                }

                int rows = 0;
                if (values.length > 0) {
                    rows = context.getContentResolver().bulkInsert(StockCode.getStockUri(), values);
                }

                Log.d(LOG_TAG, "Inserted " + rows + " row(s).");
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.toString());
        }

        return null;
    }
}
