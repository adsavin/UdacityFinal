package la.com.mavin.udacityfinal;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import la.com.mavin.udacityfinal.fragment.DatePickerFragment;
import la.com.mavin.udacityfinal.fragment.StockFragment;
import la.com.mavin.udacityfinal.fragment.StockFragment;


public class StockDailyActivity extends ActionBarActivity implements StockFragment.Callback {

    private String LOG_TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_daily);
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable("URI", getIntent().getData());

            StockFragment fragment = new StockFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.stock_daily_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stock_daily, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(Uri uri) {
        Log.d(LOG_TAG, "itemurl=" + uri.toString());
        Intent intent = new Intent(getApplicationContext(), StockDetailActivity.class);
        intent.setData(uri);
        startActivity(intent);
    }

    public void showStartDatePickerDialog(View v) {
        DatePickerFragment f = new DatePickerFragment();
//        f.setTxt((TextView) findViewById(R.id.txt_startdate));
        f.show(getSupportFragmentManager(), "dtStartDate");
    }

    public void showEndDatePickerDialog(View v) {
        DatePickerFragment f = new DatePickerFragment();
//        f.setTxt((TextView) findViewById(R.id.txt_enddate));
        f.show(getSupportFragmentManager(), "dtEndDate");
    }
}
