package la.com.mavin.udacityfinal.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import la.com.mavin.udacityfinal.R;
import la.com.mavin.udacityfinal.model.Index;

/**
 * Created by adsavin on 30/03/15.
 */
public class IndexAdapter extends CursorAdapter {
    private final String LOG_TAG = getClass().getSimpleName();
    private final int VIEW_COUNT = 2;
    private final int INDEX_CURRENT = 0;
    private final int INDEX_HISTORY = 1;


    public IndexAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    public static class ViewHolder {
        public final ImageView imgUp;
        public final ImageView imgDown;
        public final TextView txtDate;
        public final TextView txtVolume;
        public final TextView txtValue;
        public final TextView txtHigh;
        public final TextView txtLow;

        public ViewHolder(View view) {
            imgUp = (ImageView) view.findViewById(R.id.img_high);
            imgDown = (ImageView) view.findViewById(R.id.img_low);
            txtDate = (TextView) view.findViewById(R.id.index_date);
            txtVolume = (TextView) view.findViewById(R.id.volume);
            txtValue = (TextView) view.findViewById(R.id.value);
            txtHigh = (TextView) view.findViewById(R.id.index_high);
            txtLow = (TextView) view.findViewById(R.id.index_low);
        }
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
//        int position = cursor.getPosition();
//        int layoutid = -1;
//        switch (position) {
//            case INDEX_CURRENT:
//                layoutid = R.layout.list_index_current;
//                break;
//            case INDEX_HISTORY:
//                layoutid = R.layout.list_stock_history;
//                break;
//            default:
//                break;
//        }

//        View view = LayoutInflater.from(context).inflate(layoutid, viewGroup, false);
//        ViewHolder viewHolder = new ViewHolder(view);
//        view.setTag(viewHolder);

//        return view;
        View view = LayoutInflater.from(context).inflate(R.layout.list_daily_index, viewGroup, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        Index model = Index.toObject(cursor);
//        viewHolder.txtDate.setText(Long.toString(model.getClosing()));
//        viewHolder.txtVolume.setText(Long.toString(model.getVolume()));
//        viewHolder.txtValue.setText(Long.toString(model.getValue()));
//        viewHolder.txtHigh.setText(Long.toString(model.getHigh()));
//        viewHolder.txtLow.setText(Long.toString(model.getLow()));
        viewHolder.imgUp.setImageResource(R.drawable.up);
        viewHolder.imgDown.setImageResource(R.drawable.down);
        if(model.getChanged() > 1) {
            viewHolder.imgDown.setVisibility(View.INVISIBLE);
            viewHolder.imgUp.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgDown.setVisibility(View.VISIBLE);
            viewHolder.imgUp.setVisibility(View.INVISIBLE);
        }
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
