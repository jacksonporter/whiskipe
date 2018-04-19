package edu.snow.whiskipe;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by katel on 3/24/2018.
 */

public class DeleteActivity extends AppCompatActivity {
    private MSSQLManager database;
    private User user;
    private ArrayList<Item> items;
    private Item toDelete;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        database = MSSQLManager.getInstance();

        user = new User();
        Bundle extras = getIntent().getExtras();
        user.setId(extras.getInt("userid"));
        user.setUsername(extras.getString("username"));
        user.setFirstname(extras.getString("userfirstname"));
        user.setLastname(extras.getString("userlastname"));

        toDelete = null;

        new GetItems().execute();
    }

    public void updateView(){
        RelativeLayout layout = new RelativeLayout(this);
        ScrollView scrollView = new ScrollView(this);
        RadioGroup group = new RadioGroup(this);
        for (Item item : items){
            RadioButton rb = new RadioButton(this);
            rb.setId(item.getId());
            rb.setText(item.toString());
            group.addView(rb);
        }

        RadioButtonHandler rbh = new RadioButtonHandler();
        group.setOnCheckedChangeListener(rbh);

        Button backButton = new Button(this);
        backButton.setText(R.string.button_back);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteActivity.this.finish();
            }
        });

        scrollView.addView(group);
        layout.addView(scrollView);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.setMargins(0,0,0,50);
        layout.addView(backButton,params);

        setContentView(layout);
    }

    private class RadioButtonHandler implements RadioGroup.OnCheckedChangeListener{
        public void onCheckedChanged(RadioGroup group, int checkedId){
            toDelete = null;
            for(int i = 0; i < items.size(); i++){
                if(items.get(i).getId() == checkedId){
                    toDelete = items.get(i);
                    new DeleteItem().execute();
                }
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

    private class DeleteItem extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {

            final boolean worked = database.deleteItem(toDelete);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(worked){
                        Toast.makeText(DeleteActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
                        new GetItems().execute();
                    }
                    else{
                        Toast.makeText(DeleteActivity.this, "Item couldn't be deleted.", Toast.LENGTH_SHORT).show();
                        new GetItems().execute();
                    }
                }
            });


            return null;
        }
    }
}
