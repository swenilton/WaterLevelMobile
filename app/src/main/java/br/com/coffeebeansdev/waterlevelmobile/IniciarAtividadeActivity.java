package br.com.coffeebeansdev.waterlevelmobile;

import android.app.ActionBar;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.coffeebeans.atividade.Atividade;
import br.com.coffeebeans.fachada.Fachada;

public class IniciarAtividadeActivity extends AppCompatActivity {

    TextView tvDesc, tvInicio, tvStatus;
    ImageButton btnStart, btnPause, btnStop;
    Chronometer chronometer;
    ProgressBar progressBar;
    Fachada fachada;
    Atividade atividade = null;
    int id;
    long tempoQuandoParado = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_atividade);
        setTitle("Iniciar Atividade");
        tvDesc = (TextView) findViewById(R.id.tvDesc);
        id = (int)getIntent().getExtras().get("id");
        try{
            fachada = Fachada.getInstance(getApplicationContext());
            atividade = fachada.atividadeProcurar(id);
        }catch (Exception e){

        }
        tvDesc.setText(atividade.getDescricao());
        chronometer = (Chronometer) findViewById(R.id.chronometer2);
        tvInicio = (TextView) findViewById(R.id.tvInicio2);
        tvStatus = (TextView) findViewById(R.id.tvStatus2);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        btnPause = (ImageButton) findViewById(R.id.btnPause);
        btnStart = (ImageButton) findViewById(R.id.btnStart);
        btnStop = (ImageButton) findViewById(R.id.btnStop);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });
        btnPause.setEnabled(false);
        btnStop.setEnabled(false);
    }

    private void stop() {
        btnStop.setEnabled(false);
        btnPause.setEnabled(false);
        btnStart.setEnabled(true);

        chronometer.stop();
        chronometer.setText("00:00");
        tempoQuandoParado = 0;
        progressBar.setVisibility(View.INVISIBLE);
        tvStatus.setText(AtividadeRealizadaListAdapter.PARADA);
        tvInicio.setText("00:00:00");
    }

    private void pause() {
        btnStop.setEnabled(true);
        btnPause.setEnabled(false);
        btnStart.setEnabled(true);

        tempoQuandoParado = chronometer.getBase() - SystemClock.elapsedRealtime();
        chronometer.stop();
        tvStatus.setText(AtividadeRealizadaListAdapter.PAUSADA);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void start() {
        btnStop.setEnabled(true);
        btnPause.setEnabled(true);
        btnStart.setEnabled(false);

        chronometer.setBase(SystemClock.elapsedRealtime() + tempoQuandoParado);
        chronometer.start();
        tempoQuandoParado = 0;
        tvStatus.setText(AtividadeRealizadaListAdapter.INICIADA);
        progressBar.setVisibility(View.VISIBLE);
        if(tvInicio.getText().equals("00:00:00"))
            tvInicio.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;
    }
}
