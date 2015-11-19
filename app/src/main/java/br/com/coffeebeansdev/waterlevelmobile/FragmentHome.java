package br.com.coffeebeansdev.waterlevelmobile;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.coffeebeans.repositorio.Repositorio;
import br.com.coffeebeans.repositorio.RepositorioRetangular;

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
    ViewPager pager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        pager = (ViewPager) rootView.findViewById(R.id.pager);

        /** Getting fragment manager */
        final FragmentManager fm = getChildFragmentManager();

        List<Repositorio> repositorios = new ArrayList<>();
        repositorios.add(new RepositorioRetangular("Repositorio 1", 180.0, 10.0, 100.0, 2.0));
        repositorios.add(new RepositorioRetangular("Repositorio 2", 180.0, 10.0, 100.0, 2.0));

        /** Instantiating FragmentPagerAdapter */
        HomeFragmentPageAdapter pagerAdapter = new HomeFragmentPageAdapter(repositorios);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                FragmentHomeBody fhb = new FragmentHomeBody();

                fm.beginTransaction().replace(R.id.content_main, fhb).commit();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        /** Setting the pagerAdapter to the pager object */
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(0);

        return rootView;
    }
}
