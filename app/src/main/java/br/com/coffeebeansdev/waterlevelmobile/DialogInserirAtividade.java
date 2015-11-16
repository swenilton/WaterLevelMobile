package br.com.coffeebeansdev.waterlevelmobile;

import android.app.DialogFragment;
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

import br.com.coffeebeans.atividade.Atividade;
import br.com.coffeebeans.fachada.Fachada;

/**
 * Created by swenilton on 04/10/15.
 */
public class DialogInserirAtividade extends DialogFragment {

    private Fachada fachada;
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
        final EditText txtDesc = (EditText) view.findViewById(R.id.txtDescricao);
        if (!title.equals("Inserir Atividade")){
            try {
                txtDesc.setText(fachada.atividadeProcurar(id).getDescricao());
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
                            fachada.cadastrar(a);
                            Toast.makeText(getActivity(), "Atividade salva com sucesso", Toast.LENGTH_SHORT).show();
                            FragmentAtividade.popularLista();
                            dismiss();
                        } else {
                            a.setId(id);
                            a.setDescricao(txtDesc.getText().toString());
                            fachada.atualizar(a);
                            Toast.makeText(getActivity(), "Atividade salva com sucesso", Toast.LENGTH_SHORT).show();
                            FragmentAtividade.popularLista();
                            dismiss();
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
}