package android.example.cooltimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
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
    private boolean isTimerOn;
    private Button button;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        isTimerOn = false;
        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView);

        //set Max for SeekBar(sec)
        seekBar.setMax(600);
        seekBar.setProgress(30);

        //set text by seekBar progress
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                long progressMillis = progress * 1000;
                updateTimer(progressMillis);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void start(View view) {
        if(!isTimerOn) {
            button.setText("STOP");
            seekBar.setEnabled(false);
            isTimerOn = true;
            timer();
        } else {
            resetTimer();
        }
    }

    private void timer () {
        countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                MediaPlayer mediaPlayer;
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bell_sound);
                mediaPlayer.start();
                resetTimer();
            }
        }.start();
    }

    private void resetTimer() {
        countDownTimer.cancel();
        textView.setText("00 : 30");
        button.setText("START");
        seekBar.setEnabled(true);
        seekBar.setProgress(30);
        isTimerOn = false;
    }

    private  void updateTimer(long millisUntilFinished) {
        int minutes = (int) millisUntilFinished / 60000;
        int seconds = (int) (millisUntilFinished / 1000 - minutes * 60);

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
}
