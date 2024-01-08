package com.example.helpcasamobile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    EditText title_input,nbrchambre_input, ville_input, description_input, price_input;
    ConstraintLayout update_button, delete_button;
    LinearLayout login,home,support,addbtn,settings;
    String id, title,nbrchambre, ville, description, price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input = findViewById(R.id.tittleupdate);
        ville_input = findViewById(R.id.villeupdate);
        nbrchambre_input = findViewById(R.id.nbrchambreupdate);
        description_input = findViewById(R.id.descriptionupdate);
        price_input = findViewById(R.id.priceupdate);
        update_button = findViewById(R.id.updateBtn);
        delete_button = findViewById(R.id.deleteBtn);

        login=findViewById(R.id.Login1);
        settings=findViewById(R.id.settingsBtn);
        addbtn=findViewById(R.id.addBtnmain);
        home=findViewById(R.id.homebtn);
        support=findViewById(R.id.supportBtn);

        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                title = title_input.getText().toString().trim();
                nbrchambre = nbrchambre_input.getText().toString().trim();
                ville = ville_input.getText().toString().trim();
                description = description_input.getText().toString().trim();
                price = price_input.getText().toString().trim();
                myDB.updateData(id, title, nbrchambre, ville, description, price);
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateActivity.this,MainActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateActivity.this, LoginActivity.class));
//                Toast.makeText(MainActivity.this,"Test ",Toast.LENGTH_SHORT).show();


            }
        });
        addbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateActivity.this,addActivity.class));
            }
        });

        support.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateActivity.this,supportActivity.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateActivity.this, settingsActivity.class));
            }
        });
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("nbrchambre") &&
                getIntent().hasExtra("ville") && getIntent().hasExtra("description")&& getIntent().hasExtra("price")){
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            nbrchambre = getIntent().getStringExtra("nbrchambre");
            ville = getIntent().getStringExtra("ville");
            description = getIntent().getStringExtra("description");
            price = getIntent().getStringExtra("price");

            title_input.setText(title);
            nbrchambre_input.setText(nbrchambre);
            ville_input.setText(ville);
            description_input.setText(description);
            price_input.setText(price);
            Log.d("id", title+" "+nbrchambre+" "+ville+" "+description+" "+price);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });


        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNeutralButton("Delete all data", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteAllData();
            }
        });
        builder.create().show();
    }

}
