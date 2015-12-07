package br.com.coffeebeansdev.waterlevelmobile;

/**
 * Created by swenilton on 23/10/15.
 */
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.coffeebeans.atividade.Atividade;
import br.com.coffeebeans.fachada.Fachada;

public class RankFragmentPageAdapter extends PagerAdapter {

    List<Atividade> atividades;
    View rootview;
    ListView listView;
    Fachada fachada;
    Context context;

    /** Constructor of the class */
    public RankFragmentPageAdapter(List<Atividade> atividades) {
        this.atividades = atividades;
    }

    /** This method will be invoked when a page is requested to create */
//    @Override
//    public Fragment getItem(int arg0) {
////        FragmentListRank myFragment = new FragmentListRank();
////        Bundle data = new Bundle();
////        data.putInt("current_page", arg0+1);
////        myFragment.setArguments(data);
//        return new FragmentListRank();
//    }

    /** Returns the number of pages */
    @Override
    public int getCount() {
        return atividades.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return atividades.get(position).getDescricao();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(container.getContext().LAYOUT_INFLATER_SERVICE);
        rootview = inflater.inflate(R.layout.fragment_list_rank, null);
        context = container.getContext();

        listView = (ListView) rootview.findViewById(R.id.listView);
        try {
            RankListAdapter rankListAdapter = new RankListAdapter(container.getContext(), Fachada.getInstance(container.getContext()).getUsuarioLista());
            listView.setAdapter(rankListAdapter);
        }catch (Exception e){

        }

        ((ViewPager) container).addView(rootview, 0);
        return rootview;
    }
}
