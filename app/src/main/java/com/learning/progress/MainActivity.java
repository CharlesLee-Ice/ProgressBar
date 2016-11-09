package com.learning.progress;

import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ProgressBar pg1;
    ProgressBar pg2;
    ViewGroup pg3;
    ArcProgress pg4;
    ProgressBar pg5;
    ClipDrawable pg6;

    int pg3Width;
    int pg3Count;

    int pg6Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pg1 = (ProgressBar) findViewById(R.id.progress_bar_1);
        pg2 = (ProgressBar) findViewById(R.id.progress_bar_2);
        pg3 = (ViewGroup) findViewById(R.id.progress_bar_3);
        pg4 = (ArcProgress) findViewById(R.id.progress_bar_4);
        pg5 = (ProgressBar) findViewById(R.id.progress_bar_5);
        pg6 = (ClipDrawable) ((ImageView)findViewById(R.id.progress_bar_6)).getDrawable();

        pg3Width = pg3.getLayoutParams().width;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pg1.setProgress((pg1.getProgress() + (int) (pg1.getMax() * 0.05)) % pg1.getMax());
                        pg2.setProgress((pg2.getProgress() + (int) (pg2.getMax() * 0.05)) % pg2.getMax());

                        ViewGroup.LayoutParams params = pg3.getLayoutParams();
                        params.width = (int)(pg3Count++ % 20 / (float)20 * pg3Width);
                        pg3.setLayoutParams(params);

                        float progress = pg4.getProgress();
                        progress += 0.05;
                        pg4.setProgress(progress > 1 ? progress - 1 : progress);

                        pg5.setProgress((pg5.getProgress() + (int) (pg5.getMax() * 0.05)) % pg5.getMax());

                        pg6Level += 500;
                        pg6.setLevel(pg6Level % 10000);
                    }
                });
            }
        }, 200, 100);
    }
}
