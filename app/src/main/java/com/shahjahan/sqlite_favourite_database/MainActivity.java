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
    Button insertData_button,displayData_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         tie_name = findViewById(R.id.tie_name);
        tie_age = findViewById(R.id.tie_age);
        tie_gander = findViewById(R.id.tie_gander);
        insertData_button = findViewById(R.id.insertData_button);
        displayData_button = findViewById(R.id.displayData_button);




        myDatabaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getReadableDatabase();



        insertData_button.setOnClickListener(this);
        displayData_button.setOnClickListener(this);

    }//Oncreate Mathod end here>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Override
    public void onClick(View view) {

        String  name = tie_name.getText().toString();
        String number = tie_age.getText().toString();
        String gander = tie_gander.getText().toString();

        if (view.getId() == R.id.insertData_button){

            long rowID =   myDatabaseHelper.insertData(name,number);

            if (rowID==-1){
                Toast.makeText(getApplicationContext(), "Unseccfull", Toast.LENGTH_SHORT).show() ;
            }else{

                Toast.makeText(getApplicationContext(), "row "+rowID+" seccfull", Toast.LENGTH_SHORT).show();

            }


        }//insertData_button end here




        //displayData_button >>>>>>>>>>>>
        if (view.getId() == R.id.displayData_button){
         Cursor cursor = myDatabaseHelper.getData();


         if (cursor.getCount()==0){

             showData("Error","No data find");
             return;
         }
         StringBuffer stringBuffer = new StringBuffer();
         while (cursor.moveToNext()){
             stringBuffer.append("name : "+ cursor.getString(1)+"\n\n");
             stringBuffer.append("number : "+ cursor.getString(2)+"\n\n");

         }
         showData("ResultSet",stringBuffer.toString());


        }

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