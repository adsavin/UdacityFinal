package la.com.mavin.udacityfinal.cursor;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * Created by Adsavin on 3/30/2015.
 */
@TargetApi(11)
public class StockListCursor extends CursorAdapter {

    public StockListCursor(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
