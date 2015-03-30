package la.com.mavin.udacityfinal.task;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        String indexGroupCode = params[0].trim();
        String startDate = params[1].trim();
        String endDate = params[2].trim();

        try {
            String baseurl = "http://lsx.com.la/market/index/daily.do?";
            Uri uri = Uri.parse(baseurl).buildUpon()
                    .appendQueryParameter("indexGroupCode", indexGroupCode)
                    .appendQueryParameter("fromDate", startDate)
                    .appendQueryParameter("toDate", endDate)
                    .build();
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
            StringBuffer stringBuffer = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line.trim());
            }

            if(stringBuffer.length() == 0) {
                return null;
            }

            Log.d(LOG_TAG, stringBuffer.toString());
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.toString());
        }

        return null;
    }
}
