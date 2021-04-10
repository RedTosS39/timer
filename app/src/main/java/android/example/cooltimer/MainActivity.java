package android.example.cooltimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                if(sharedPreferences.getBoolean("enable_sound", true)) {
                    String melodyName = sharedPreferences.getString("timer_melody", "bell");
                    switch (melodyName) {
                        case "bell": {
                            MediaPlayer mediaPlayer;
                            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bell_sound);
                            mediaPlayer.start();
                            break;
                        }
                        case "alarm": {
                            MediaPlayer mediaPlayer;
                            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm_siren_sound);
                            mediaPlayer.start();
                            break;
                        }
                        case "bip": {
                            MediaPlayer mediaPlayer;
                            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bip_sound);
                            mediaPlayer.start();
                            break;
                        }
                    }

                }
                
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.timer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        } else if(id == R.id.action_about) {
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
