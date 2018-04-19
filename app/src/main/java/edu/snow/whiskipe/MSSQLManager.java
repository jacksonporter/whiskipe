package edu.snow.whiskipe;

import android.content.Context;
import android.util.Log;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MSSQLManager {
    //Singleton
    private static MSSQLManager manager;

    //Class variables
    private static final String DATABASE_LOCATION = "whiskipedb.ck9qmzz9yyn6.us-west-2.rds.amazonaws.com";
    private static final String DATABASE_USERNAME = "whiskipeadmin";
    private static final String DATABASE_PASSWORD = "whiskipeadmin";
    private static final String DATABASE_NAME = "whiskipe";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_ITEMS = "items";

    //Instance variables
    private MSSQLConnection conn;

    public MSSQLManager(){
        conn = new MSSQLConnection(MSSQLManager.DATABASE_LOCATION, MSSQLManager.DATABASE_NAME, MSSQLManager.DATABASE_USERNAME, MSSQLManager.DATABASE_PASSWORD);
        if(conn == null){
            Log.w("MSSQLManager", "Connection is not active.");
        }
        else{
            Log.w("MSSQLManager", "Connection is active.");
        }
    }

    public static synchronized MSSQLManager getInstance(){
        if(MSSQLManager.manager == null){
            MSSQLManager.manager = new MSSQLManager();
        }
        return MSSQLManager.manager;
    }

    public boolean insertItem(Item item, User user){
        String toExecute = "INSERT INTO " + MSSQLManager.TABLE_ITEMS
                + " ("
                + MSSQLManager.TABLE_ITEMS + ".name,"
                + MSSQLManager.TABLE_ITEMS + ".quantity,"
                + MSSQLManager.TABLE_ITEMS + ".size,"
                + MSSQLManager.TABLE_ITEMS + ".userid"
                + ") OUTPUT INSERTED.ID VALUES ("
                + "'" + item.getName()
                + "'," + item.getQty()
                + "," + item.getSize()
                + "," + user.getId()
                + ");";

        Log.w("MSSQLManager", "Insert item execution: " + toExecute);

        try {
            int itemid = conn.executeSQLGettingID(toExecute);
            Log.w("MSSQLManager", "Got item id");

            if(itemid < 0){
                return false;
            }
            else{
                item.setId(itemid);
                return true;
            }
        } catch (SQLException e) {
            Log.w("MSSQLManager", "Error in inserting item.");
            return false;
        }
    }

    public boolean deleteItem(Item item){
        String toExecute = "DELETE FROM " + MSSQLManager.TABLE_ITEMS
                + " WHERE " + MSSQLManager.TABLE_ITEMS
                + ".ID = " + item.getId()
                + ";";
        try {
            return conn.executeSQL(toExecute);
        } catch (SQLException e) {
            Log.w("MSSQLManager", "Error in setting user to inactive.");
            return false;
        }
    }

    public boolean insertUser(User user){
        String toExecute = "INSERT INTO " + MSSQLManager.TABLE_USERS
                + " (username, firstname, lastname, active) OUTPUT INSERTED.ID VALUES ("
                + "'" + user.getUsername()
                + "','" + user.getFirstname()
                + "','" + user.getLastname()
                + "', 1);";

        try {
            int userid = conn.executeSQLGettingID(toExecute);

            if(userid < 0){
                return false;
            }
            else {
                user.setId(userid);
                return true;
            }
        } catch (SQLException e) {
            Log.w("MSSQLManager", "Error in inserting user.");
            return false;
        }
    }

    public boolean deleteUser(User user){ //sets user to inactive on database
        String toExecute = "UPDATE " + MSSQLManager.TABLE_USERS
                + " SET " + MSSQLManager.TABLE_USERS
                + ".active = 0 WHERE " + MSSQLManager.TABLE_USERS
                + ".ID = " + user.getId() + ";";

        Log.w("MSSQLManager", "Delete user execute: " + toExecute);

        try {
            return conn.executeSQL(toExecute);
        } catch (SQLException e) {
            Log.w("MSSQLManager", "Error in setting user to inactive.");
            return false;
        }
    }

    public ArrayList<Item> getItems(User user){
        ArrayList<Item> items = new ArrayList<Item>(); //Make an empty arraylist of items to return

        String query = "SELECT " + MSSQLManager.TABLE_ITEMS + ".ID AS 'ID',"
                + MSSQLManager.TABLE_USERS + ".username AS 'Username',"
                + MSSQLManager.TABLE_ITEMS + ".name AS 'Item Name',"
                + MSSQLManager.TABLE_ITEMS + ".quantity AS 'Quantity',"
                + MSSQLManager.TABLE_ITEMS + ".size AS 'Size (Weight in oz.)'"
                + " FROM " + MSSQLManager.TABLE_USERS + " INNER JOIN " + MSSQLManager.TABLE_ITEMS
                + " ON " + MSSQLManager.TABLE_USERS + ".ID = " + MSSQLManager.TABLE_ITEMS + ".userid "
        + " WHERE " + MSSQLManager.TABLE_USERS + ".ID = " + user.getId() + ";"; //Query to get user items

        Log.w("MSSQLManager", "TAG: " + query);

        try {
            ResultSet results = conn.executeQuery(query); //get results from query

            while(results.next()){ //Go until all of the rows have been read in
                Item tempItem = new Item(); //create a empty item

                tempItem.setId(Integer.parseInt(results.getString(1))); //get id
                tempItem.setName(results.getString(3)); //get name
                tempItem.setQty(Integer.parseInt(results.getString(4))); //get quantity
                tempItem.setSize(Double.parseDouble(results.getString(5))); //get size

                items.add(tempItem);
            }

            results.close();
        } catch (SQLException e) {
            Log.w("MSSQLManager", "Error in getting user items.");
            return null;
        }

        return items;
    }

    public String foo() throws java.sql.SQLException{
        return conn.foo();
    }

    public User userExists(String username){
        String query = "SELECT " + MSSQLManager.TABLE_USERS + ".ID AS 'ID',"
                + MSSQLManager.TABLE_USERS + ".firstname AS 'First Name',"
                + MSSQLManager.TABLE_USERS + ".lastname AS 'Last Name'"
                + " FROM " + MSSQLManager.TABLE_USERS
                + " WHERE " + MSSQLManager.TABLE_USERS + ".username ='" + username + "' AND " + MSSQLManager.TABLE_USERS + ".active = 1;"; //Query to get user items

        Log.w("MSSQLManager", "userexists query: " + query);

        try {
            ResultSet results = conn.executeQuery(query); //get results from query

            String firstname = "EMPTYFIRST";
            String lastname = "EMPTYLAST";
            int id = -1;

            while(results.next()){ //Go until all of the rows have been read in
                firstname = results.getString(2);
                lastname = results.getString(3);
                id = results.getInt(1);
            }

            if(id == -1){
                return null;
            }

            User user = new User(id, username, firstname, lastname);

            results.close();

            return user;
        } catch (SQLException e) {
            Log.w("MSSQLManager", "Error in checkin' if user is there.");
            return null;
        }
    }
}
