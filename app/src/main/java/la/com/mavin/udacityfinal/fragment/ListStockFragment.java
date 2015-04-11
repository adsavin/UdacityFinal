package la.com.mavin.udacityfinal.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import la.com.mavin.udacityfinal.R;
import la.com.mavin.udacityfinal.adapter.StockListAdapter;
import la.com.mavin.udacityfinal.adapter.StockListAdapter;
import la.com.mavin.udacityfinal.model.Stock;
import la.com.mavin.udacityfinal.model.StockCode;
import la.com.mavin.udacityfinal.task.StockListTask;

/**
 * Created by Adsavin on 4/11/2015.
 */
public class ListStockFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private final String LOG_TAG = getClass().getSimpleName();
    private StockListAdapter stockListAdapter;
    private ListView listview_stock;
    private int position = ListView.INVALID_POSITION;
    private String SELECTED_KEY = "selected_stock_position";
    private final int LOADER = 0;

    public ListStockFragment() {
    }

    public interface Callback {
        public void onItemSelected(Uri Uri);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        new StockListTask(getActivity()).execute();
        this.stockListAdapter = new StockListAdapter(getActivity(), null, 0);
        View rootView = inflater.inflate(R.layout.fragment_stock_list, container, false);
        this.listview_stock = (ListView) rootView.findViewById(R.id.listview_stock);
        this.listview_stock.setAdapter(this.stockListAdapter);
        this.listview_stock.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(i);
                if (cursor != null) {
                    ((Callback) getActivity()).onItemSelected(Stock.getStockUri(cursor.getString(StockCode.CODE)));
                }
                position = i;
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            this.position = savedInstanceState.getInt(SELECTED_KEY);
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (this.position != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, this.position);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(LOG_TAG, "oncreateLoader=" + StockCode.getStockListUri());
        return new CursorLoader(
                getActivity(),
                StockCode.getStockListUri(),
                StockCode.COLUMNS,
                null,
                null,
                Stock.COL_CODE
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        this.stockListAdapter.swapCursor(data);

        if (this.position != ListView.INVALID_POSITION) {
            this.listview_stock.smoothScrollToPosition(this.position);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        this.stockListAdapter.swapCursor(null);
    }
}
