package edu.snow.whiskipe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class InsertActivity extends AppCompatActivity {
    private DatabaseManager dbManager;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        setContentView(R.layout.activity_insert);
    }

    public void insert(View v){
        EditText nameEditText = (EditText)findViewById(R.id.input_name);
        EditText qtyEditText = (EditText)findViewById(R.id.input_qty);
        EditText sizeEditText = (EditText)findViewById(R.id.input_size);

        String name = nameEditText.getText().toString();
        String qtyString = qtyEditText.getText().toString();
        String size = sizeEditText.getText().toString();

        try{
            double qty = Double.parseDouble(qtyString);
            Food food = new Food(0, name, qty, size);
            dbManager.insert(food);
            Toast.makeText(this, "Food item added", Toast.LENGTH_SHORT).show();
        }catch (NumberFormatException nfe){
            Toast.makeText(this, "Quantity error", Toast.LENGTH_LONG).show();
        }

        nameEditText.setText("");
        qtyEditText.setText("");
        sizeEditText.setText("");
    }

    public void goBack(View v){
        this.finish();
    }
}

