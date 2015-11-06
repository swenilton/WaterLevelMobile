package br.com.coffeebeansdev.waterlevelmobile;

/**
 * Created by swenilton on 23/10/15.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import br.com.coffeebeans.repositorio.Repositorio;

public class HomeFragmentPageAdapter extends FragmentPagerAdapter{

    List<Repositorio> repositorios;

    /** Constructor of the class */
    public HomeFragmentPageAdapter(FragmentManager fm, List<Repositorio> repositorios) {
        super(fm);
        this.repositorios = repositorios;
    }

    /** This method will be invoked when a page is requested to create */
    @Override
    public Fragment getItem(int arg0) {
        //FragmentRank myFragment = new FragmentRank();
//        Bundle data = new Bundle();
//        data.putInt("current_page", arg0+1);
//        myFragment.setArguments(data);
        return new FragmentHomeBody();
    }

    /** Returns the number of pages */
    @Override
    public int getCount() {
        return repositorios.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return repositorios.get(position).getDescricao();
    }


}
