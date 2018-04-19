package edu.snow.whiskipe;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateUserActivity extends AppCompatActivity {
    private MSSQLManager database;
    private User user;
    private EditText usernameEditText, firstnameEditText, lastnameEditText;
    private String username, firstname, lastname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = MSSQLManager.getInstance();
        setContentView(R.layout.activity_create_user);
    }

    public void createUserAndLogin(View v){
        usernameEditText = (EditText)findViewById(R.id.edittext_usernamenew);
        username = usernameEditText.getText().toString();

        firstnameEditText = (EditText)findViewById(R.id.edittext_firstnamenew);
        firstname = firstnameEditText.getText().toString();

        lastnameEditText = (EditText)findViewById(R.id.edittext_lastnamenew);
        lastname = lastnameEditText.getText().toString();

        new CreateUserTask().execute();
    }

    private class CreateUserTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            user = new User(username, firstname, lastname);
            database.createUser(user);

            if(user == null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Couldn't create user", Toast.LENGTH_SHORT).show();
                    }
                });


            }
            else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Created user\nWelcome " + user.getFirstname(), Toast.LENGTH_SHORT).show();
                    }
                });

                Intent intent = new Intent(CreateUserActivity.this, MainActivity.class);
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
