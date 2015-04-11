package la.com.mavin.udacityfinal.model;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import la.com.mavin.udacityfinal.database.Contract;

import static la.com.mavin.udacityfinal.model.Stock.LISTED_SHARE;

/**
 * Created by Adsavin on 3/30/2015.
 */
public class Stock extends BaseModel implements BaseColumns {
    public static final String TABLE_NAME = "lsx_stock";
    public static final String COL_MARKET_CAP = "market_cap";
    public static final String COL_LISTED_SHARES = "listed_shares";
    public static final int MARKET_CAP = 11;
    public static final int LISTED_SHARE = 12;
    private String marketCap;
    private String listedShares;

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public void setListedShares(String listedShares) {
        this.listedShares = listedShares;
    }

    public static final String PATH = "stock";

    public static final Uri CONTENT_URI = Contract.BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + Contract.CONTENT_AUTHORITY + "/" + PATH;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + Contract.CONTENT_AUTHORITY + "/" + PATH;

    public static final String[] COLUMNS = {
            Stock.TABLE_NAME + "." + StockCode._ID,
            Stock.COL_CHANGED,
            Stock.COL_CLOSING,
            Stock.COL_CODE,
            Stock.COL_DATE,
            Stock.COL_HIGH,
            Stock.COL_LOW,
            Stock.COL_NAME,
            Stock.COL_OPENING,
            Stock.COL_VALUE,
            Stock.COL_VOLUME,
            Stock.COL_MARKET_CAP,
            Stock.COL_LISTED_SHARES
    };

    public static Stock toObject(Cursor cursor) {
        Stock model = new Stock();
        model.setChanged(cursor.getString(CHANGED));
        model.setClosing(cursor.getString(CLOSING));
        model.setCode(cursor.getString(CODE));
        model.setDate(cursor.getLong(DATE));
        model.setHigh(cursor.getString(HIGH));
        if (model.getHigh().equals("0")) {
            model.setHigh(model.getClosing());
        }
        model.setLow(cursor.getString(LOW));
        if (model.getLow().equals("0")) {
            model.setLow(model.getClosing());
        }
        model.setName(cursor.getString(NAME));
        model.setOpening(cursor.getString(OPENING));
        model.setValue(cursor.getString(VALUE));
        model.setVolume(cursor.getString(VOLUME));
        model.setListedShares(cursor.getString(LISTED_SHARE));
        model.setMarketCap(cursor.getString(MARKET_CAP));
        return model;
    }

    public static Uri getStockUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    public static Uri getStockUri() {
        return CONTENT_URI;
    }

    public static Uri getStockUri(String code) {
        return CONTENT_URI.buildUpon()
                .appendPath(code)
                .build();
    }

    public static Uri getStockUri(String code, String startDate) {
        return CONTENT_URI.buildUpon()
                .appendQueryParameter("code", code)
                .appendQueryParameter("startDate", startDate)
                .build();
    }

    public static Uri getStockUri(String code, String startDate, String endDate) {
        return CONTENT_URI.buildUpon()
                .appendQueryParameter("code", code)
                .appendQueryParameter("startDate", startDate)
                .appendQueryParameter("endDate", endDate)
                .build();
    }
}
