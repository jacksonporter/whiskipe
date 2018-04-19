package edu.snow.whiskipe;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MSSQLConnection {
    private Connection conn;
    private String url, database, dbusername, dbpassword;

    public MSSQLConnection(String url, String database, String dbusername, String dbpassword){
        this.url = url;
        this.database = database;
        this.dbusername = dbusername;
        this.dbpassword = dbpassword;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String connectionURL = "";
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver://" + url + ";databaseName=" + database + ";" +
                    "user=" + dbusername + ";password=" + dbpassword + ";";
            conn = DriverManager.getConnection(connectionURL);
        }catch (SQLException e) {
            e.printStackTrace();
            Log.w("ConnectionHelper", "SQL Exception hit");
            conn = null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.w("ConnectionHelper", "ClassNotFound Exception hit");
            conn = null;
        }
    }

    public Connection getConnection(){
        if(conn != null){
            return this.conn;
        }
        else{
            return null;
        }
    }

    public boolean connectionActive(){
        if(conn != null){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean executeSQL(String execute) throws java.sql.SQLException {
        Statement stmt = conn.createStatement();
        boolean toReturn = stmt.execute(execute);

        //stmt.close();
        return toReturn;
    }

    public int executeSQLGettingID(String execute) throws java.sql.SQLException {
        Log.w("MSSQLConnection", "Attempting to execute sql getting id");
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery(execute);
        Log.w("MSSQLConnection", "ResultSet made.");

        int id = -1;
        while(results.next()) {
            String idString = results.getString(1);
            id = Integer.parseInt(idString);
        }

        Log.w("MSSQLConnection", "Id is: " + id);

        //stmt.close();
        return id;
    }

    public ResultSet executeQuery(String query) throws java.sql.SQLException {
        Statement stmt = conn.createStatement();
        Log.w("MSSQLConnection", "Statement made.");
        ResultSet reset = stmt.executeQuery(query);
        Log.w("MSSQLConnection", "ResultSet made.");

        //reset.close();
        //stmt.close();

        return reset;
    }

    public String foo() throws java.sql.SQLException{
        Statement stmt = conn.createStatement();
        ResultSet reset = stmt.executeQuery("select * from users;");

        String result = "";

        while(reset.next()) {
            try {
                result += reset.getString(1) + reset.getString(2) + reset.getString(3) + reset.getString(4) + reset.getString(5) + "\n";
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        reset.close();
        stmt.close();

        return result;
    }


}
