package com.example.helpcasamobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.LinearLayout;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText usernameLogin, passwordLogin;
    ConstraintLayout LoginBtn;

    TextView inscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameLogin=findViewById(R.id.usernameLogin);
        passwordLogin=findViewById(R.id.passwordLogin);
        LoginBtn=findViewById(R.id.connectBtn);
        inscription=findViewById(R.id.newaccountLogin);
//        login=findViewById(R.id.Login1);
//        settings=findViewById(R.id.settingsBtn);
//        addbtn=findViewById(R.id.addBtnmain);
//        home=findViewById(R.id.homebtn);
//        support=findViewById(R.id.supportBtn);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameLogin.getText().toString();
                String password = passwordLogin.getText().toString();

                if (username.length() == 0 || password.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please fill in all details", Toast.LENGTH_SHORT).show();
                } else {
                    DBHelper db = new DBHelper(getApplicationContext());
               if (db.loginChecker(username, password)) {
                   saveUsernameToSharedPreferences(username);
                   savePasswordToSharedPreferences(password);
                   Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);



                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid credentials, please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

    }
    private void saveUsernameToSharedPreferences(String username) {
        String sharedPrefName = "mySharedPreferences";
        SharedPreferences sharedPreferences = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("username", username);

        editor.apply();
    }
    private void savePasswordToSharedPreferences(String password) {
        String sharedPrefName = "mySharedPreferences1";
        SharedPreferences sharedPreferences = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("password", password);

        editor.apply();
    }
}