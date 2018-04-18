package edu.snow.whiskipe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity{
        private DatabaseManager dbManager;

        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            dbManager = new DatabaseManager(this);
            setContentView(R.layout.activity_login);
        }

        public void login(View v){
            EditText usernameEditText = (EditText)findViewById(R.id.input_name);
            EditText firstnameEditText = (EditText)findViewById(R.id.input_firstname);
            EditText lastnameEditText = (EditText)findViewById(R.id.input_lastname);

            String username = usernameEditText.getText().toString();
            String firstname = firstnameEditText.getText().toString();
            String lastname = lastnameEditText.getText().toString();

                User user = new User(0, username, firstname, lastname);
                dbManager.login(user);
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();


            usernameEditText.setText("");
            firstnameEditText.setText("");
            lastnameEditText.setText("");
        }


    }

