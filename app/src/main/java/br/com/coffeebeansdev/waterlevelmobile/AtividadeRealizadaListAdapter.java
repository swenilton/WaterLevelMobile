package br.com.coffeebeansdev.waterlevelmobile;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.coffeebeans.atividade.Atividade;
import br.com.coffeebeans.fachada.Fachada;

/**
 * Created by swenilton on 04/10/15.
 */
public class AtividadeRealizadaListAdapter extends BaseAdapter {

    private List<Atividade> atividades;
    private Context context;
    private Fachada fachada;
    private FragmentManager fragmentManager;
    public static final String PARADA = "PARADA";
    public static final String INICIADA = "INICIADA";
    public static final String PAUSADA = "PAUSADA";
    public static String status;

    public AtividadeRealizadaListAdapter(Context context, FragmentManager fragmentManager, List<Atividade> atividades) {
        this.fragmentManager = fragmentManager;
        this.context = context;
        this.atividades = atividades;
    }

    @Override
    public int getCount() {
        return atividades.size();
    }

    @Override
    public Object getItem(int position) {
        return atividades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        final Atividade atividade = atividades.get(position);

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.list_view_atividade_realizada, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, IniciarAtividadeActivity.class);
                intent.putExtra("id", atividades.get(position).getId());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    context.startActivity(intent);
                } else {
                    context.startActivity(intent);
                }
            }
        });
        TextView textDesc = (TextView) view.findViewById(R.id.textDescricao);
        textDesc.setText(atividade.getDescricao());

        return view;
    }
}
