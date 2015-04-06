package la.com.mavin.udacityfinal.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import la.com.mavin.udacityfinal.R;
import la.com.mavin.udacityfinal.adapter.IndexAdapter;
import la.com.mavin.udacityfinal.model.Index;
<<<<<<< HEAD
<<<<<<< HEAD
import la.com.mavin.udacityfinal.provider.IndexListProvider;
=======
import la.com.mavin.udacityfinal.model.IndexCode;
import la.com.mavin.udacityfinal.provider.IndexProvider;
>>>>>>> origin/master
=======
import la.com.mavin.udacityfinal.provider.IndexProvider;
>>>>>>> parent of 45d23b2... ad night
import la.com.mavin.udacityfinal.task.IndexTask;

/**
 * Created by Adsavin on 3/30/2015.
 */
public class IndexFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private IndexAdapter indexAdapter;
    private ListView listview_index;
    private int position = ListView.INVALID_POSITION;
    private String SELECTED_KEY = "selected_index_position";
    private Uri selectedUri;

    private final int LOADER = 0;
    private final String[] COLUMNS = {
            Index.TABLE_NAME + "." + Index._ID,
            Index.COL_CHANGED,
            Index.COL_CHANGED_PERCENT,
            Index.COL_CLOSING,
            Index.COL_CODE,
            Index.COL_DATE,
            Index.COL_HIGH,
            Index.COL_LOW,
            Index.COL_NAME,
            Index.COL_OPENING,
            Index.COL_PREVIOUS_DAY,
            Index.COL_VALUE,
            Index.COL_VOLUME
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
<<<<<<< HEAD
<<<<<<< HEAD
        new IndexTask(getActivity()).execute(IndexListProvider.getIndexCodeFromUri(selectedUri));

        View rootView = inflater.inflate(R.layout.fragment_index_daily, container, false);
        this.listview_index = (ListView) rootView.findViewById(R.id.list_daily_index);
=======
        new IndexTask(getActivity()).execute(IndexProvider.getIndexCodeFromUri(selectedUri));

        View rootView = inflater.inflate(R.layout.fragment_index_list, container, false);
        this.listview_index = (ListView) rootView.findViewById(R.id.listview_index);
>>>>>>> origin/master
=======
        Log.d(LOG_TAG, "Selected Uti: " + selectedUri.toString());
        Log.d(LOG_TAG, "Code:"+IndexProvider.getIndexCodeFromUri(selectedUri));
        new IndexTask(getActivity()).execute(IndexProvider.getIndexCodeFromUri(selectedUri));

        View rootView = inflater.inflate(R.layout.fragment_index_daily, container, false);
        this.listview_index = (ListView) rootView.findViewById(R.id.listview_index);
>>>>>>> parent of 45d23b2... ad night
        this.indexAdapter = new IndexAdapter(getActivity(), null, 0);
        this.listview_index.setAdapter(this.indexAdapter);
        this.listview_index.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(i);
                if(cursor != null) {
                    ((Callback) getActivity()).onItemSelected(Index.getIndexUri(cursor.getString(Index.CODE)));
                }
                position = i;
            }
        });

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
<<<<<<< HEAD
        if(args != null) {
            selectedUri = args.getParcelable("URI");
        }
        Log.d(LOG_TAG, "----Selected: " + selectedUri.toString());
        Log.d(LOG_TAG, "----Code:" + IndexProvider.getIndexCodeFromUri(selectedUri));

        return new CursorLoader(
                getActivity(),
<<<<<<< HEAD
                selectedUri,
=======
        return new CursorLoader(
                getActivity(),
                IndexCode.getIndexListUri(),
>>>>>>> origin/master
=======
                Index.getIndexUri("001"),
>>>>>>> parent of 45d23b2... ad night
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
}
