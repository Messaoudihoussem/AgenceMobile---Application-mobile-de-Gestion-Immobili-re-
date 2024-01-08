
package com.example.helpcasamobile;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


public class supportActivity extends AppCompatActivity {
    LinearLayout login,home,support,addbtn,settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        login=findViewById(R.id.Login1);
        settings=findViewById(R.id.settingsBtn);
        addbtn=findViewById(R.id.addBtnmain);
        home=findViewById(R.id.homebtn);
        support=findViewById(R.id.supportBtn);



        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(supportActivity.this,MainActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(supportActivity.this, LoginActivity.class));
//                Toast.makeText(MainActivity.this,"Test ",Toast.LENGTH_SHORT).show();


            }
        });
        addbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(supportActivity.this,addActivity.class));
            }
        });

        support.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(supportActivity.this,supportActivity.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(supportActivity.this, settingsActivity.class));
            }
        });
    }
}