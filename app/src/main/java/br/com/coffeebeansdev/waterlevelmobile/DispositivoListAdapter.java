package br.com.coffeebeansdev.waterlevelmobile;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import br.com.coffeebeans.atividade.Atividade;
import br.com.coffeebeans.dispositivo.Dispositivo;
import br.com.coffeebeans.exception.DAOException;
import br.com.coffeebeans.exception.DispositivoNaoEncontradoException;
import br.com.coffeebeans.fachada.Fachada;

/**
 * Created by swenilton on 04/10/15.
 */
public class DispositivoListAdapter extends BaseAdapter {

    private List<Dispositivo> dispositivos;
    private Context context;
    private Fachada fachada;
    private FragmentManager fragmentManager;

    public DispositivoListAdapter(Context context, FragmentManager fragmentManager, List<Dispositivo> dispositivos) {
        this.fragmentManager = fragmentManager;
        this.context = context;
        this.dispositivos = dispositivos;
        try {
            this.fachada = Fachada.getInstance(context);
        } catch (Exception e){
            Log.i("Fachada: ", e.getMessage());
        }
    }

    @Override
    public int getCount() {
        return dispositivos.size();
    }

    @Override
    public Object getItem(int position) {
        return dispositivos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dispositivos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Dispositivo dispositivo = dispositivos.get(position);
        Log.i("Dispositivo", dispositivo.toString());
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.list_view_dispositivo, null);
        TextView textNome = (TextView)view.findViewById(R.id.txtNome);
        textNome.setText(dispositivo.getNome());
        TextView textHost = (TextView)view.findViewById(R.id.txtHost);
        textHost.setText(dispositivo.getHost());
        TextView textPorta = (TextView)view.findViewById(R.id.txtPorta);
        textPorta.setText(String.valueOf(dispositivo.getPorta()));
        ImageButton btnDelete = (ImageButton) view.findViewById(R.id.btnDelete);
        ImageButton btnEdit = (ImageButton) view.findViewById(R.id.btnEdit);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Tem certeza que deseja excluir o registro selecionado?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                try{
                                    fachada.dispositivoRemover(dispositivo.getId());
                                    Toast.makeText(view.getContext(), "Dispositivo removido.",
                                            Toast.LENGTH_LONG).show();
                                    FragmentDisp.popularLista();
                                    dialog.dismiss();
                                } catch (Exception e){
                                    Toast.makeText(view.getContext(), "Erro ao remover dispositivo\n" + e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                builder.create();
                builder.show();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = fragmentManager;
                DialogInserirDispositivo inserirDispositivo = DialogInserirDispositivo.newInstance("Alterar Dispositivo", dispositivo.getId());
                inserirDispositivo.show(fm, "fragment_inserir_dispositivo");
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Atenção").setMessage("Tem certeza que deseja ativar esse dispositivo?").setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            fachada.setDispositivoAtivo(dispositivo);
                            Toast.makeText(view.getContext(), "Dispositivo ativado com sucesso",
                                    Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(view.getContext(), "Erro ao ativar dispositivo\n" + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });
        return view;
    }

}
