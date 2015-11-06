package br.com.coffeebeansdev.waterlevelmobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.coffeebeans.atividade.Atividade;
import br.com.coffeebeans.fachada.Fachada;


public class FragmentRank extends Fragment {
    View rootView;
    List<Atividade> atividades;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_rank, container, false);
        /** Getting a reference to the ViewPager defined the layout file */
        ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);

        try{
            atividades = Fachada.getInstance(getContext()).atividadeListar();
        } catch (Exception e){

        }

        /** Getting fragment manager */
        FragmentManager fm = getChildFragmentManager();

        /** Instantiating FragmentPagerAdapter */
        RankFragmentPageAdapter pagerAdapter = new RankFragmentPageAdapter(fm, atividades);

        /** Setting the pagerAdapter to the pager object */
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(0);
        return rootView;
    }
}
