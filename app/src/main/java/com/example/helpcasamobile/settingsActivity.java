package com.example.helpcasamobile;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class settingsActivity extends AppCompatActivity {
    LinearLayout login,home,support,addbtn,settings;


    private EditText currentPasswordEditText, newPasswordEditText, confirmPasswordEditText;
    private Button changePasswordButton,logoutButton;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        login=findViewById(R.id.Login1);
        settings=findViewById(R.id.settingsBtn);
        addbtn=findViewById(R.id.addBtnmain);
        home=findViewById(R.id.homebtn);
        support=findViewById(R.id.supportBtn);

        currentPasswordEditText = findViewById(R.id.currentPasswordEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        changePasswordButton = findViewById(R.id.changePasswordButton);
        logoutButton = findViewById(R.id.logout);
        dbHelper = new DBHelper(this);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(settingsActivity.this,LoginActivity.class));
            }
        });
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(settingsActivity.this,MainActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(settingsActivity.this, LoginActivity.class));
//                Toast.makeText(MainActivity.this,"Test ",Toast.LENGTH_SHORT).show();


            }
        });
        addbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(settingsActivity.this,addActivity.class));
            }
        });

        support.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(settingsActivity.this,supportActivity.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(settingsActivity.this, settingsActivity.class));
            }
        });
    }

    private void changePassword() {
        String currentPassword = currentPasswordEditText.getText().toString().trim();
        String newPassword = newPasswordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(settingsActivity.this, "New password and confirm password do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        String username = getCurrentUsername();

        if (dbHelper.loginChecker(username, currentPassword)) {
            dbHelper.updatePassword(username, newPassword);



            currentPasswordEditText.setText("");
            newPasswordEditText.setText("");
            confirmPasswordEditText.setText("");

            Intent intent = new Intent(settingsActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(settingsActivity.this, "Current password is incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    private String getCurrentUsername() {
        String sharedPrefName = "mySharedPreferences";

        SharedPreferences sharedPreferences = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);

        return sharedPreferences.getString("username", "");
    }
}
