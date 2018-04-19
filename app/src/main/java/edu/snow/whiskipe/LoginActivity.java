package edu.snow.whiskipe;

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
            usernameEditText = (EditText)findViewById(R.id.edittext_usernamenew);
            username = usernameEditText.getText().toString();

            new LoginTask().execute();
        }

        public void createUser(View v){
            Intent createIntent = new Intent (this, CreateUserActivity.class);
            this.startActivity(createIntent);
            finish();
        }

        private class LoginTask extends AsyncTask {

            @Override
            protected Object doInBackground(Object[] objects) {
                user = database.userExists(username);

                if(user == null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                            usernameEditText.setText("");
                        }
                    });


                }
                else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Login Successful\nWelcome " + user.getFirstname(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("userid", user.getId());
                    intent.putExtra("username", user.getUsername());
                    intent.putExtra("userfirstname", user.getFirstname());
                    intent.putExtra("userlastname", user.getLastname());

                    startActivity(intent);
                    finish();
                }


                return null;
            }
        }

    private class MakeUserTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            user = database.userExists(username);

            if(user == null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                        usernameEditText.setText("");
                    }
                });


            }
            else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Login Successful\nWelcome " + user.getFirstname(), Toast.LENGTH_SHORT).show();
                    }
                });

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("userid", user.getId());
                intent.putExtra("username", user.getUsername());
                intent.putExtra("userfirstname", user.getFirstname());
                intent.putExtra("userlastname", user.getLastname());

                startActivity(intent);
                finish();
            }


            return null;
        }
    }


    }

