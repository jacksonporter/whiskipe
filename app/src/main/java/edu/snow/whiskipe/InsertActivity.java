package edu.snow.whiskipe;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class InsertActivity extends AppCompatActivity {
    private MSSQLManager database;
    private User user;
    private Item item;
    private EditText nameEditText, qtyEditText, sizeEditText;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        database = MSSQLManager.getInstance();

        user = new User();
        Bundle extras = getIntent().getExtras();
        user.setId(extras.getInt("userid"));
        user.setUsername(extras.getString("username"));
        user.setFirstname(extras.getString("username"));
        user.setLastname(extras.getString("username"));

        setContentView(R.layout.activity_insert);

        nameEditText = (EditText)findViewById(R.id.input_name);
        if(nameEditText == null) { Log.w("InsertActivity", "name edit text is null"); }
        qtyEditText = (EditText)findViewById(R.id.input_qty);
        if(qtyEditText == null) { Log.w("InsertActivity", "qty edit text is null"); }
        sizeEditText = (EditText)findViewById(R.id.input_size);
        if(sizeEditText == null) { Log.w("InsertActivity", "size edit text is null"); }
    }

    public void insert(View v){
        String name = nameEditText.getText().toString();
        String qtyString = qtyEditText.getText().toString();
        String sizeString = sizeEditText.getText().toString();

        try{
            int qty = Integer.parseInt(qtyString);
            double size = Double.parseDouble(sizeString);

            item = new Item(name, qty, size);

            new MakeConnection().execute();

        }catch (NumberFormatException nfe){
            Toast.makeText(this, "Quantity or size error", Toast.LENGTH_LONG).show();
        }
    }

    public void goBack(View v){
        this.finish();
    }

    private class MakeConnection extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            if(database.insertItem(item, user)){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Item added", Toast.LENGTH_SHORT).show();
                        nameEditText.setText("");
                        qtyEditText.setText("");
                        sizeEditText.setText("");
                    }
                });


            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Error adding item to database.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }
    }
}

