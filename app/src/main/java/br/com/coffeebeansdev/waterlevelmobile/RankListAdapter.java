package br.com.coffeebeansdev.waterlevelmobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.coffeebeans.fachada.Fachada;
import br.com.coffeebeans.usuario.Usuario;

/**
 * Created by swenilton on 23/10/15.
 */
public class RankListAdapter extends BaseAdapter {
    private List<Usuario> usuarios;
    private Context context;
    private Fachada fachada;
    private FragmentManager fragmentManager;

    public RankListAdapter(Context context, FragmentManager fragmentManager, List<Usuario> usuarios) {
        this.fragmentManager = fragmentManager;
        this.context = context;
        this.usuarios = usuarios;
        try {
            this.fachada = Fachada.getInstance(context);
        } catch (Exception e){
            Log.i("Fachada: ", e.getMessage());
        }
    }

    @Override
    public int getCount() {
        return usuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return usuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Usuario usuario = usuarios.get(position);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_view_rank, null);
        TextView textNome = (TextView)view.findViewById(R.id.textDescricao);
        textNome.setText(usuario.getNome());
        TextView tvPosicao = (TextView) view.findViewById(R.id.lblPosicao);
        tvPosicao.setText(position + 1 + "º");
//        TextView textPerfil = (TextView)view.findViewById(R.id.textPerfil);
//        textPerfil.setText(usuario.getPerfil());
//        TextView textStatus = (TextView)view.findViewById(R.id.textStatus);
//        textStatus.setText(usuario.getAtivo());
//        ImageView img = (ImageView)view.findViewById(R.id.imgUsuario);
//        img.setImageResource(0);
//        view.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
//                builder.setTitle("Opções")
//                        .setItems(R.array.opc_menu, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                if (which == 0) {
//                                    FragmentManager fm = fragmentManager;
//                                    DialogInserirUsuario inserirUsuario = DialogInserirUsuario.newInstance("Atualizar Usuario", usuario.getId());
//                                    inserirUsuario.show(fm, "fragment_inserir_usuario");
//                                    dialog.dismiss();
//                                } else {
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                                    builder.setMessage("Tem certeza que deseja excluir o registro selecionado?")
//                                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
//                                                public void onClick(DialogInterface dialog, int id) {
//                                                    try {
//                                                        fachada.usuarioRemover(usuario.getId());
//                                                        Toast.makeText(context, "Usuario removido.",
//                                                                Toast.LENGTH_LONG).show();
//                                                        FragmentUsuario.popularLista();
//                                                        dialog.dismiss();
//                                                    } catch (Exception e) {
//                                                        Toast.makeText(context, "Erro ao remover usuario\n" + e.getMessage(),
//                                                                Toast.LENGTH_LONG).show();
//                                                    }
//                                                }
//                                            })
//                                            .setNegativeButton("Não", new DialogInterface.OnClickListener() {
//                                                public void onClick(DialogInterface dialog, int id) {
//                                                    dialog.dismiss();
//                                                }
//                                            });
//                                    builder.create();
//                                    builder.show();
//                                }
//                            }
//                        });
//                builder.create();
//                builder.show();
//                return true;
//            }
//        });
        return view;
    }
}
