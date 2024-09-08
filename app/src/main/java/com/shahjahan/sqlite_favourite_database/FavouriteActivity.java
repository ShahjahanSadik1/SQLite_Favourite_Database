package com.shahjahan.sqlite_favourite_database;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class FavouriteActivity extends AppCompatActivity {

    MyDatabaseHelper myDatabaseHelper;
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    RecyclerView recycler_View;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String>hashMap;
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        recycler_View = findViewById(R.id.recycler_View);


        // পরিচয় করিয়ে দেওয়া হয়েছে ।
        myDatabaseHelper = new MyDatabaseHelper(FavouriteActivity.this);
        get_Data_Mathod();







        MY_FAV_ADAPTER my_adapter = new MY_FAV_ADAPTER();
        recycler_View.setAdapter(my_adapter);


    }// onCreate method end here ==============================>>>>>>>>>>>>>>>>>>>>>>

    public class MY_FAV_ADAPTER extends RecyclerView.Adapter<MY_FAV_ADAPTER.MY_FAV_VIEW_HOLDER>{



        public class MY_FAV_VIEW_HOLDER extends RecyclerView.ViewHolder{


            //Variable name
            TextView text_id,text_Name,text_Namber;

            ImageView delete_button_im,im_Favourite;


            CardView card_view;
            public MY_FAV_VIEW_HOLDER(@NonNull View itemView) {
                super(itemView);


                //porychoy porbo
                text_id = itemView.findViewById(R.id.text_id);
                text_Name = itemView.findViewById(R.id.text_Name);
                text_Namber = itemView.findViewById(R.id.text_Namber);

                delete_button_im = itemView.findViewById(R.id.delete_button_im);
                im_Favourite = itemView.findViewById(R.id.im_Favourite);
                card_view = itemView.findViewById(R.id.card_view);



            }
        }//MY_FAV_VIEW_HOLDER end here ==============================>>>>>>>>>>>>>>>>>>>>>>





        @NonNull
        @Override
        public MY_FAV_VIEW_HOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(FavouriteActivity.this);
            View view =  layoutInflater.inflate(R.layout.data_display_design_xml,parent,false);


            return new MY_FAV_VIEW_HOLDER(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MY_FAV_VIEW_HOLDER holder, int position) {


            HashMap<String, String> hashMap = arrayList.get(position);
            String id = hashMap.get("textid");
            String namee = hashMap.get("textname");
            String numberr = hashMap.get("textnumber");
            String isFavourite = hashMap.get("isFavourite");

            holder.text_id.setText("ID :"+id);
            holder.text_Name.setText("NAME :"+namee);
            holder.text_Namber .setText("NAMBER :"+numberr);

            holder.delete_button_im.setVisibility(View.GONE);



            if (isFavourite.equals("0")) {
                holder.im_Favourite.setImageResource(R.drawable.unfavorite_icon);

            } else {
                holder.im_Favourite.setImageResource(R.drawable.favorite_icon);
            }

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


            // Delete button এর onClick লেখা হয়েছে ।
            holder.delete_button_im.setOnClickListener(v -> {

                myDatabaseHelper.Delete_Data(id);
                get_Data_Mathod();
                notifyDataSetChanged();

            });

        }//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        @Override
        public int getItemCount() {
            return arrayList.size();
        }







    }//MY_FAV_ADAPTER end here ==============================>>>>>>>>>>>>>>>>>>>>>>












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


            if (isFavourite == 1) {
                hashMap = new HashMap<>();
                hashMap.put("textid", iid);
                hashMap.put("textname", namee);
                hashMap.put("textnumber", numbere);
                hashMap.put("isFavourite", "" + isFavourite);
                arrayList.add(hashMap);
            }

        }





        MY_FAV_ADAPTER my_adapter = new MY_FAV_ADAPTER();

        recycler_View.setAdapter(my_adapter);
        recycler_View.setLayoutManager(new LinearLayoutManager(FavouriteActivity.this,RecyclerView.VERTICAL,false));
        recycler_View.setFocusable(false);
        recycler_View.setNestedScrollingEnabled(false);

    }//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}//// public class end here ==============================>>>>>>>>>>>>>>>>>>>>>>