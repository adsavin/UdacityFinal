package la.com.mavin.udacityfinal.database;

import android.net.Uri;

/**
 * Created by Adsavin on 3/30/2015.
 */
public class Contract {
    public static final String CONTENT_AUTHORITY = "la.com.mavin.udacityfinal";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
}
