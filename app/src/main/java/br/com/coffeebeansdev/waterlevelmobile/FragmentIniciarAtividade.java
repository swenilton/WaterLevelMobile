package br.com.coffeebeansdev.waterlevelmobile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
        context = getContext();
        fragmentManager = getFragmentManager();
        try{
            popularLista();
        } catch (Exception e){
            Log.i("ERRO", e.getMessage());
        }
        return rootView;
    }

    public static void popularLista() throws Exception {
        fachada = Fachada.getInstance(context);
        atividadeRealizadaListAdapter = new AtividadeRealizadaListAdapter(context, fragmentManager, fachada.atividadeListar());
        listView.setAdapter(atividadeRealizadaListAdapter);
    }
}
