package edu.snow.whiskipe;

import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by katel on 3/27/2018.
 */

public class UpdateActivity extends AppCompatActivity {
    private MSSQLManager database;
    private User user;
    private ArrayList<Item> items;
    private Item toUpdate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = MSSQLManager.getInstance();

        user = new User();
        Bundle extras = getIntent().getExtras();
        user.setId(extras.getInt("userid"));
        user.setUsername(extras.getString("username"));
        user.setFirstname(extras.getString("userfirstname"));
        user.setLastname(extras.getString("userlastname"));

        toUpdate = null;

        new GetItems().execute();
    }

    public void updateView() {
        if (items.size() > 0) {
            ScrollView scrollView = new ScrollView(this);
            GridLayout grid = new GridLayout(this);
            grid.setRowCount(items.size());
            grid.setColumnCount(5);

            TextView[] ids = new TextView[items.size()];
            EditText[][] namequantitysizes = new EditText[items.size()][3];
            Button[] buttons = new Button[items.size()];
            ButtonHandler bh = new ButtonHandler();

            Point size = new Point();
            getWindowManager().getDefaultDisplay().getSize(size);
            int width = size.x;

            int i = 0;

            for (Item item : items) {
                ids[i] = new TextView(this);
                ids[i].setGravity(Gravity.CENTER);
                ids[i].setText("" + item.getId());

                namequantitysizes[i][0] = new EditText(this);
                namequantitysizes[i][1] = new EditText(this);
                namequantitysizes[i][2] = new EditText(this);
                namequantitysizes[i][0].setText(item.getName());
                namequantitysizes[i][1].setText("" + item.getQty());
                namequantitysizes[i][2].setText("" + item.getSize());
                namequantitysizes[i][1].setInputType(InputType.TYPE_CLASS_NUMBER);
                namequantitysizes[i][2].setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                namequantitysizes[i][0].setId(10 * item.getId());
                namequantitysizes[i][1].setId(10 * item.getId() + 1);
                namequantitysizes[i][2].setId(10 * item.getId() + 2);

                buttons[i] = new Button(this);
                buttons[i].setText("Update");
                buttons[i].setId(item.getId());
                buttons[i].setTextSize(12.0f);

                buttons[i].setOnClickListener(bh);

                grid.addView(ids[i], width / 10, ViewGroup.LayoutParams.WRAP_CONTENT);
                grid.addView(namequantitysizes[i][0], (int) (width * .4), ViewGroup.LayoutParams.WRAP_CONTENT);
                grid.addView(namequantitysizes[i][1], (int) (width * .15), ViewGroup.LayoutParams.WRAP_CONTENT);
                grid.addView(namequantitysizes[i][2], (int) (width * .15), ViewGroup.LayoutParams.WRAP_CONTENT);
                grid.addView(buttons[i], (int) (width * .2), ViewGroup.LayoutParams.WRAP_CONTENT);

                i++;
            }
            scrollView.addView(grid);
            setContentView(scrollView);
        }
    }


    private class ButtonHandler implements View.OnClickListener {
        public void onClick(View v) {

            int foodId = v.getId();
            EditText nameET = (EditText) findViewById(10 * foodId);
            EditText qtyET = (EditText) findViewById(10 * foodId +1);
            EditText sizeET = (EditText) findViewById(10 * foodId +2);
            String name = nameET.getText().toString();
            String qtyString = qtyET.getText().toString();
            String sizeString = sizeET.getText().toString();

            try{
                toUpdate = new Item();
                toUpdate.setId(foodId);
                toUpdate.setName(name);
                toUpdate.setQty((int) Double.parseDouble(qtyString));
                toUpdate.setSize(Double.parseDouble(sizeString));

                new UpdateItem().execute();

            }catch (NumberFormatException nfe){
                Toast.makeText(UpdateActivity.this, "Quantity error", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class GetItems extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {

            items = database.getItems(user);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateView();
                }
            });

            return null;
        }
    }

    private class UpdateItem extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {

            final boolean worked = database.updateItem(toUpdate);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(worked){
                        Toast.makeText(UpdateActivity.this, "Item updated", Toast.LENGTH_SHORT).show();
                        new GetItems().execute();
                    }
                    else{
                        Toast.makeText(UpdateActivity.this, "Item couldn't be updated.", Toast.LENGTH_SHORT).show();
                        new GetItems().execute();
                    }
                }
            });


            return null;
        }
    }
}


