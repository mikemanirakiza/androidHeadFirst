package study.pmoreira.stopwatch;

import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class StopWatchActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

        if (savedInstanceState != null) {
            running = savedInstanceState.getBoolean("running");
            seconds = savedInstanceState.getInt("seconds");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        } else {
            resetTimer();
        }

        runTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (wasRunning) {
            running = true;
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
        resetTimer();
    }

    private void resetTimer() {
        ((TextView) findViewById(R.id.time_view)).setText(String.valueOf("0:00:00"));
    }

    private void runTimer() {

        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (running) {
                    int hours = seconds / 3600;
                    int minutes = (seconds % 3600) / 60;
                    int secs = seconds % 60;

                    String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                    seconds++;
                    timeView.setText(time);
                }

                handler.postDelayed(this, 1000);
            }
        });

    }

}
