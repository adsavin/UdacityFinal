package la.com.mavin.udacityfinal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import la.com.mavin.udacityfinal.fragment.ListStockFragment;
import la.com.mavin.udacityfinal.fragment.StockFragment;


public class StockListActivity extends ActionBarActivity implements ListStockFragment.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        if (savedInstanceState == null) {
            ListStockFragment stockFragment = new ListStockFragment();
            Bundle bundle = new Bundle();
//            bundle.putParcelable();

            stockFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, stockFragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stock, menu);
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
        Intent intent = new Intent(getApplicationContext(), StockDailyActivity.class);
        intent.setData(uri);
        startActivity(intent);
    }

}
