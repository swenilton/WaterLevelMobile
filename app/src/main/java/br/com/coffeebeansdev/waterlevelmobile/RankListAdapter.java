package br.com.coffeebeansdev.waterlevelmobile;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

    public RankListAdapter(Context context, List<Usuario> usuarios) {
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
        ImageView imageView = (ImageView)view.findViewById(R.id.imgUsuario);
        if(usuario.getFoto() != null) {
            imageView.setImageBitmap(decodeSampledBitmapFromPath(usuario.getFoto(), 100, 100));
        } else {
            imageView.setImageResource(R.drawable.ic_user);
//            imageView.setBackgroundResource(R.color.cinza);
        }
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
