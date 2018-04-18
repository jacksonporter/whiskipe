package edu.snow.whiskipe;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by katel on 3/24/2018.
 */

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "foodDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FOOD = "food";
    private static final String TABLE_USER = "user";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String QTY = "qty";

    public DatabaseManager(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        String sqlCreate = "create table " + TABLE_FOOD + "("+ ID;
        sqlCreate += " integer primary key autoincrement, " + NAME;
        sqlCreate += " text, " + QTY + " real )";

        db.execSQL(sqlCreate);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists " + TABLE_FOOD);
        onCreate(db);
    }

    public void insert(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlInsert = "insert into " + TABLE_FOOD;
        sqlInsert += " values( null, '" + item.getName();
        sqlInsert += "', '" + item.getQty() + "' )";
        db.execSQL(sqlInsert);
        db.close();
    }

    public void login(User user){

    }

    public void deleteById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlDelete = "delete from " + TABLE_FOOD;
        sqlDelete += " where " + ID + " = " + id;

        db.execSQL(sqlDelete);
        db.close();
    }

    public void updateById(int id, String name, double qty){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlUpdate = "update " + TABLE_FOOD;
        sqlUpdate += " set " + NAME + " = '" + name + "', ";
        sqlUpdate += QTY + " = '" + qty + "'";
        sqlUpdate += " where " + ID + " = " + id;

        db.execSQL(sqlUpdate);
        db.close();
    }

    public ArrayList<Item>selectAll(){
        String sqlQuery = "select * from " + TABLE_FOOD;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        ArrayList<Item> itemList = new ArrayList<Item>();
        while (cursor.moveToNext()){
            Item currentItem = new Item(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getDouble(2), cursor.getString(3));
            itemList.add(currentItem);
        }
        db.close();
        return itemList;
    }

    public Item selectById(int id){
        String sqlQuery = "select * from " + TABLE_FOOD;
        sqlQuery += " where " + ID + " = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        Item item = null;
        if(cursor.moveToFirst())
            item = new Item(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getDouble(2),cursor.getString(3));
        return item;
    }
}

