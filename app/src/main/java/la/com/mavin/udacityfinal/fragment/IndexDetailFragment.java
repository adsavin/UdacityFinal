package la.com.mavin.udacityfinal.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import la.com.mavin.udacityfinal.R;

/**
 * Created by Adsavin on 4/7/2015.
 */
public class IndexDetailFragment extends Fragment {
    public IndexDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_index_detail, container, false);
        return rootView;
    }
}
