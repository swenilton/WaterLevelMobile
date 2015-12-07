package br.com.coffeebeansdev.waterlevelmobile;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import br.com.coffeebeans.atividade.Atividade;
import br.com.coffeebeans.fachada.Fachada;


public class FragmentRank extends Fragment {
    View rootView;
    List<Atividade> atividades;
    Fachada fachada;
    ViewPager pager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_rank, container, false);
        /** Getting a reference to the ViewPager defined the layout file */
        pager = (ViewPager) rootView.findViewById(R.id.pager);

        new TaskAtividade().execute();

        /** Getting fragment manager */
        //FragmentManager fm = getChildFragmentManager();

        /** Instantiating FragmentPagerAdapter */

        return rootView;
    }

    private class TaskAtividade extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            if (isAdded())
                //showProgress(true);
                try {
                    fachada = Fachada.getInstance(getActivity());
                } catch (Exception e) {
                    Log.i("Erro Fachada", "Erro ao instancia fachada " + e.getMessage());
                    Toast.makeText(getActivity(), "Erro ao instancia fachada\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                atividades = fachada.atividadeListar();
            } catch (Exception e) {
                Log.i("Erro listarAtividade", "Erro ao listar atividades " + e.getMessage());
                //Toast.makeText(context, "Erro ao listar atividades\n" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            RankFragmentPageAdapter pagerAdapter = new RankFragmentPageAdapter(atividades);
            /** Setting the pagerAdapter to the pager object */
            pager.setAdapter(pagerAdapter);
            pager.setCurrentItem(0);
        }
    }
}
