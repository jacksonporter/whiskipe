package edu.snow.whiskipe;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    DatabaseManager dbManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        updateView();
    }

    public void updateView() {
        ArrayList<Food> foodList = dbManager.selectAll();
        if (foodList.size() > 0) {
            ScrollView scrollView = new ScrollView(this);
            GridLayout grid = new GridLayout(this);
            grid.setRowCount(foodList.size());
            grid.setColumnCount(4);

            TextView[] ids = new TextView[foodList.size()];
            EditText[][] namesAndPrices = new EditText[foodList.size()][2];
            Button[] buttons = new Button[foodList.size()];
            ButtonHandler bh = new ButtonHandler();

            Point size = new Point();
            getWindowManager().getDefaultDisplay().getSize(size);
            int width = size.x;

            int i = 0;

            for (Food food : foodList) {
                ids[i] = new TextView(this);
                ids[i].setGravity(Gravity.CENTER);
                ids[i].setText("" + food.getId());

                namesAndPrices[i][0] = new EditText(this);
                namesAndPrices[i][1] = new EditText(this);
                namesAndPrices[i][0].setText(food.getName());
                namesAndPrices[i][1].setText("" + food.getQty());
                namesAndPrices[i][1].setInputType(InputType.TYPE_CLASS_NUMBER);
                namesAndPrices[i][0].setId(10 * food.getId());
                namesAndPrices[i][1].setId(10 * food.getId() + 1);

                buttons[i] = new Button(this);
                buttons[i].setText("Update");
                buttons[i].setId(food.getId());

                buttons[i].setOnClickListener(bh);

                grid.addView(ids[i], width / 10, ViewGroup.LayoutParams.WRAP_CONTENT);
                grid.addView(namesAndPrices[i][0], (int) (width * .4), ViewGroup.LayoutParams.WRAP_CONTENT);
                grid.addView(namesAndPrices[i][1], (int) (width * .15), ViewGroup.LayoutParams.WRAP_CONTENT);
                grid.addView(buttons[i], (int) (width * .35), ViewGroup.LayoutParams.WRAP_CONTENT);

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
            String name = nameET.getText().toString();
            String qtyString = qtyET.getText().toString();

            try{
                double qty = Double.parseDouble(qtyString);
                dbManager.updateById(foodId, name, qty);
                Toast.makeText(UpdateActivity.this, "Food item updated", Toast.LENGTH_SHORT).show();

                updateView();
            }catch (NumberFormatException nfe){
                Toast.makeText(UpdateActivity.this, "Quantity error", Toast.LENGTH_LONG).show();
            }
        }
    }
}


