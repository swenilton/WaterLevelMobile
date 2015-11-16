package br.com.coffeebeansdev.waterlevelmobile;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.coffeebeans.fachada.Fachada;


public class FragmentDisp extends Fragment {
    View rootView;
    private static Fachada fachada;
    private static ListView listView;
    private static Context context;
    private static DispositivoListAdapter dispositivoListAdapter;
    private static FragmentManager fragmentManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_disp, container, false);
        fragmentManager = getFragmentManager();
        listView = (ListView) rootView.findViewById(R.id.listView);
        context = getActivity();
        try{
            popularLista();
        } catch (Exception e){
            Log.i("ERRO", e.getMessage());
        }
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInserirDispositivo inserirDispositivo = DialogInserirDispositivo.newInstance("Inserir Dispositivo", 0);
                inserirDispositivo.show(getFragmentManager(), "fragment_inserir_dispositivo");
            }
        });
        return rootView;
    }
    public static void popularLista() throws Exception {
        fachada = Fachada.getInstance(context);
        dispositivoListAdapter = new DispositivoListAdapter(context, fragmentManager, fachada.dispositivoListar());
        listView.setAdapter(dispositivoListAdapter);
    }
}
