package edu.snow.whiskipe;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    //TimedTask t = new TimedTask();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startSplash();

        new MakeConnection().execute();
        //t.run();
    }

    private void startSplash(){
        Timer timer = new Timer();
        timer.schedule(new TimedTask(), 3000);
    }

    private class MakeConnection extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            MSSQLManager manager = MSSQLManager.getInstance();
            if(manager != null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Connected!", Toast.LENGTH_LONG).show();
                    }
                });
            }
            else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Not Connected :(", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
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
