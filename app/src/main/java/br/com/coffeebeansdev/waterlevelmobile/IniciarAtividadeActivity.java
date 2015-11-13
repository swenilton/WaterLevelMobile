package br.com.coffeebeansdev.waterlevelmobile;

import android.app.ActionBar;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
        notifica();
    }

    private void notifica() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ico)
                        .setContentTitle("Atividade Iniciada")
                        .setContentText(atividade.getDescricao());
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MainActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;
    }
}
