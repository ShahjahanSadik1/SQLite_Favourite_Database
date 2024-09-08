package com.shahjahan.sqlite_favourite_database;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity  {

    MyDatabaseHelper myDatabaseHelper;


//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    RecyclerView recycler_View;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String>hashMap;

//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




    ImageView favorite_Activity;
    TextInputEditText tie_name,tie_age,tie_gander;
    Button insertData_button,displayData_button,update_button,delete_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler_View = findViewById(R.id.recycler_View);


        favorite_Activity = findViewById(R.id.favorite_Activity);
         tie_name = findViewById(R.id.tie_name);
        tie_age = findViewById(R.id.tie_age);
        tie_gander = findViewById(R.id.tie_gander);
        insertData_button = findViewById(R.id.insertData_button);
        displayData_button = findViewById(R.id.displayData_button);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);






        myDatabaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getReadableDatabase();





        ///favorite_Activity
        favorite_Activity.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,FavouriteActivity.class));
        });





        insertData_button.setOnClickListener(v -> {

            insertData_Data_Mathod();

        });

        displayData_button.setOnClickListener(v -> {

            get_Data_Mathod();

        });

        update_button.setOnClickListener(v -> {

            update_Data_Mathod();

        });

        delete_button.setOnClickListener(v -> {

            Delete_Data_Mathod();

        });

    }// onCreate method end here ==============================>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public class MY_ADAPTER extends RecyclerView.Adapter<MY_ADAPTER.MY_VIEW_HOLDER>{



        public class MY_VIEW_HOLDER extends RecyclerView.ViewHolder{

            //Variable name
            TextView text_id,text_Name,text_Namber;

            ImageView delete_button_im,im_Favourite;

            public MY_VIEW_HOLDER(@NonNull View itemView) {
                super(itemView);

                //porychoy porbo
                text_id = itemView.findViewById(R.id.text_id);
                text_Name = itemView.findViewById(R.id.text_Name);
                text_Namber = itemView.findViewById(R.id.text_Namber);

                delete_button_im = itemView.findViewById(R.id.delete_button_im);
                im_Favourite = itemView.findViewById(R.id.im_Favourite);


            }
        }//View Holder end here>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        @NonNull
        @Override
        public MY_VIEW_HOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
            View view =  layoutInflater.inflate(R.layout.data_display_design_xml,parent,false);


            return new MY_VIEW_HOLDER(view);
        }//>>>>>>>

        @Override
        public void onBindViewHolder(@NonNull MY_VIEW_HOLDER holder, int position) {

            HashMap<String, String> hashMap = arrayList.get(position);
            String id = hashMap.get("textid");
            String namee = hashMap.get("textname");
            String numberr = hashMap.get("textnumber");
            String isFavourite = hashMap.get("isFavourite");

            holder.text_id.setText("ID :"+id);
            holder.text_Name.setText("NAME :"+namee);
            holder.text_Namber .setText("NAMBER :"+numberr);



            // Delete button এর onClick লেখা হয়েছে ।
            holder.delete_button_im.setOnClickListener(v -> {

                    myDatabaseHelper.Delete_Data(id);
                    get_Data_Mathod();
                    notifyDataSetChanged();

            });



            if (isFavourite.equals("0")) {
                holder.im_Favourite.setImageResource(R.drawable.unfavorite_icon);
            } else {
                holder.im_Favourite.setImageResource(R.drawable.favorite_icon);
            }

            // Favourite button এর onClick লেখা হয়েছে ।
            holder.im_Favourite.setOnClickListener(v -> {
                if (isFavourite.equals("0")) {
                    myDatabaseHelper.updateAddFavourite(Integer.parseInt(id));
                    holder.im_Favourite.setImageResource(R.drawable.favorite_icon);
                    get_Data_Mathod();
                    notifyDataSetChanged();
                } else {
                    myDatabaseHelper.updateRemoveFavourite(Integer.parseInt(id));
                    holder.im_Favourite.setImageResource(R.drawable.unfavorite_icon);
                    get_Data_Mathod();
                    notifyDataSetChanged();
                }
            });



        }//onBindViewHolder end here>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        @Override
        public int getItemCount() {
            return arrayList.size();
        }







    }//Adapter end here>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

















//Mathod here>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private void insertData_Data_Mathod() {

        String  name = tie_name.getText().toString();
        String number = tie_age.getText().toString();




        if (name.equals("")&& number.equals("")){
            Toast.makeText(this, "Please enter the all data.", Toast.LENGTH_SHORT).show();


        }else {

            long rowID =   myDatabaseHelper.insertData(name,number);

            if (rowID==-1){
                Toast.makeText(getApplicationContext(), "Unseccfull", Toast.LENGTH_SHORT).show() ;
            }else{

                Toast.makeText(getApplicationContext(), "row "+rowID+" seccfull", Toast.LENGTH_SHORT).show();

                get_Data_Mathod();
            }


        }
        
        
        
        
        
    }//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    public void get_Data_Mathod(){
        arrayList = new ArrayList<>();
        Cursor cursor = myDatabaseHelper.getData();


        if (cursor.getCount()==0){

            Toast.makeText(this, "No data find", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        while (cursor.moveToNext()){
            stringBuffer.append("Id : "+ cursor.getString(0)+"\n");
            stringBuffer.append("Name : "+ cursor.getString(1)+"\n");
            stringBuffer.append("Number : "+ cursor.getString(2)+"\n\n\n\n");
            stringBuffer.append("IsFavourite : "+ cursor.getString(3)+"\n\n\n\n");


            int idd = cursor.getInt(0);
            String namee = cursor.getString(1);
            String numbere = cursor.getString(2);
            int isFavourite = cursor.getInt(3);




            String iid = Integer.toString(idd);


            hashMap = new HashMap<>();
            hashMap.put("textid", iid);
            hashMap.put("textname", namee);
            hashMap.put("textnumber", numbere);
            hashMap.put("isFavourite", "" + isFavourite);
            arrayList.add(hashMap);

        }





        MY_ADAPTER my_adapter = new MY_ADAPTER();

        recycler_View.setAdapter(my_adapter);
        recycler_View.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false));
        recycler_View.setFocusable(false);
        recycler_View.setNestedScrollingEnabled(false);

    }//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










    private void update_Data_Mathod() {
        String  name = tie_name.getText().toString();
        String number = tie_age.getText().toString();
        String id = tie_gander.getText().toString();

        try {
            boolean isUpdated = myDatabaseHelper.Update_Data(name,number,id);

            tie_gander.getText().clear();
            tie_name.getText().clear();
            tie_age.getText().clear();


            if (isUpdated) {
                Toast.makeText(this, "Data is Updated", Toast.LENGTH_SHORT).show();
                get_Data_Mathod();
            } else {
                Toast.makeText(this, "Data is Not Updated", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }
    }//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



    private void Delete_Data_Mathod() {
        String id = tie_gander.getText().toString();

        Integer valus = myDatabaseHelper.Delete_Data(id);

        if (valus>0){
            Toast.makeText(this, "Data is deleted", Toast.LENGTH_SHORT).show();

            get_Data_Mathod();

        }else {
            Toast.makeText(this, "Data is not deleted", Toast.LENGTH_SHORT).show();
        }
    }//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>





    @Override
    protected void onResume() {
        get_Data_Mathod();
        super.onResume();

    }


}//Publice class end here>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>