package br.com.coffeebeansdev.waterlevelmobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import br.com.coffeebeans.fachada.Fachada;

/**
 * Created by swenilton on 04/10/15.
 */
public class DialogRecuperarSenha extends DialogFragment {

    private Fachada fachada;
    public DialogRecuperarSenha() {
        try {
            fachada = Fachada.getInstance(Fachada.context);
        } catch (Exception e){
            Log.i("Erro", e.getMessage());
        }
    }


    public static DialogRecuperarSenha newInstance() {
        DialogRecuperarSenha frag = new DialogRecuperarSenha();
        Bundle args = new Bundle();
        args.putString("title", "Digite seu Email");
        frag.setArguments(args);
        return frag;
    }


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recuperar_senha, container);
    }


    @Override

    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String title = getArguments().getString("title");
        getDialog().setTitle(title);
        final EditText txtEmail = (EditText) view.findViewById(R.id.txtEmail);
        Button btnCancelar = (Button) view.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Button btnEnviar = (Button) view.findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        txtEmail.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}