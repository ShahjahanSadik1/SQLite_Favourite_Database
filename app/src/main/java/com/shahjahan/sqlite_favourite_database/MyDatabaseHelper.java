package com.shahjahan.sqlite_favourite_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "users";
    public static final String DB_TABLE_NAME = "users_table";
    public static final int DB_VERSION = 1;
    Context context;



    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        this.context=context;
    }



    @Override
    public void onCreate(SQLiteDatabase  sqLiteDatabase) {


        try {
            Toast.makeText(context, "onCreate is Called", Toast.LENGTH_SHORT).show();
            sqLiteDatabase.execSQL("CREATE TABLE " + DB_TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, number TEXT, isFavourite INTEGER) ");
        }catch (Exception e){

            Toast.makeText(context, "Exception :"+e, Toast.LENGTH_SHORT).show();
        }



    }//>>>>>>>>>>>>>>


    @Override
    public void onUpgrade(SQLiteDatabase  sqLiteDatabase, int oldVersion, int newVersion) {

        try {
            Toast.makeText(context, "onUpgrade is Called", Toast.LENGTH_SHORT).show();
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_NAME);
            onCreate(sqLiteDatabase);

        }catch (Exception e){
            Toast.makeText(context, "Exception :"+e, Toast.LENGTH_SHORT).show();
        }

    }//>>>>>>>>>>>>>>>>>



    //>>>>>>>>>>
    public long insertData(String name, String number ) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("number", number);
        contentValues.put("isFavourite", 0);

        // data insert করা হয়েছে ।
        long rowId = database.insert(DB_TABLE_NAME, null, contentValues);

       return rowId;
    }
    //>>>>>>>>>>>>>



}//Publice Class end here>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.
