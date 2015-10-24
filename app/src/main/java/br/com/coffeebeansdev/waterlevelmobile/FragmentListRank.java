package br.com.coffeebeansdev.waterlevelmobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import br.com.coffeebeans.atividade.Atividade;
import br.com.coffeebeans.fachada.Fachada;


public class FragmentListRank extends Fragment {
    View rootView;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list_rank, container, false);
        /** Getting a reference to the ViewPager defined the layout file */
        listView = (ListView) rootView.findViewById(R.id.listView);
        try {
            RankListAdapter rankListAdapter = new RankListAdapter(getContext(), getFragmentManager(), Fachada.getInstance(getContext()).getUsuarioLista());
            listView.setAdapter(rankListAdapter);
        }catch (Exception e){

        }

        return rootView;
    }
}
