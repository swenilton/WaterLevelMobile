package br.com.coffeebeansdev.waterlevelmobile;

/**
 * Created by swenilton on 23/10/15.
 */


import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;
import java.util.zip.Inflater;

import br.com.coffeebeans.repositorio.Repositorio;

public class HomeFragmentPageAdapter extends PagerAdapter {

    List<Repositorio> repositorios;
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

    /** Constructor of the class */
    public HomeFragmentPageAdapter(List<Repositorio> repositorios) {
        this.repositorios = repositorios;
    }

    /** This method will be invoked when a page is requested to create */
//    @Override
//    public Fragment getItem(int arg0) {
//        //FragmentRank myFragment = new FragmentRank();
////        Bundle data = new Bundle();
////        data.putInt("current_page", arg0+1);
////        myFragment.setArguments(data);
//        return new FragmentHomeBody();
//    }

    /** Returns the number of pages */
    @Override
    public int getCount() {
        return repositorios.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return repositorios.get(position).getDescricao();
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(container.getContext().LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.tab_rep, null);

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
                            Toast.makeText(container.getContext(), "Erro ProgressBar\n" + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            break;
                        } catch (Exception e) {
                            Log.i("Erro ProgressBar", e.getMessage());
                            Toast.makeText(container.getContext(), "Erro ProgressBar\n" + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
                catch(Throwable t) {
                    Log.i("Erro Thread", t.getMessage());
                    Toast.makeText(container.getContext(), "Erro thread\n" + t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        background.start();

        ((ViewPager) container).addView(rootView, 0);
        return rootView;
    }


}
