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

import org.w3c.dom.Text;

import la.com.mavin.udacityfinal.R;
import la.com.mavin.udacityfinal.helper.MyHelper;
import la.com.mavin.udacityfinal.model.Index;

/**
 * Created by adsavin on 30/03/15.
 */
public class IndexAdapter extends CursorAdapter {
    private final String LOG_TAG = getClass().getSimpleName();
    private final int VIEW_COUNT = 2;
    private final int INDEX_CURRENT = 0;
    private final int INDEX_HISTORY = 1;

    public ImageView imgUp;
    public ImageView imgDown;
    public TextView txtDate;
    public TextView txtHigh;
    public TextView txtLow;
    public TextView txtClosed;
    public TextView txtChanged;

    public IndexAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
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
        imgUp = (ImageView) view.findViewById(R.id.img_high);
        imgDown = (ImageView) view.findViewById(R.id.img_low);
        txtDate = (TextView) view.findViewById(R.id.index_date);
        txtHigh = (TextView) view.findViewById(R.id.index_high);
        txtLow = (TextView) view.findViewById(R.id.index_low);
        txtClosed = (TextView) view.findViewById(R.id.index_closing);
        txtChanged = (TextView) view.findViewById(R.id.change);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Index model = Index.toObject(cursor);
        txtDate.setText(MyHelper.formatDateToShow(Long.toString(model.getDate()), "/"));
        txtHigh.setText("H: " + model.getHigh());
        txtLow.setText("L: " + model.getLow());
        txtChanged.setText( model.getChanged());
        txtClosed.setText("Closed: " + model.getClosing());
        if (Float.parseFloat(model.getChanged()) > 10) {
            imgDown.setVisibility(View.INVISIBLE);
            imgUp.setVisibility(View.VISIBLE);
        } else if (Float.parseFloat(model.getChanged()) == 0) {
            imgDown.setVisibility(View.INVISIBLE);
            imgUp.setVisibility(View.INVISIBLE);
        } else {
            imgDown.setVisibility(View.VISIBLE);
            imgUp.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return INDEX_CURRENT;
        else return INDEX_HISTORY;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_COUNT;
    }
}
