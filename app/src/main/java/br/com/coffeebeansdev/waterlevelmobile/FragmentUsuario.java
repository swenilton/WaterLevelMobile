package br.com.coffeebeansdev.waterlevelmobile;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ScrollView;

import br.com.coffeebeans.fachada.Fachada;


public class FragmentUsuario extends Fragment {
    private View rootView;
    private ScrollView sv;
    private GridView gv;
    private UsuarioListAdapter usuarioListAdapter;
    private Fachada fachada;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_usuario, container, false);

        try {
            fachada = Fachada.getInstance();
            usuarioListAdapter = new UsuarioListAdapter(fachada.getUsuarioLista());
        }catch (Exception e){

        }

        gv = (GridView) rootView.findViewById(R.id.gridView);
        gv.setAdapter(usuarioListAdapter);


        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return rootView;
    }
}
