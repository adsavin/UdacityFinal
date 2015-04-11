package la.com.mavin.udacityfinal.provider;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

/**
 * Created by Adsavin on 4/9/2015.
 */
public class MyContentObserver extends ContentObserver {
    private final String LOG_TAG = getClass().getSimpleName();

    public MyContentObserver(Handler handler) {
        super(handler);
    }

    @Override
    public boolean deliverSelfNotifications() {
        return super.deliverSelfNotifications();
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        Log.d(LOG_TAG, "Onchange: " + uri.toString());
    }
}
