package com.shahjahan.sqlite_favourite_database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MyDatabaseHelper myDatabaseHelper;

    TextInputEditText tie_name,tie_age,tie_gander;
    Button insertData_button,displayData_button,update_button,delete_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         tie_name = findViewById(R.id.tie_name);
        tie_age = findViewById(R.id.tie_age);
        tie_gander = findViewById(R.id.tie_gander);
        insertData_button = findViewById(R.id.insertData_button);
        displayData_button = findViewById(R.id.displayData_button);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);





        myDatabaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getReadableDatabase();



        insertData_button.setOnClickListener(this);
        displayData_button.setOnClickListener(this);
        update_button.setOnClickListener(this);
        delete_button.setOnClickListener(this);



    }//Oncreate Mathod end here>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Override
    public void onClick(View view) {


        String  name = tie_name.getText().toString();
        String number = tie_age.getText().toString();
        String id = tie_gander.getText().toString();

        if (view.getId() == R.id.insertData_button){


            if (name.equals("")&& number.equals("")){
                Toast.makeText(this, "Please enter the all data.", Toast.LENGTH_SHORT).show();


            }else {

            long rowID =   myDatabaseHelper.insertData(name,number);

            if (rowID==-1){
                Toast.makeText(getApplicationContext(), "Unseccfull", Toast.LENGTH_SHORT).show() ;
            }else{

                Toast.makeText(getApplicationContext(), "row "+rowID+" seccfull", Toast.LENGTH_SHORT).show();

            }


            }
        }//insertData_button end here
        //insertData_button end here
        //insertData_button end here




        //displayData_button >>>>>>>>>>>>
        ////displayData_button >>>>>>>>>>>>
        // displayData_button >>>>>>>>>>>>
        if (view.getId() == R.id.displayData_button){
         Cursor cursor = myDatabaseHelper.getData();


         if (cursor.getCount()==0){

             showData("Error","No data find");
             return;
         }
         StringBuffer stringBuffer = new StringBuffer();
         while (cursor.moveToNext()){
             stringBuffer.append("Id : "+ cursor.getString(0)+"\n");
             stringBuffer.append("Name : "+ cursor.getString(1)+"\n");
             stringBuffer.append("Number : "+ cursor.getString(2)+"\n\n\n\n");

         }
         showData("ResultSet",stringBuffer.toString());


        }//displayData_button end here>>>>>>>>>>>>
        //displayData_button end here>>>>>>>>>>>>
        //displayData_button end here>>>>>>>>>>>>



        //Update_button >>>>>>>>>>>>
        //Update_button >>>>>>>>>>>>
        //Update_button >>>>>>>>>>>>
        else if (view.getId() == R.id.update_button) {

            try {
                boolean isUpdated = myDatabaseHelper.Update_Data(name,number,id);

                tie_gander.getText().clear();
                tie_name.getText().clear();
                tie_age.getText().clear();



                if (isUpdated) {
                    Toast.makeText(this, "Data is Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Data is Not Updated", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
              showData("t","e");
            }




        }//Update_button end here>>>>>>>>>>>>
        //Update_button end here>>>>>>>>>>>>
        //Update_button end here>>>>>>>>>>>>




        //delete_button >>>>>>>>>>>>>>>>>>>>
        //delete_button >>>>>>>>>>>>>>>>>>>>
        //delete_button >>>>>>>>>>>>>>>>>>>>
        else if (view.getId() == R.id.delete_button) {
            Integer valus = myDatabaseHelper.Delete_Data(id);

            if (valus>0){
                Toast.makeText(this, "Data is deleted", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Data is not deleted", Toast.LENGTH_SHORT).show();
            }
        }//delete_button end here >>>>>>>>>>>>>>>>>>>>
        //delete_button end here >>>>>>>>>>>>>>>>>>>>
        //delete_button end here >>>>>>>>>>>>>>>>>>>>


    }//Button click>>>>>>>>>>>>>>>>>




    //Data Show Dialog>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public void showData(String title, String data){

        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(data);
        builder.setCancelable(true);
        builder.show();
    }


}//Publice class end here>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>