package edu.snow.whiskipe;

import android.content.Context;

import java.sql.Connection;
import java.util.ArrayList;

public class MSSQLManager {
    //Class variables
    private static final String DATABASE_LOCATION = "whiskipedb.ck9qmzz9yyn6.us-west-2.rds.amazonaws.com";
    private static final String DATABASE_USERNAME = "whiskipeadmin";
    private static final String DATABASE_PASSWORD = "whiskipeadmin";
    private static final String DATABASE_NAME = "whiskipe";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_ITEMS = "items";

    //Instance variables
    private MSSQLConnection conn;

    public MSSQLManager(Context context){
        conn = new MSSQLConnection(MSSQLManager.DATABASE_LOCATION, MSSQLManager.DATABASE_NAME, MSSQLManager.DATABASE_USERNAME, MSSQLManager.DATABASE_PASSWORD);
    }

    public void insertItem(Item item){

    }

    public void insertUser(User user){

    }

    public void deleteUser(User user){

    }

    public ArrayList<Item> getItems(User user){

    }
}
