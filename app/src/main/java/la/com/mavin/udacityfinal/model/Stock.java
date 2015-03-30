package la.com.mavin.udacityfinal.model;

import android.provider.BaseColumns;

/**
 * Created by Adsavin on 3/30/2015.
 */
public class Stock extends BaseModel implements BaseColumns {
    public static final String TABLE_NAME = "lsx_stock";
    public static final String COL_MARKET_CAP = "market_cap";
    public static final String COL_LISTED_SHARES = "listed_shares";

    private long marketCap;
    private long listedShares;

    public long getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(long marketCap) {
        this.marketCap = marketCap;
    }

    public long getListedShares() {
        return listedShares;
    }

    public void setListedShares(long listedShares) {
        this.listedShares = listedShares;
    }
}
