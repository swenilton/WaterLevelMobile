package br.com.coffeebeansdev.waterlevelmobile;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.SQLException;

import br.com.coffeebeans.exception.DAOException;
import br.com.coffeebeans.fachada.Fachada;


public class FragmentUsuario extends Fragment {
    private View rootView;
    private static ListView listView;
    private static Fachada fachada;
    private static UsuarioListAdapter usuarioListAdapter;
    private static Context context;
    private static FragmentManager fragmentManager;
    private static ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_usuario, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar2);
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
                DialogInserirUsuario editNameDialog = DialogInserirUsuario.newInstance("Inserir Usuario", 0);
                editNameDialog.show(getFragmentManager(), "fragment_inserir_usuario");
            }
        });
        return rootView;
    }

    public static void popularLista() throws Exception {
        new Task().execute();
//        try {
//            fachada = Fachada.getInstance(context);
//            usuarioListAdapter = new UsuarioListAdapter(context, fragmentManager, fachada.getUsuarioLista());
//            listView.setAdapter(usuarioListAdapter);
//        } catch (Exception e) {
//            Log.i("Erro TASK LIST USUARIO", e.getMessage());
//            Toast.makeText(context, "Erro ao carregar lista\n" + e.getMessage(), Toast.LENGTH_LONG).show();
//        }
    }

    private static class Task extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            listView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                fachada = Fachada.getInstance(context);
                usuarioListAdapter = new UsuarioListAdapter(context, fragmentManager, fachada.getUsuarioLista());
                Thread.sleep(1000);
            } catch (Exception e) {
                Log.i("Erro TASK LIST USUARIO", e.getMessage());
                Toast.makeText(context, "Erro ao carregar lista\n" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String retorno) {
            listView.setAdapter(usuarioListAdapter);
            progressBar.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
        }
    }

}
