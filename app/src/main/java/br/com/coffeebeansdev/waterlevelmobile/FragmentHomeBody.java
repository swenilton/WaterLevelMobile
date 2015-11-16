package br.com.coffeebeansdev.waterlevelmobile;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class FragmentHomeBody extends Fragment {
    View rootView, automatico, manual;
    private ProgressBar progressBar;
    private TextView textView;
    boolean isRunning = true;
    Random random = new Random();
    int oldProgress = 0;
    RadioGroup radioGroup;
    RadioButton radioButtonManual, radioButtonAutomatico;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //bar.incrementProgressBy(5);
            textView.setText(progressBar.getProgress() + "%");
            ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, oldProgress, progressBar.getProgress());
            anim.setDuration(1000);
            progressBar.startAnimation(anim);
        }// handleMessage
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab_rep, container, false);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        textView = (TextView) rootView.findViewById(R.id.lblNivel);


        automatico = rootView.findViewById(R.id.layoutAutomatico);
        manual = rootView.findViewById(R.id.layoutManual);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButton){
                    automatico.setVisibility(View.VISIBLE);
                    manual.setVisibility(View.INVISIBLE);
                } else {
                    manual.setVisibility(View.VISIBLE);
                    automatico.setVisibility(View.INVISIBLE);
                }
            }
        });

        radioButtonAutomatico = (RadioButton) rootView.findViewById(R.id.radioButton);
        radioButtonManual = (RadioButton) rootView.findViewById(R.id.radioButton2);


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        progressBar.setProgress(0);
        isRunning = true;
        Thread background = new Thread(new Runnable() {
            public void run() {
                try {
                    while (isRunning) {
                        try {
                            oldProgress = progressBar.getProgress();
                            progressBar.setProgress(random.nextInt(100));
                            Thread.sleep(2000);
                            handler.sendMessage(handler.obtainMessage());
                        } catch (InterruptedException e) {
                            Log.i("Erro ProgressBar", e.getMessage());
                            Toast.makeText(getActivity(), "Erro ProgressBar\n" + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            break;
                        } catch (Exception e) {
                            Log.i("Erro ProgressBar", e.getMessage());
                            Toast.makeText(getActivity(), "Erro ProgressBar\n" + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
                catch(Throwable t) {
                    Log.i("Erro Thread", t.getMessage());
                    Toast.makeText(getActivity(), "Erro thread\n" + t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        background.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        isRunning = false;
    }

}
