package com.shahjahan.sqlite_favourite_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "users";
    public static final String DB_TABLE_NAME = "users_table";
    public static final int DB_VERSION = 1;

    public static final String text = "text";
    public static final String SELECT_ALL = "SELECT * FROM "+DB_TABLE_NAME;
    Context context;



    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        this.context=context;
    }



    //onCreate >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public void onCreate(SQLiteDatabase  sqLiteDatabase) {


        try {
            Toast.makeText(context, "onCreate is Called", Toast.LENGTH_SHORT).show();
            sqLiteDatabase.execSQL("CREATE TABLE " + DB_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, NUMBER TEXT, isFavourite INTEGER) ");
        }catch (Exception e){

            Toast.makeText(context, "Exception :"+e, Toast.LENGTH_SHORT).show();
        }



    }//onCreate end here>>>>>>>>>>>>>>



    //onUpgrade >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public void onUpgrade(SQLiteDatabase  sqLiteDatabase, int oldVersion, int newVersion) {

        try {
            Toast.makeText(context, "onUpgrade is Called", Toast.LENGTH_SHORT).show();
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_NAME);
            onCreate(sqLiteDatabase);

        }catch (Exception e){
            Toast.makeText(context, "Exception :"+e, Toast.LENGTH_SHORT).show();
        }

    }//onUpgrade end here>>>>>>>>>>>>>>>>>



    //insertData >>>>>>>>>>>>>>>>>>>>>>>>>
    public long insertData(String name, String number ) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", name);
        contentValues.put("NUMBER", number);
        contentValues.put("isFavourite", 0);

        // data insert করা হয়েছে ।
        long rowId = database.insert(DB_TABLE_NAME, null, contentValues);

       return rowId;
    } //insertData end here >>>>>>>>>>>>>>>>>>>>>>>>>



    //GetData >>>>>>>>>>>>>>>>>>>>>>>>>
    public Cursor getData(){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(SELECT_ALL,null);

        return cursor;
    }
    //getData>>>>>>>>>>>>>>>>>>>>>>>>>>>>>




    //insertData >>>>>>>>>>>>>>>>>>>>>>>>>
    public boolean Update_Data(String neme, String namber ,String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID",id);
        contentValues.put("NAME", neme);
        contentValues.put("NUMBER", namber);
        contentValues.put("isFavourite", 0);

       database.update(DB_TABLE_NAME,contentValues,"ID = ?",new String[]{id});
       return true;
    } //insertData end here >>>>>>>>>>>>>>>>>>>>>>>>>



    //Delete_Data>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public Integer Delete_Data(String id){

        SQLiteDatabase database = this.getWritableDatabase();

      return   database.delete(DB_TABLE_NAME,"ID = ?",new String[]{id});

    }//Delete Data end here>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>






    public void updateAddFavourite(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int value = 1;
            db.execSQL("UPDATE " + DB_TABLE_NAME + " SET isFavourite ='" + value + "' WHERE id =" + id);
            Toast.makeText(context, "Favourite", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateRemoveFavourite(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int value = 0;
            db.execSQL("UPDATE " + DB_TABLE_NAME + " SET isFavourite ='" + value + "' WHERE id =" + id);
            Toast.makeText(context, "Un Favourite", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
        }
    }

}//Publice Class end here>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.
