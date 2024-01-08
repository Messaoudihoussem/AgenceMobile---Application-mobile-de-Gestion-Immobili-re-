package com.example.helpcasamobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LinearLayout login,home,support,addbtn,settings;

    ConstraintLayout s1btn,s2btn,s3btn,villabtn;

    RecyclerView recyclerView1;


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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=findViewById(R.id.Login1);
        settings=findViewById(R.id.settingsBtn);
        addbtn=findViewById(R.id.addBtnmain);
        home=findViewById(R.id.homebtn);
        support=findViewById(R.id.supportBtn);

        s1btn=findViewById(R.id.s1btn);

        recyclerView1 = findViewById(R.id.recyclerView1);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);

        myDB = new MyDatabaseHelper(MainActivity.this);
        house_id = new ArrayList<>();
        house_title = new ArrayList<>();
        house_nbrchambre = new ArrayList<>();
        house_ville = new ArrayList<>();
        house_description = new ArrayList<>();
        house_price = new ArrayList<>();
        house_type = new ArrayList<>();
        house_image = new ArrayList<byte[]>();

        storeDataInArrays();




        Button button1=findViewById(R.id.tunisBtn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, TunisActivity.class));
            }
        });


        Button button2=findViewById(R.id.nabeulBtn);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, NabeulActivity.class));
            }
        });

        s1btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,S1Activity.class));
            }
        });
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Already logged in", Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this,"Test ",Toast.LENGTH_SHORT).show();

            }
        });



        addbtn.setOnClickListener(new View.OnClickListener() {
            DBHelper myDB = new DBHelper(MainActivity.this);

            @Override
            public void onClick(View view) {
                String enteredUsername = getCurrentUsername();
                String enteredPassword = getCurrentPassword();

                // Check if the entered user is an admin
                if ("12345678".equals(myDB.getAdminStatus(enteredUsername, enteredPassword))) {
                    // The entered user is an admin, allow access to addActivity
                    startActivity(new Intent(MainActivity.this, addActivity.class));
                } else {
                    Toast.makeText(MainActivity.this, "Only admins can access this feature", Toast.LENGTH_SHORT).show();
                }
            }
        });



        support.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,supportActivity.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, settingsActivity.class));
            }
        });

        customAdapter = new CustomAdapter(MainActivity.this,this, house_id, house_title, house_nbrchambre, house_ville,house_description, house_image,house_price, house_type);
        recyclerView1.setAdapter(customAdapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    private String getCurrentUsername() {
        String sharedPrefName = "mySharedPreferences";

        SharedPreferences sharedPreferences = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);

        return sharedPreferences.getString("username", "");
    }

    private String getCurrentPassword() {
        String sharedPrefName = "mySharedPreferences1";

        SharedPreferences sharedPreferences = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);

        return sharedPreferences.getString("password", "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
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

//

}