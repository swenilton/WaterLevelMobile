package br.com.coffeebeansdev.waterlevelmobile;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.coffeebeans.fachada.Fachada;


public class FragmentListRank extends Fragment {
    View rootView;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list_rank, container, false);
        /** Getting a reference to the ViewPager defined the layout file */


        return rootView;
    }
}
