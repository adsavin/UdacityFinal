package la.com.mavin.udacityfinal.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import la.com.mavin.udacityfinal.R;
import la.com.mavin.udacityfinal.model.IndexCode;


/**
 * Created by adsavin on 30/03/15.
 */
public class IndexListAdapter extends CursorAdapter {

    public IndexListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_index, viewGroup, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        IndexCode indexCode = IndexCode.toObject(cursor);
        ((TextView) view.findViewById(R.id.item_index)).setText(indexCode.getCode() + " - " + indexCode.getName());
    }
}
