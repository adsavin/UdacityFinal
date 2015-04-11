package la.com.mavin.udacityfinal.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import la.com.mavin.udacityfinal.R;
import la.com.mavin.udacityfinal.model.StockCode;

/**
 * Created by Adsavin on 4/11/2015.
 */
public class StockListAdapter extends CursorAdapter {

    public StockListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_stock, viewGroup, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        StockCode stockCode = StockCode.toObject(cursor);
        ((TextView) view.findViewById(R.id.item_stock)).setText(stockCode.getCode() + " - " + stockCode.getName());
    }
}
