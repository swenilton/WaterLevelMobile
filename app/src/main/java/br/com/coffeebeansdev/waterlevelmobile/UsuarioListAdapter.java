package br.com.coffeebeansdev.waterlevelmobile;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.util.List;

import br.com.coffeebeans.fachada.Fachada;
import br.com.coffeebeans.usuario.Usuario;

/**
 * Created by swenilton on 04/10/15.
 */
public class UsuarioListAdapter extends BaseAdapter {

    private List<Usuario> usuarios;
    private Context context;
    private Fachada fachada;
    private FragmentManager fragmentManager;

    public UsuarioListAdapter(Context context, FragmentManager fragmentManager, List<Usuario> usuarios) {
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
        return usuarios.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Usuario usuario = usuarios.get(position);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_view_usuario, null);
        TextView textNome = (TextView)view.findViewById(R.id.textDescricao);
        textNome.setText(usuario.getNome());
        TextView textEmail = (TextView) view.findViewById(R.id.tvEmail);
        textEmail.setText(usuario.getEmail());
        TextView textLogin = (TextView) view.findViewById(R.id.tvLogin);
        textLogin.setText(usuario.getLogin());
        TextView textPerfil = (TextView)view.findViewById(R.id.textPerfil);
        textPerfil.setText(usuario.getPerfil());
        TextView textStatus = (TextView)view.findViewById(R.id.textStatus);
        textStatus.setText(usuario.getAtivo());
        ImageView imageView = (ImageView)view.findViewById(R.id.imgUsuario);
        if(usuario.getFoto() != null) {
            imageView.setImageBitmap(decodeSampledBitmapFromPath(usuario.getFoto(), 100, 100));
        } else {
            imageView.setImageResource(R.drawable.ic_user);
//            imageView.setBackgroundResource(R.color.cinza);
        }
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Opções")
                        .setItems(R.array.opc_menu, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    FragmentManager fm = fragmentManager;
                                    DialogInserirUsuario inserirUsuario = DialogInserirUsuario.newInstance("Atualizar Usuario", usuario.getId());
                                    inserirUsuario.show(fm, "fragment_inserir_usuario");
                                    dialog.dismiss();
                                } else {
                                    AlertDialog.Builder builderConfirm = new AlertDialog.Builder(v.getContext());
                                    builderConfirm.setMessage("Tem certeza que deseja excluir o registro selecionado?")
                                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    try {
                                                        fachada.usuarioRemover(usuario.getId());
                                                        Toast.makeText(v.getContext(), "Usuario removido.",
                                                                Toast.LENGTH_LONG).show();
                                                        FragmentUsuario.popularLista();
                                                        dialog.dismiss();
                                                    } catch (Exception e) {
                                                        Toast.makeText(v.getContext(), "Erro ao remover usuario\n" + e.getMessage(),
                                                                Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            })
                                            .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    builderConfirm.create();
                                    builderConfirm.show();
                                }
                            }
                        });
                builder.create();
                builder.show();
                return true;
            }
        });
        return view;
    }

    public static Bitmap decodeSampledBitmapFromPath(String path, int reqWidth, int reqHeight) {

        //Bitmap bmp = BitmapFactory.decodeFile(usuario.getFoto());
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(path, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeFile(path, options);

        return bmp;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
