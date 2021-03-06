package la.com.mavin.udacityfinal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import la.com.mavin.udacityfinal.fragment.DatePickerFragment;
import la.com.mavin.udacityfinal.fragment.IndexFragment;


public class IndexDailyActivity extends ActionBarActivity implements IndexFragment.Callback {
    private String LOG_TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_daily);
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable("URI", getIntent().getData());

            IndexFragment fragment = new IndexFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.index_daily_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_index_daily, menu);
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
        Intent intent = new Intent(getApplicationContext(), IndexDetailActivity.class);
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
