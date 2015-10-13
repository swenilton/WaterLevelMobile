package br.com.coffeebeansdev.waterlevelmobile;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import br.com.coffeebeans.fachada.Fachada;


public class FragmentUsuario extends Fragment {
    private View rootView;
    private static ListView listView;
    private static Fachada fachada;
    private static UsuarioListAdapter usuarioListAdapter;
    private static Context context;
    private static FragmentManager fragmentManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_usuario, container, false);
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
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
                FragmentManager fm = getFragmentManager();
                DialogInserirUsuario editNameDialog = DialogInserirUsuario.newInstance("Inserir Usuario", 0);
                editNameDialog.show(fm, "fragment_inserir_usuario");
            }
        });
        return rootView;
    }

    public static void popularLista() throws Exception {
        fachada = Fachada.getInstance(context);
        usuarioListAdapter = new UsuarioListAdapter(context, fragmentManager, fachada.getUsuarioLista());
        listView.setAdapter(usuarioListAdapter);
    }
}
