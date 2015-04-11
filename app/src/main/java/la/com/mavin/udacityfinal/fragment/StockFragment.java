package la.com.mavin.udacityfinal.fragment;

import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import la.com.mavin.udacityfinal.R;
import la.com.mavin.udacityfinal.adapter.StockAdapter;
import la.com.mavin.udacityfinal.helper.MyHelper;
import la.com.mavin.udacityfinal.model.Stock;
import la.com.mavin.udacityfinal.provider.StocxProvider;
import la.com.mavin.udacityfinal.task.StockTask;

/**
 * Created by Adsavin on 3/30/2015.
 */
public class StockFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {
    private final String LOG_TAG = getClass().getSimpleName();
    private StockAdapter stockAdapter;
    private ListView listview_stock;
    private Button btnInq;
    private int position = ListView.INVALID_POSITION;
    private String SELECTED_KEY = "selected_stock_position";
    private Uri selectedUri;
    private EditText txtStartDate;
    private EditText txtEndDate;

    private final int LOADER = 0;
    private final String[] COLUMNS = {
            Stock.TABLE_NAME + "." + Stock._ID,
            Stock.COL_CHANGED,
            Stock.COL_CLOSING,
            Stock.COL_CODE,
            Stock.COL_DATE,
            Stock.COL_HIGH,
            Stock.COL_LOW,
            Stock.COL_NAME,
            Stock.COL_OPENING,
            Stock.COL_VALUE,
            Stock.COL_VOLUME,
            Stock.COL_MARKET_CAP,
            Stock.COL_LISTED_SHARES
    };

    public StockFragment() {
    }

    public interface Callback {
        public void onItemSelected(Uri Uri);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle != null) {
            selectedUri = bundle.getParcelable("URI");
        }
        new StockTask(getActivity()).execute(StocxProvider.getStockCodeFromUri(selectedUri));
        View rootView = inflater.inflate(R.layout.fragment_stock_daily, container, false);
//        this.txtStartDate = (EditText) rootView.findViewById(R.id.txt_startdate);
//        this.txtEndDate = (EditText) rootView.findViewById(R.id.txt_enddate);
        this.listview_stock = (ListView) rootView.findViewById(R.id.list_daily_stock);
        this.stockAdapter = new StockAdapter(getActivity(), null, 0);
        this.listview_stock.setAdapter(this.stockAdapter);
//        this.listview_stock.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Cursor cursor = (Cursor) adapterView.getItemAtPosition(i);
//                if(cursor != null) {
//                    ((Callback) getActivity()).onItemSelected(Stock.getStockUri(cursor.getString(Stock.CODE)));
//                }
//                position = i;
//            }
//        });
//        this.btnInq = (Button) rootView.findViewById(R.id.btn_stock_inq);
//        this.btnInq.setOnClickListener(this);

        if(savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
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
        if(this.position != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, this.position);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(args != null) {
            selectedUri = args.getParcelable("URI");
        }

        return new CursorLoader(
                getActivity(),
                selectedUri,
                COLUMNS,
                null,
                null,
                Stock.COL_CODE
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        this.stockAdapter.swapCursor(data);
        if(this.position != ListView.INVALID_POSITION) {
            this.listview_stock.smoothScrollToPosition(this.position);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        this.stockAdapter.swapCursor(null);
    }

    @Override
    public void onClick(View v) {
        String code = StocxProvider.getStockCodeFromUri(selectedUri);
        String dateStart = txtStartDate.getText().toString().trim().length() > 0 ? MyHelper.formatDateToDb(txtStartDate.getText().toString()) : null;
        String dateEnd = txtEndDate.getText().toString().trim().length() > 0? MyHelper.formatDateToDb(txtEndDate.getText().toString()) : null;
        if(dateStart != null && dateEnd != null) {
            this.stockAdapter = new StockAdapter(getActivity(), null, 0, Stock.getStockUri(code, dateStart, dateEnd));
            this.listview_stock.setAdapter(this.stockAdapter);
            new StockTask(getActivity()).execute(code, dateStart, dateEnd);
        } else if(dateStart != null && dateEnd == null) {
            this.stockAdapter = new StockAdapter(getActivity(), null, 0, Stock.getStockUri(code, dateStart));
            this.listview_stock.setAdapter(this.stockAdapter);
            new StockTask(getActivity()).execute(code, dateStart);
        } else {
            this.stockAdapter = new StockAdapter(getActivity(), null, 0, Stock.getStockUri(code));
            this.listview_stock.setAdapter(this.stockAdapter);
            new StockTask(getActivity()).execute(code);
        }
    }
}
