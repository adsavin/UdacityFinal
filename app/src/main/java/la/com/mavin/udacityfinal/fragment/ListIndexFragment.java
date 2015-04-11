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
import la.com.mavin.udacityfinal.adapter.IndexListAdapter;
import la.com.mavin.udacityfinal.model.Index;
import la.com.mavin.udacityfinal.model.IndexCode;
import la.com.mavin.udacityfinal.task.IndexListTask;

/**
 * Created by adsavin on 30/03/15.
 */
public class ListIndexFragment extends Fragment  implements LoaderManager.LoaderCallbacks<Cursor>  {

    private final String LOG_TAG = getClass().getSimpleName();
    private IndexListAdapter indexListAdapter;
    private ListView listview_index;
    private int position = ListView.INVALID_POSITION;
    private String SELECTED_KEY = "selected_index_position";
    private final int LOADER = 0;

    public ListIndexFragment() {
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
        new IndexListTask(getActivity()).execute();
        this.indexListAdapter = new IndexListAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.fragment_index_list, container, false);
        this.listview_index = (ListView) rootView.findViewById(R.id.listview_index);
        this.listview_index.setAdapter(this.indexListAdapter);
        this.listview_index.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(i);
                if(cursor != null) {
                    ((Callback) getActivity()).onItemSelected(Index.getIndexUri(cursor.getString(IndexCode.CODE)));
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
        return new CursorLoader(
                getActivity(),
                IndexCode.getIndexListUri(),
                IndexCode.COLUMNS,
                null,
                null,
                Index.COL_CODE
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        this.indexListAdapter.swapCursor(data);

        if(this.position != ListView.INVALID_POSITION) {
            this.listview_index.smoothScrollToPosition(this.position);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        this.indexListAdapter.swapCursor(null);
    }
}
