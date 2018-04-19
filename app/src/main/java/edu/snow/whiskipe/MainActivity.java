package edu.snow.whiskipe;

import android.app.Activity;
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
        private MSSQLManager database;
        private User user;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            database = MSSQLManager.getInstance();

            //Make user
            user = new User();
            Bundle extras = getIntent().getExtras();
            user.setId(extras.getInt("userid"));
            user.setUsername(extras.getString("username"));
            user.setFirstname(extras.getString("userfirstname"));
            user.setLastname(extras.getString("userlastname"));

            //Log.w("MainActivity", "User: " + user);

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
                    insertIntent.putExtra("userid", user.getId());
                    insertIntent.putExtra("username", user.getUsername());
                    insertIntent.putExtra("userfirstname", user.getFirstname());
                    insertIntent.putExtra("userlastname", user.getLastname());
                    this.startActivity(insertIntent);
                    return true;
                case R.id.action_delete:
                    Log.w("MainActivity", "Delete selected");
                    Intent deleteIntent = new Intent(this, DeleteActivity.class);
                    deleteIntent.putExtra("userid", user.getId());
                    deleteIntent.putExtra("username", user.getUsername());
                    deleteIntent.putExtra("userfirstname", user.getFirstname());
                    deleteIntent.putExtra("userlastname", user.getLastname());
                    this.startActivity(deleteIntent);
                    return true;
                case R.id.action_update:
                    Log.w("MainActivity", "Update selected");
                    Intent updateIntent = new Intent(this, UpdateActivity.class);
                    updateIntent.putExtra("userid", user.getId());
                    updateIntent.putExtra("username", user.getUsername());
                    updateIntent.putExtra("userfirstname", user.getFirstname());
                    updateIntent.putExtra("userlastname", user.getLastname());
                    this.startActivity(updateIntent);
                    return true;
                case R.id.action_login:
                    Log.w("MainActivity", "Login selected");
                    Intent loginIntent = new Intent(this, LoginActivity.class);
                    loginIntent.putExtra("userid", user.getId());
                    loginIntent.putExtra("username", user.getUsername());
                    loginIntent.putExtra("userfirstname", user.getFirstname());
                    loginIntent.putExtra("userlastname", user.getLastname());
                    this.startActivity(loginIntent);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }


        }
    }
