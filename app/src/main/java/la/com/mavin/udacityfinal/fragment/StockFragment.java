package la.com.mavin.udacityfinal.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import la.com.mavin.udacityfinal.R;

/**
 * Created by Adsavin on 3/30/2015.
 */
public class StockFragment extends Fragment {

    public StockFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stock, container, false);
        return rootView;
    }

}
