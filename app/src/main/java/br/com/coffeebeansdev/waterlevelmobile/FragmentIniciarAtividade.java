package br.com.coffeebeansdev.waterlevelmobile;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import br.com.coffeebeans.fachada.Fachada;


public class FragmentIniciarAtividade extends Fragment {
    View rootView;
    private static ListView listView;
    private static Fachada fachada;
    private static AtividadeRealizadaListAdapter atividadeRealizadaListAdapter;
    private static Context context;
    private static FragmentManager fragmentManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_iniciar_atividade, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView2);
        context = getActivity();
        fragmentManager = getFragmentManager();
        try{
            popularLista();
        } catch (Exception e){
            Log.i("ERRO", e.getMessage());
        }
        return rootView;
    }

    public void popularLista() throws Exception {
        //fachada = Fachada.getInstance(context);
        new TaskAtividade().execute();
        //atividadeRealizadaListAdapter = new AtividadeRealizadaListAdapter(context, fragmentManager, fachada.atividadeListar());
//        listView.setAdapter(atividadeRealizadaListAdapter);
    }

    private class TaskAtividade extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            if (isAdded())
                //showProgress(true);
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
                atividadeRealizadaListAdapter = new AtividadeRealizadaListAdapter(context, fragmentManager, fachada.atividadeListar());
            } catch (Exception e) {
                Log.i("Erro listarAtividade", "Erro ao listar atividades " + e.getMessage());
                //Toast.makeText(context, "Erro ao listar atividades\n" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            listView.setAdapter(atividadeRealizadaListAdapter);
            //if (isAdded())
                //showProgress(false);
        }
    }
}
