package edu.snow.whiskipe;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {
    //TimedTask t = new TimedTask();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startSplash();
        //t.run();
    }

    private void startSplash(){
        Timer timer = new Timer();
        timer.schedule(new TimedTask(), 3000);
    }

    private class TimedTask extends TimerTask{
        public TimedTask(){}

        @Override
        public void run() {
           // startSplash();
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

}
