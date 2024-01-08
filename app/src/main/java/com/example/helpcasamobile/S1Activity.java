package com.example.helpcasamobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class S1Activity extends AppCompatActivity {
    LinearLayout login,home,support,addbtn,settings;
    RecyclerView recyclerView3;

    ImageView empty_imageview;
    TextView no_data;

    MyDatabaseHelper myDB;
    ArrayList<String> house_id;
    ArrayList<String> house_title;
    ArrayList<String> house_nbrchambre;
    ArrayList<String> house_ville;
    ArrayList<String> house_description;
    ArrayList<String> house_price;
    ArrayList<String> house_type;
    ArrayList<byte[]> house_image;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s1);
        login=findViewById(R.id.Login1);
        settings=findViewById(R.id.settingsBtn);
        addbtn=findViewById(R.id.addBtnmain);
        home=findViewById(R.id.homebtn);
        support=findViewById(R.id.supportBtn);

        recyclerView3 = findViewById(R.id.recyclerView3);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);

        myDB = new MyDatabaseHelper(S1Activity.this);
        house_id = new ArrayList<>();
        house_title = new ArrayList<>();
        house_nbrchambre = new ArrayList<>();
        house_ville = new ArrayList<>();
        house_description = new ArrayList<>();
        house_price = new ArrayList<>();
        house_type = new ArrayList<>();
        house_image = new ArrayList<byte[]>();

        storeDatas1InArrays();

        customAdapter = new CustomAdapter(S1Activity.this,this, house_id, house_title, house_nbrchambre, house_ville,house_description, house_image,house_price,house_type);
        recyclerView3.setAdapter(customAdapter);
        recyclerView3.setLayoutManager(new LinearLayoutManager(S1Activity.this));
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(S1Activity.this,MainActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(S1Activity.this, LoginActivity.class));
//                Toast.makeText(MainActivity.this,"Test ",Toast.LENGTH_SHORT).show();


            }
        });
        addbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(S1Activity.this,addActivity.class));
            }
        });

        support.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(S1Activity.this,supportActivity.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(S1Activity.this, settingsActivity.class));
            }
        });

    }
    void storeDatas1InArrays(){
        Cursor cursor = myDB.readAlldatas1();
        if(cursor.getCount() == 0){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                house_id.add(cursor.getString(0));
                house_title.add(cursor.getString(1));
                house_nbrchambre.add(cursor.getString(2));
                house_ville.add(cursor.getString(3));
                house_description.add(cursor.getString(4));
                house_image.add(cursor.getBlob(5));
                house_price.add(cursor.getString(6));
                house_type.add(cursor.getString(7));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }
}