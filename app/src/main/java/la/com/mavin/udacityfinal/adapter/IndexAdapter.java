package la.com.mavin.udacityfinal.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import la.com.mavin.udacityfinal.R;
import la.com.mavin.udacityfinal.model.Index;

/**
 * Created by adsavin on 30/03/15.
 */
public class IndexAdapter extends CursorAdapter {

    private final int VIEW_COUNT = 2;
    private final int INDEX_CURRENT = 0;
    private final int INDEX_HISTORY = 1;

    public IndexAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    public static class ViewHolder {
//        public final ImageView iconView;
//        public final TextView dateView;
//        public final TextView descriptionView;
//        public final TextView highTempView;
//        public final TextView lowTempView;

        public ViewHolder(View view) {
//            iconView = (ImageView) view.findViewById(R.id.list_item_icon);
//            dateView = (TextView) view.findViewById(R.id.list_item_date_textview);
//            descriptionView = (TextView) view.findViewById(R.id.list_item_forecast_textview);
//            highTempView = (TextView) view.findViewById(R.id.list_item_high_textview);
//            lowTempView = (TextView) view.findViewById(R.id.list_item_low_textview);
        }
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        int position = cursor.getPosition();
        int layoutid = -1;
        switch (position) {
            case INDEX_CURRENT:
                layoutid = R.layout.list_index_current;
                break;
            case INDEX_HISTORY:
                layoutid = R.layout.list_stock_history;
                break;
            default:
                break;
        }

        View view = LayoutInflater.from(context).inflate(layoutid, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
<<<<<<< HEAD
        Index model = Index.toObject(cursor);
        ((TextView) view.findViewById(R.id.index_date)).setText(model.getDate());
=======

>>>>>>> origin/master
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) return INDEX_CURRENT;
        else return INDEX_HISTORY;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_COUNT;
    }
}
