package edu.snow.whiskipe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by katel on 3/21/2018.
 */

public class InsertActivity extends AppCompatActivity {
    private DatabaseManager dbManager;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        setContentView(R.layout.activity_insert);
    }

    public void insert(View v){
        EditText nameEditText = (EditText)findViewById(R.id.input_name);
        EditText priceEditText = (EditText)findViewById(R.id.input_qty);

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

