package la.com.mavin.udacityfinal.task;

import android.content.ContentValues;
import android.content.Context;
<<<<<<< HEAD
=======
import android.content.UriMatcher;
import android.net.Uri;
>>>>>>> origin/master
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.HttpURLConnection;

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
<<<<<<< HEAD
//            String baseurl = "http://stocx.webatu.com/index.php?r=site/showIndex";
//            Uri uri = Uri.parse(baseurl).buildUpon().appendQueryParameter("code", code).build();
//
//            if(startDate != "") {
//                uri = uri.buildUpon().appendQueryParameter("startDate", startDate).build();
//                if(endDate != "" ) {
//                    uri = uri.buildUpon().appendQueryParameter("endDate", endDate).build();
//                }
//            }
//
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

//            String jsonIndex = stringBuilder.toString();
            String jsonIndex;
            jsonIndex = "[{\"current\":\"1\",\"date\":\"20150401\",\"opening\":\"1545.36\",\"closing\":\"1544.48\",\"changed\":\" 8.78\",\"changed_percent\":\" 0.57\",\"high\":\"1545.36\",\"low\":\"1544.48\",\"volume\":\"19545\",\"value\":\"136029550\"},{\"date\":\"20150401\",\"opening\":\"1545.36\",\"closing\":\"1544.48\",\"changed\":\" 8.78\",\"changed_percent\":\" 0.57\",\"high\":\"1545.36\",\"low\":\"1544.48\",\"volume\":\"19545\",\"value\":\"136029550\"},{\"date\":\"20150331\",\"opening\":\"1534.42\",\"closing\":\"1553.26\",\"changed\":\" 3.03\",\"changed_percent\":\" 0.20\",\"high\":\"1553.26\",\"low\":\"1486.99\",\"volume\":\"27455\",\"value\":\"157729000\"},{\"date\":\"20150330\",\"opening\":\"1566.92\",\"closing\":\"1550.23\",\"changed\":\" 16.69\",\"changed_percent\":\" 1.07\",\"high\":\"1566.92\",\"low\":\"1550.23\",\"volume\":\"19368\",\"value\":\"139519500\"},{\"date\":\"20150327\",\"opening\":\"1475.09\",\"closing\":\"1566.92\",\"changed\":\" 91.83\",\"changed_percent\":\" 6.23\",\"high\":\"1569.95\",\"low\":\"1475.09\",\"volume\":\"15090\",\"value\":\"68112000\"},{\"date\":\"20150326\",\"opening\":\"1475.09\",\"closing\":\"1475.09\",\"changed\":\" 0.00\",\"changed_percent\":\" 0.00\",\"high\":\"1475.09\",\"low\":\"1475.09\",\"volume\":\"4455\",\"value\":\"33412500\"},{\"date\":\"20150325\",\"opening\":\"1459.28\",\"closing\":\"1475.09\",\"changed\":\" 23.71\",\"changed_percent\":\" 1.63\",\"high\":\"1475.09\",\"low\":\"1459.28\",\"volume\":\"54315\",\"value\":\"391441400\"},{\"date\":\"20150324\",\"opening\":\"1451.38\",\"closing\":\"1451.38\",\"changed\":\" 7.90\",\"changed_percent\":\" 0.55\",\"high\":\"1451.38\",\"low\":\"1451.38\",\"volume\":\"438486\",\"value\":\"3214680900\"},{\"date\":\"20150323\",\"opening\":\"1443.48\",\"closing\":\"1443.48\",\"changed\":\" 0.00\",\"changed_percent\":\" 0.00\",\"high\":\"1451.38\",\"low\":\"1443.48\",\"volume\":\"79594\",\"value\":\"544263000\"},{\"date\":\"20150320\",\"opening\":\"1445.48\",\"closing\":\"1443.48\",\"changed\":\" 2.00\",\"changed_percent\":\" 0.14\",\"high\":\"1445.48\",\"low\":\"1441.96\",\"volume\":\"2614\",\"value\":\"18263900\"}]";
=======
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
>>>>>>> origin/master
            Log.d(LOG_TAG, "JsonString=" + jsonIndex);
            JSONArray dailyIndex = new JSONArray(jsonIndex);

            if(dailyIndex.length() > 0) {
                ContentValues[] values = new ContentValues[dailyIndex.length()];
                for (int i = 0; i < dailyIndex.length(); i++) {
                    JSONObject o = dailyIndex.getJSONObject(i);
<<<<<<< HEAD
=======

>>>>>>> origin/master
                    ContentValues value = new ContentValues();
                    value.put(Index.COL_CHANGED, o.getLong(Index.COL_CHANGED));
                    value.put(Index.COL_CHANGED_PERCENT, o.getLong(Index.COL_CHANGED_PERCENT));
                    value.put(Index.COL_CLOSING, o.getLong(Index.COL_CLOSING));
<<<<<<< HEAD
                    value.put(Index.COL_CODE, code);
                    value.put(Index.COL_DATE, o.getString(Index.COL_DATE));
                    Log.d(LOG_TAG, value.getAsString(Index.COL_DATE));
=======
                    value.put(Index.COL_CODE, o.getString(Index.COL_CODE));
                    value.put(Index.COL_DATE, o.getLong(Index.COL_DATE));
>>>>>>> origin/master
                    value.put(Index.COL_HIGH, o.getLong(Index.COL_HIGH));
                    value.put(Index.COL_LOW, o.getLong(Index.COL_LOW));
                    value.put(Index.COL_NAME, o.getString(Index.COL_NAME));
                    value.put(Index.COL_OPENING, o.getLong(Index.COL_OPENING));
                    value.put(Index.COL_PREVIOUS_DAY, o.getLong(Index.COL_PREVIOUS_DAY));
                    value.put(Index.COL_VALUE, o.getLong(Index.COL_VALUE));
                    value.put(Index.COL_VOLUME, o.getLong(Index.COL_VOLUME));

                    values[i] = value;
                }
<<<<<<< HEAD
=======

>>>>>>> origin/master
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
