package edu.snow.whiskipe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import android.graphics.Point;

import java.text.NumberFormat;
import java.util.ArrayList;

    public class MainActivity extends AppCompatActivity {
        private DatabaseManager dbManager;
        private double total;
        private ScrollView scrollView;
        private int buttonWidth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

            dbManager = new DatabaseManager(this);
            total = 0.0;
            scrollView = (ScrollView)findViewById(R.id.scrollView);
            Point size = new Point();
            getWindowManager().getDefaultDisplay().getSize(size);
            buttonWidth = size.x/2;
            updateView();
        }

        protected void onResume(){
            super.onResume();
            updateView();
        }

        public void updateView(){
            ArrayList<Food> foodList = dbManager.selectAll();
            if(foodList.size()>0){
                scrollView.removeAllViewsInLayout();

                GridLayout grid = new GridLayout(this);
                grid.setRowCount(foodList.size()+1/2);
                grid.setColumnCount(2);

                FoodButton [] buttons = new FoodButton[foodList.size()];
                ButtonHandler bh = new ButtonHandler();

                int i =0;
                for(Food food:foodList){
                    buttons[i] = new FoodButton(this,food);
                    buttons[i].setText(food.getName()+ "\n" + food.getQty());

                    buttons[i].setOnClickListener(bh);

                    grid.addView(buttons[i], buttonWidth, GridLayout.LayoutParams.WRAP_CONTENT);
                    i++;
                }
                scrollView.addView(grid);
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

            switch (id){
                case R.id.action_add:
                    Log.w("MainActivity", "Add selected");
                    Intent insertIntent = new Intent (this, InsertActivity.class);
                    this.startActivity(insertIntent);
                    return true;
                case R.id.action_delete:
                    Log.w("MainActivity", "Delete selected");
                    Intent deleteIntent = new Intent(this, DeleteActivity.class);
                    this.startActivity(deleteIntent);
                    return true;
                case R.id.action_update:
                    Log.w("MainActivity", "Update selected");
                    Intent updateIntent = new Intent(this, UpdateActivity.class);
                    this.startActivity(updateIntent);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }


        }

        private class ButtonHandler implements View.OnClickListener{
            public void onClick(View v){
                total+=((FoodButton)v).getPrice();
                String pay = NumberFormat.getCurrencyInstance().format(total);
                Toast.makeText(MainActivity.this, pay, Toast.LENGTH_LONG).show();
            }
        }
    }
