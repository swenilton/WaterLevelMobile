package br.com.coffeebeansdev.waterlevelmobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import br.com.coffeebeans.fachada.Fachada;
import br.com.coffeebeans.usuario.Usuario;

/**
 * Created by swenilton on 04/10/15.
 */
public class DialogInserirUsuario extends DialogFragment {

    private int IMAGEM_INTERNA = 12;
    private static View rootView;
    private static ImageView img = null;

    private Fachada fachada;
    public DialogInserirUsuario() {
        try {
            fachada = Fachada.getInstance(Fachada.context);
        } catch (Exception e){
            Log.i("Erro", e.getMessage());
        }
    }


    public static DialogInserirUsuario newInstance(String title, int id) {
        DialogInserirUsuario frag = new DialogInserirUsuario();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("id", id);
        frag.setArguments(args);
        return frag;
    }


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return rootView = inflater.inflate(R.layout.fragment_inserir_usuario, container);
    }


    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String title = getArguments().getString("title");
        getDialog().setTitle(title);
        final int id = getArguments().getInt("id");
        img = (ImageView) view.findViewById(R.id.imageView2);
        final EditText txtNome = (EditText) view.findViewById(R.id.txtNome);
        final EditText txtEmail = (EditText) view.findViewById(R.id.txtEmail);
        final EditText txtLogin = (EditText) view.findViewById(R.id.txtLogin);
        final EditText txtSenha = (EditText) view.findViewById(R.id.txtSenha);
        final EditText txtConfirmaSenha = (EditText) view.findViewById(R.id.txtConfirmaSenha);
        final EditText txtTelefone = (EditText) view.findViewById(R.id.txtTelefone);
        final Spinner spPerfil = (Spinner) view.findViewById(R.id.spinner);
        final Spinner spAtivo = (Spinner) view.findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.perfil_usuario, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPerfil.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.status_usuario, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAtivo.setAdapter(adapter2);
        if (!title.equals("Inserir Usuario")){
            try {
                Usuario u = fachada.usuarioProcurar(id);
                txtNome.setText(u.getNome());
                txtEmail.setText(u.getEmail());
                txtLogin.setText(u.getLogin());
                txtSenha.setText(u.getSenha());
                txtConfirmaSenha.setText(u.getSenha());
                txtTelefone.setText(u.getTelefone());
                spAtivo.setSelection(getIndex(spAtivo, u.getAtivo()));
                spPerfil.setSelection(getIndex(spPerfil, u.getPerfil()));
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
        Button btnSalvar = (Button) view.findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    boolean cancela = false;
                    View focusView = null;
                    if (txtEmail.getText().toString().isEmpty()) {
                        txtEmail.setError(getString(R.string.error_field_required));
                        cancela = true;
                        focusView = txtEmail;
                    } else if (!isEmailValid(txtEmail.getText().toString())) {
                        txtEmail.setError("Digite um email válido");
                        cancela = true;
                        focusView = txtEmail;
                    }
                    if (txtSenha.getText().toString().isEmpty()) {
                        txtSenha.setError(getString(R.string.error_field_required));
                        focusView = txtSenha;
                        cancela = true;
                    } else if (!isPasswordValid(txtSenha.getText().toString())) {
                        txtSenha.setError("A senha deve ter pelo menos 5 caracteres");
                        cancela = true;
                        focusView = txtSenha;
                    }
                    if (!txtConfirmaSenha.getText().toString().equals(txtSenha.getText().toString())) {
                        txtConfirmaSenha.setError("Senhas Diferentes");
                        focusView = txtConfirmaSenha;
                        cancela = true;
                    }
                    if (txtNome.getText().toString().isEmpty()) {
                        txtNome.setError(getString(R.string.error_field_required));
                        focusView = txtNome;
                        cancela = true;
                    }
                    if (txtLogin.getText().toString().isEmpty()) {
                        txtLogin.setError(getString(R.string.error_field_required));
                        focusView = txtLogin;
                        cancela = true;
                    }
                    if (txtTelefone.getText().toString().isEmpty()) {
                        txtTelefone.setError(getString(R.string.error_field_required));
                        focusView = txtTelefone;
                        cancela = true;
                    }
                    if (cancela) {
                        focusView.requestFocus();
                    } else {
                        Usuario u = new Usuario(txtNome.getText().toString(), txtLogin.getText().toString(),
                                txtSenha.getText().toString(), txtEmail.getText().toString(),
                                spAtivo.getSelectedItem().toString(), spPerfil.getSelectedItem().toString());
                        u.setTelefone(txtTelefone.getText().toString());

                        if (title.equals("Inserir Usuario")) {
                            fachada.cadastrar(u);
                            Toast.makeText(getContext(), "Usuario salvo com sucesso", Toast.LENGTH_SHORT).show();
                            FragmentUsuario.popularLista();
                            dismiss();
                        } else {
                            u.setId(id);
                            fachada.atualizar(u);
                            Toast.makeText(getContext(), "Usuario salvo com sucesso", Toast.LENGTH_SHORT).show();
                            FragmentUsuario.popularLista();
                            dismiss();
                        }
                    }
                } catch (Exception e) {
                    Log.i("Erro: ", e.getMessage());
                    Toast.makeText(getContext(), "Erro\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGEM_INTERNA);
            }
        });
        txtNome.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
    public static void setImg(Bitmap bmp){
        img.setImageBitmap(bmp);
    }
    private int getIndex(Spinner spinner, String myString) {
        int index = 0;
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@") && email.contains(".");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


}