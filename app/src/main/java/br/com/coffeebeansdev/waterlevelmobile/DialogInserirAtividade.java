package br.com.coffeebeansdev.waterlevelmobile;

import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.gcm.Task;

import br.com.coffeebeans.atividade.Atividade;
import br.com.coffeebeans.fachada.Fachada;

/**
 * Created by swenilton on 04/10/15.
 */
public class DialogInserirAtividade extends DialogFragment {

    private Fachada fachada;
    private EditText txtDesc;
    public DialogInserirAtividade() {
        try {
            fachada = Fachada.getInstance(Fachada.context);
        } catch (Exception e){
            Log.i("Erro", e.getMessage());
        }
    }


    public static DialogInserirAtividade newInstance(String title, int id) {
        DialogInserirAtividade frag = new DialogInserirAtividade();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("id", id);
        frag.setArguments(args);
        return frag;
    }


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inserir_atividade, container);
    }


    @Override

    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String title = getArguments().getString("title");
        getDialog().setTitle(title);
        final int id = getArguments().getInt("id");
        txtDesc = (EditText) view.findViewById(R.id.txtDescricao);
        if (!title.equals("Inserir Atividade")){
            try {
                new TaskAtividade("EDITAR").execute();
            } catch (Exception e){
                Log.i("Erro: ", e.getMessage());
            }
        }
        Button btnCancelar = (Button) view.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Button btnSalvar = (Button) view.findViewById(R.id.btnEnviar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    boolean cancela = false;
                    View focusView = null;
                    if(txtDesc.getText().toString().isEmpty()) {
                        txtDesc.setError("Campo obrigatório");
                        cancela = true;
                        focusView = txtDesc;
                    }
                    Atividade a = new Atividade(txtDesc.getText().toString());
                    if (cancela){
                        focusView.requestFocus();
                    } else {
                        if (title.equals("Inserir Atividade")) {
                            new TaskAtividade("CADASTRAR", a).execute();
                        } else {
                            a.setId(id);
                            new TaskAtividade("ATUALIZAR", a).execute();
                        }
                    }
                } catch (Exception e){
                    Log.i("Erro: ", e.getMessage());
                    Toast.makeText(getActivity(), "Erro\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        txtDesc.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private class TaskAtividade extends AsyncTask<Void, Void, String> {

        String cmd;
        Atividade atividade;

        public TaskAtividade(String cmd){
            this.cmd = cmd;
        }

        public TaskAtividade(String cmd, Atividade a){
            this.cmd = cmd;
            this.atividade = a;
        }

        @Override
        protected void onPreExecute() {
            //showProgress(true);
            try {
                //fachada = Fachada.getInstance(getContext());
            } catch (Exception e) {
                Log.i("Erro Fachada", "Erro ao instancia fachada " + e.getMessage());
                Toast.makeText(getActivity(), "Erro ao instancia fachada\n" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                if(cmd.equals("EDITAR")){
                    atividade = fachada.atividadeProcurar(getArguments().getInt("id"));
                } else if(cmd.equals("CADASTRAR")){
                    fachada.cadastrar(atividade);
                } else if(cmd.equals("ATUALIZAR")){
                    fachada.atualizar(atividade);
                }
                return "Atividade salva com sucesso";
            } catch (Exception e) {
                Log.i("Erro", "Erro dialog atividades " + e.getMessage());
//                Toast.makeText(getActivity(), "Erro dialog atividades\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(cmd.equals("EDITAR")){
                txtDesc.setText(atividade.getDescricao());
            } else {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                dismiss();
                new FragmentAtividade().executeTask();
            }
            //showProgress(false);
        }
    }
}