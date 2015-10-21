package br.com.coffeebeansdev.waterlevelmobile;

import android.app.Activity;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentHome.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {
    View rootView;
    private ProgressBar progressBar;
    private TextView textView;
    private TabHost myTabHost;
    boolean isRunning = true;
    Random random = new Random();
    int oldProgress = 0;

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
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        textView = (TextView) rootView.findViewById(R.id.lblNivel);

        myTabHost =(TabHost) rootView.findViewById(R.id.TabHost01);
// Before adding tabs, it is imperative to call the method setup()
        myTabHost.setup();
// Adding tabs
        // tab1 settings
        TabHost.TabSpec spec = myTabHost.newTabSpec("tab_creation");
        // text and image of tab
        spec.setIndicator("Repositorio 1",getResources().getDrawable(android.R.drawable.ic_menu_add));
        // specify layout of tab
        spec.setContent(R.id.onglet1);
        // adding tab in TabHost
        myTabHost.addTab(spec);
// otherwise :
       myTabHost.addTab(myTabHost.newTabSpec("tab_inser").setIndicator("Repositorio 2", getResources().getDrawable(android.R.drawable.ic_menu_edit)).setContent(R.id.Onglet2));

       // myTabHost.addTab(myTabHost.newTabSpec("tab_affiche").setIndicator("Show All",getResources().getDrawable(android.R.drawable.ic_menu_view)).setContent(R.id.Onglet3));





//        try{
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    while (true) {
//                        try {
//                            nivel(progressBar, textView, random.nextInt(100));
//                            Thread.sleep(2000);
//                        } catch (InterruptedException e) {
//                            Log.i("Erro ProgressBar", e.getMessage());
//                            break;
//                        } catch (Exception e){
//                            Log.i("Erro ProgressBar", e.getMessage());
//                            break;
//                        }
//                    }
//                }
//                public synchronized void nivel(ProgressBar progressBar, TextView textView, int nivel){
//                    progressBar.setProgress(nivel);
//                    textView.setText(nivel + "%");
////        ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, progressBar.getProgress(), nivel);
////        anim.setDuration(1000);
////        progressBar.startAnimation(anim);
//                }
//            }).start();
//        } catch (Exception e){
//            Log.i("Erro Thread - ProgressBar", e.getMessage());
//        }
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        progressBar.setProgress(0);
        Thread background = new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        try {
                            oldProgress = progressBar.getProgress();
                            progressBar.setProgress(random.nextInt(100));
                            Thread.sleep(2000);
                            handler.sendMessage(handler.obtainMessage());
                        } catch (InterruptedException e) {
                            Log.i("Erro ProgressBar", e.getMessage());
                            break;
                        } catch (Exception e) {
                            Log.i("Erro ProgressBar", e.getMessage());
                            break;
                        }
                    }
                }
                catch(Throwable t) {
                    // just end the background thread
                }
            }
        });
        isRunning = true;
        background.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        isRunning = false;
    }

}
