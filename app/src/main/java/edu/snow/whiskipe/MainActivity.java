package edu.snow.whiskipe;

import android.os.AsyncTask;
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

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

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

            new MakeConnection().execute();
        }

        protected void onResume(){
            super.onResume();

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
                case R.id.action_login:
                    Log.w("MainActivity", "Login selected");
                    Intent loginIntent = new Intent(this, LoginActivity.class);
                    this.startActivity(loginIntent);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }


        }


        private class MakeConnection extends AsyncTask {

            @Override
            protected Object doInBackground(Object[] objects) {
                MSSQLManager manager = MSSQLManager.getInstance();
                if(manager != null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Connected!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Not Connected :(", Toast.LENGTH_LONG).show();
                        }
                    });
                }

               /* User user = new User(2, "katelynpeterson", "katelyn", "peterson");
                final ArrayList<Item> items = manager.getItems(user);

                if(items == null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "CRAP", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                for(int i = 0; i < items.size(); i++){
                    Log.w("MainActivity", i + ": " + items.get(i).getName());
                }

                *//*try {
                    Log.w("MainActivity", "FLAG: " + manager.foo());
                } catch (SQLException e) {
                    e.printStackTrace();
                }*/

                User user1 = new User("alexthayn", "alex", "thayn");
                user1.setId(3);

                ArrayList<Item> items = manager.getItems(user1);

                for(int i = 0; i < items.size(); i++){
                    manager.deleteItem(items.get(i));
                    i++;
                }

                return null;
            }
        }
    }
