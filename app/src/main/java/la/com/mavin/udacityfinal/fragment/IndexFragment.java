package la.com.mavin.udacityfinal.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import la.com.mavin.udacityfinal.R;
import la.com.mavin.udacityfinal.adapter.IndexAdapter;
import la.com.mavin.udacityfinal.helper.MyHelper;
import la.com.mavin.udacityfinal.model.Index;
import la.com.mavin.udacityfinal.provider.MyContentObserver;
import la.com.mavin.udacityfinal.provider.StocxProvider;
import la.com.mavin.udacityfinal.task.IndexTask;

/**
 * Created by Adsavin on 3/30/2015.
 */
public class IndexFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {
    private final String LOG_TAG = getClass().getSimpleName();
    private IndexAdapter indexAdapter;
    private ListView listview_index;
    private Button btnInq;
    private int position = ListView.INVALID_POSITION;
    private String SELECTED_KEY = "selected_index_position";
    private Uri selectedUri;
    private EditText txtStartDate;
    private EditText txtEndDate;

    private final int LOADER = 0;
    private final String[] COLUMNS = {
            Index.TABLE_NAME + "." + Index._ID,
            Index.COL_CHANGED,
            Index.COL_CLOSING,
            Index.COL_CODE,
            Index.COL_DATE,
            Index.COL_HIGH,
            Index.COL_LOW,
            Index.COL_NAME,
            Index.COL_OPENING,
            Index.COL_VALUE,
            Index.COL_VOLUME,
            Index.COL_CHANGED_PERCENT,
            Index.COL_PREVIOUS_DAY
    };

    public IndexFragment() {
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
        new IndexTask(getActivity()).execute(StocxProvider.getIndexCodeFromUri(selectedUri));
        View rootView = inflater.inflate(R.layout.fragment_index_daily, container, false);
//        this.txtStartDate = (EditText) rootView.findViewById(R.id.txt_startdate);
//        this.txtEndDate = (EditText) rootView.findViewById(R.id.txt_enddate);
        this.listview_index = (ListView) rootView.findViewById(R.id.list_daily_index);
        this.indexAdapter = new IndexAdapter(getActivity(), null, 0);
        this.listview_index.setAdapter(this.indexAdapter);
//        this.listview_index.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Cursor cursor = (Cursor) adapterView.getItemAtPosition(i);
//                if(cursor != null) {
//                    ((Callback) getActivity()).onItemSelected(Index.getIndexUri(cursor.getString(Index.CODE)));
//                }
//                position = i;
//            }
//        });
//        this.btnInq = (Button) rootView.findViewById(R.id.btn_index_inq);
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
                Index.COL_CODE
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        this.indexAdapter.swapCursor(data);
        if(this.position != ListView.INVALID_POSITION) {
            this.listview_index.smoothScrollToPosition(this.position);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        this.indexAdapter.swapCursor(null);
    }

    @Override
    public void onClick(View v) {
        String code = StocxProvider.getIndexCodeFromUri(selectedUri);
        String dateStart = txtStartDate.getText().toString().trim().length() > 0 ? MyHelper.formatDateToDb(txtStartDate.getText().toString()) : null;
        String dateEnd = txtEndDate.getText().toString().trim().length() > 0? MyHelper.formatDateToDb(txtEndDate.getText().toString()) : null;
        if(dateStart != null && dateEnd != null) {
            this.indexAdapter = new IndexAdapter(getActivity(), null, 0, Index.getIndexUri(code, dateStart, dateEnd));
            this.listview_index.setAdapter(this.indexAdapter);
            Log.d(LOG_TAG, Index.getIndexUri(code, dateStart, dateEnd).toString());
            new IndexTask(getActivity()).execute(code, dateStart, dateEnd);
        } else if(dateStart != null && dateEnd == null) {
            this.indexAdapter = new IndexAdapter(getActivity(), null, 0, Index.getIndexUri(code, dateStart));
            this.listview_index.setAdapter(this.indexAdapter);
            Log.d(LOG_TAG, Index.getIndexUri(code, dateStart).toString());
            new IndexTask(getActivity()).execute(code, dateStart);
        } else {
            this.indexAdapter = new IndexAdapter(getActivity(), null, 0, Index.getIndexUri(code));
            this.listview_index.setAdapter(this.indexAdapter);
            Log.d(LOG_TAG, Index.getIndexUri(code).toString());
            new IndexTask(getActivity()).execute(code);
        }
    }
}
