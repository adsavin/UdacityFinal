package la.com.mavin.udacityfinal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import la.com.mavin.udacityfinal.fragment.IndexFragment;


public class IndexDailyActivity extends ActionBarActivity implements IndexFragment.Callback {

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
        Intent intent = new Intent(getApplicationContext(), IndexDetailActivity.class);
        intent.setData(uri);
        startActivity(intent);
    }
}
