package br.com.coffeebeansdev.waterlevelmobile;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.coffeebeans.dispositivo.Dispositivo;
import br.com.coffeebeans.fachada.Fachada;

/**
 * Created by swenilton on 04/10/15.
 */
public class DialogInserirDispositivo extends DialogFragment {

    private static View rootView;

    private Fachada fachada;
    public DialogInserirDispositivo() {
        try {
            fachada = Fachada.getInstance(Fachada.context);
        } catch (Exception e){
            Log.i("Erro", e.getMessage());
        }
    }

    public static DialogInserirDispositivo newInstance(String titulo, int id) {
        DialogInserirDispositivo frag = new DialogInserirDispositivo();
        Bundle args = new Bundle();
        args.putString("title", titulo);
        args.putInt("id", id);
        frag.setArguments(args);
        return frag;
    }


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return rootView = inflater.inflate(R.layout.fragment_inserir_dispositivo, container);
    }


    @Override

    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String title = getArguments().getString("title");
        getDialog().setTitle(title);
        final int id = getArguments().getInt("id");
        final EditText txtNome = (EditText) view.findViewById(R.id.txtNome);
        final EditText txtPorta = (EditText) view.findViewById(R.id.txtPorta);
        final EditText txtHost = (EditText) view.findViewById(R.id.txtHost);
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       android.text.Spanned dest, int dstart, int dend) {
                if (end > start) {
                    String destTxt = dest.toString();
                    String resultingTxt = destTxt.substring(0, dstart) + source.subSequence(start, end) + destTxt.substring(dend);
                    if (!resultingTxt.matches ("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) {
                        return "";
                    } else {
                        String[] splits = resultingTxt.split("\\.");
                        for (int i=0; i<splits.length; i++) {
                            if (Integer.valueOf(splits[i]) > 255) {
                                return "";
                            }
                        }
                    }
                }
                return null;
            }

        };
        txtHost.setFilters(filters);
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
                    if (txtNome.getText().toString().isEmpty()) {
                        txtNome.setError(getString(R.string.error_field_required));
                        focusView = txtNome;
                        cancela = true;
                    }
                    if (txtHost.getText().toString().isEmpty()) {
                        txtHost.setError(getString(R.string.error_field_required));
                        focusView = txtHost;
                        cancela = true;
                    }
                    if (txtPorta.getText().toString().isEmpty()) {
                        txtPorta.setError(getString(R.string.error_field_required));
                        focusView = txtPorta;
                        cancela = true;
                    }
                    if (cancela) {
                        focusView.requestFocus();
                    } else {
                        Dispositivo d = new Dispositivo(txtHost.getText().toString(), Integer.valueOf(txtPorta.getText().toString()),
                                txtNome.getText().toString());
                        if (title.equals("Inserir Dispositivo")) {
                            fachada.cadastrar(d);
                            Toast.makeText(getActivity(), "Dispositivo salvo com sucesso", Toast.LENGTH_SHORT).show();
                            FragmentDisp.popularLista();
                            dismiss();
                        } else if (title.equals("Novo Dispositivo")) {
                            fachada.cadastrar(d);
                            Toast.makeText(getActivity(), "Dispositivo salvo com sucesso.\nDispositivo usado como ativo", Toast.LENGTH_SHORT).show();
                            fachada.setDispositivoAtivo(fachada.dispositivoProcurarNome(d.getNome()));
                            dismiss();
                        } else {
                            d.setId(id);
                            fachada.atualizar(d);
                            Toast.makeText(getActivity(), "Dispositivo salvo com sucesso", Toast.LENGTH_SHORT).show();
                            FragmentAtividade.popularLista();
                            dismiss();
                        }
                    }
                } catch (Exception e) {
                    Log.i("Erro: ", e.getMessage());
                    Toast.makeText(getActivity(), "Erro\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        if (!title.equals("Inserir Dispositivo")){
            try {
                Dispositivo d = fachada.dispositivoProcurar(id);
                txtHost.setText(d.getHost());
                txtNome.setText(d.getNome());
                txtPorta.setText(String.valueOf(d.getPorta()));
            } catch (Exception e){
                Log.i("Erro: ", e.getMessage());
            }
        }
        txtNome.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}