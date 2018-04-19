package edu.snow.whiskipe;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
        private MSSQLManager database;
        private User user;
        private EditText usernameEditText;
        private String username;

        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            database = MSSQLManager.getInstance();
            setContentView(R.layout.activity_login);
        }

        public void login(View v){
            usernameEditText = (EditText)findViewById(R.id.input_username);
            username = usernameEditText.getText().toString();

            new MakeConnection().execute();
        }

    private class MakeConnection extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            user = database.userExists(username);

            if(user == null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                });

                usernameEditText.setText("");
            }
            else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Login Successful\nWelcome " + user.getFirstname(), Toast.LENGTH_SHORT).show();
                    }
                });

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }


            return null;
        }
    }


    }

