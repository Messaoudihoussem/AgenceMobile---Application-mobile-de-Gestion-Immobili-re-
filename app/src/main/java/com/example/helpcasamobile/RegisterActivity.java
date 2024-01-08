package com.example.helpcasamobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.text.TextUtils;
import android.widget.LinearLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText usernameRegister,emailRegister,passwordRegister,confirmpasswordRegister,adminsection;
    ConstraintLayout registerBtn;
//    LinearLayout login,home,support,addbtn,settings;
    TextView existaccountRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        usernameRegister = findViewById(R.id.usernameRegister);
        emailRegister=findViewById(R.id.emailRegister);
        passwordRegister = findViewById(R.id.passwordRegister);
        confirmpasswordRegister = findViewById(R.id.confirmpasswordRegister);
        adminsection = findViewById(R.id.adminsection);
        registerBtn=findViewById(R.id.registerBtn);
        existaccountRegister = findViewById(R.id.existaccountRegister);
//        login=findViewById(R.id.Login1);
//        settings=findViewById(R.id.settingsBtn);
//        addbtn=findViewById(R.id.addBtnmain);
//        home=findViewById(R.id.homebtn);
//        support=findViewById(R.id.supportBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameRegister.getText().toString().trim();
                String email = emailRegister.getText().toString().trim();
                String password = passwordRegister.getText().toString().trim();
                String confirmPassword = confirmpasswordRegister.getText().toString().trim();
                String admincode = adminsection.getText().toString().trim();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(RegisterActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 8) {
                    Toast.makeText(RegisterActivity.this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    DBHelper db = new DBHelper(RegisterActivity.this);
                    db.adduser(username, email, password,admincode);

                    usernameRegister.setText("");
                    emailRegister.setText("");
                    passwordRegister.setText("");
                    confirmpasswordRegister.setText("");
                    adminsection.setText("");
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }

            private boolean isValidEmail(String email) {    // Simple email validation using a basic pattern
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                return email.matches(emailPattern);
            }
        });





        existaccountRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
// barre Menu
//        home.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
//            }
//        });
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
////                Toast.makeText(MainActivity.this,"Test ",Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
//        addbtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(RegisterActivity.this,addActivity.class));
//            }
//        });
//
//        support.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(RegisterActivity.this,supportActivity.class));
//            }
//        });
//
//        settings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(RegisterActivity.this, settingsActivity.class));
//            }
//        });
    }


}