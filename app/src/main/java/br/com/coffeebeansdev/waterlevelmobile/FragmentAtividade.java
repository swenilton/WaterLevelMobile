package br.com.coffeebeansdev.waterlevelmobile;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.coffeebeans.fachada.Fachada;


public class FragmentAtividade extends Fragment {
    private View rootView;
    private static ListView listView;
    private static Fachada fachada;
    private static AtividadeListAdapter atividadeListAdapter;
    private static Context context;
    private static FragmentManager fragmentManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_atividade, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        context = getContext();
        fragmentManager = getFragmentManager();
        try{
            popularLista();
        } catch (Exception e){
            Log.i("ERRO", e.getMessage());
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

    public static void popularLista() throws Exception {
        fachada = Fachada.getInstance(context);
        atividadeListAdapter = new AtividadeListAdapter(context, fragmentManager, fachada.atividadeListar());
        listView.setAdapter(atividadeListAdapter);
    }
}
