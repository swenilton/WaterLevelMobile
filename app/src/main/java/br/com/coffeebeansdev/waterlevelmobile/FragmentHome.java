package br.com.coffeebeansdev.waterlevelmobile;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentHome.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {
    View rootView;
    private TabHost myTabHost;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        myTabHost =(TabHost) rootView.findViewById(R.id.TabHost01);
// Before adding tabs, it is imperative to call the method setup()
        myTabHost.setup();

// Adding tabs
        // tab1 settings
        TabHost.TabSpec spec = myTabHost.newTabSpec("tab_creation");
        // text and image of tab
        spec.setIndicator("Repositorio 1",getResources().getDrawable(android.R.drawable.ic_menu_add));
        // specify layout of tab
        spec.setContent(R.id.onglet1);
        // adding tab in TabHost
        myTabHost.addTab(spec);

// otherwise :
       // myTabHost.addTab(myTabHost.newTabSpec("tab_inser").setIndicator("Delete",getResources().getDrawable(android.R.drawable.ic_menu_edit)).setContent(R.id.Onglet2));

       // myTabHost.addTab(myTabHost.newTabSpec("tab_affiche").setIndicator("Show All",getResources().getDrawable(android.R.drawable.ic_menu_view)).setContent(R.id.Onglet3));

        return rootView;
    }
}
