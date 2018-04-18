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
            EditText firstnameEditText = (EditText)findViewById(R.id.input_name);
            EditText lastnameEditText = (EditText)findViewById(R.id.input_name);

            String name = nameEditText.getText().toString();
            String priceString = priceEditText.getText().toString();

            try{
                double qty = Double.parseDouble(priceString);
                Food food = new Food(0, name, qty);
                dbManager.insert(food);
                Toast.makeText(this, "Food item added", Toast.LENGTH_SHORT).show();
            }catch (NumberFormatException nfe){
                Toast.makeText(this, "Quantity error", Toast.LENGTH_LONG).show();
            }

            nameEditText.setText("");
            priceEditText.setText("");
        }

        public void goBack(View v){
            this.finish();
        }
    }

