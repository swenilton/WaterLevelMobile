package br.com.coffeebeansdev.waterlevelmobile;

/**
 * Created by swenilton on 23/10/15.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import br.com.coffeebeans.atividade.Atividade;

public class RankFragmentPageAdapter extends FragmentPagerAdapter{

    List<Atividade> atividades;

    /** Constructor of the class */
    public RankFragmentPageAdapter(FragmentManager fm, List<Atividade> atividades) {
        super(fm);
        this.atividades = atividades;
    }

    /** This method will be invoked when a page is requested to create */
    @Override
    public Fragment getItem(int arg0) {
        //FragmentRank myFragment = new FragmentRank();
//        Bundle data = new Bundle();
//        data.putInt("current_page", arg0+1);
//        myFragment.setArguments(data);
        return new FragmentListRank();
    }

    /** Returns the number of pages */
    @Override
    public int getCount() {
        return atividades.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return atividades.get(position).getDescricao();
    }


}
