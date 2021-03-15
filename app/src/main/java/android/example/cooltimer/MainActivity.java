package android.example.cooltimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView);

        //set Max for SeekBar(sec)
        seekBar.setMax(600);
        seekBar.setProgress(60);

        //set text by seekBar progress
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int minutes = progress / 60;
                int seconds = progress - minutes * 60;

                String minutesStr = "";
                String secondsStr = "";

                if(minutes < 10) {
                    minutesStr = "0" + minutes;
                } else {
                    minutesStr = String.valueOf(minutes);
                }

                if(seconds < 10) {
                    secondsStr = "0" + seconds;
                } else {
                    secondsStr = String.valueOf(seconds);
                }

                textView.setText(minutesStr + " : " + secondsStr);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}

//timer 1
        /*
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                Log.d("timer", "delay");
            }
        };
        handler.post(runnable);

 */
//timer 2
        /*  CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long mill
            isUntilFinished) {

                Log.d("timer", String.valueOf(millisUntilFinished) + " seconds ");
            }

            @Override
            public void onFinish() {
                Log.d("timer", "Finish!");
            }
        };

        countDownTimer.start();
        */
