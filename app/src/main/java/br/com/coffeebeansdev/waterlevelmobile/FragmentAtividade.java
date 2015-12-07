package br.com.coffeebeansdev.waterlevelmobile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import br.com.coffeebeans.fachada.Fachada;


public class FragmentAtividade extends Fragment {
    private View rootView;
    private static ListView listView;
    private static Fachada fachada;
    private static AtividadeListAdapter atividadeListAdapter;
    private static Context context;
    private static FragmentManager fragmentManager;
    private ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_atividade, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar3);
        context = getActivity();
        fragmentManager = getFragmentManager();
        try{
            executeTask();
        } catch (Exception e){
            Log.i("ERRO", e.getMessage());
            Toast.makeText(context, "Erro ao popular atividades\n" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                DialogInserirAtividade inserirAtividade = DialogInserirAtividade.newInstance("Inserir Atividade", 0);
                inserirAtividade.show(fm, "fragment_inserir_atividade");
            }
        });
        return rootView;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_longAnimTime);

            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            progressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    private class TaskAtividade extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            if (isAdded())
                showProgress(true);
            try {
                fachada = Fachada.getInstance(context);
            } catch (Exception e) {
                Log.i("Erro Fachada", "Erro ao instancia fachada " + e.getMessage());
                Toast.makeText(context, "Erro ao instancia fachada\n" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                atividadeListAdapter = new AtividadeListAdapter(context, fragmentManager, fachada.atividadeListar());
            } catch (Exception e) {
                Log.i("Erro listarAtividade", "Erro ao listar atividades " + e.getMessage());
                //Toast.makeText(context, "Erro ao listar atividades\n" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            listView.setAdapter(atividadeListAdapter);
            if (isAdded())
                showProgress(false);
        }
    }

    public void executeTask(){
        new TaskAtividade().execute();
    }
}
