package com.example.helpcasamobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class addActivity extends AppCompatActivity {
    LinearLayout login,home,support,addbtn,settings;
    EditText title_input,nbrchambre_input, ville_input,price_input,description_input;
    ConstraintLayout add;
    ImageView img;
    static byte[] imageContent;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);


        if (requestCode == 100 && resultCode == RESULT_OK) {
            Uri imageUri = intent.getData();
           try {
               InputStream inputStream = getContentResolver().openInputStream(imageUri);
               Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
               img.setImageBitmap(bitmap);

               imageContent = getBytes(bitmap);
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           }

        }
    }

    public byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,stream);
        return stream.toByteArray();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        login=findViewById(R.id.Login1);
        settings=findViewById(R.id.settingsBtn);
        addbtn=findViewById(R.id.addBtnmain);
        home=findViewById(R.id.homebtn);
        support=findViewById(R.id.supportBtn);
        add=findViewById(R.id.addBtn);

        img = findViewById(R.id.house_img);
        title_input=findViewById(R.id.tittleadd);
        ville_input=findViewById(R.id.villeadd);
        nbrchambre_input=findViewById(R.id.nbrchambre);
        price_input=findViewById(R.id.priceadd);
        description_input=findViewById(R.id.descriptionadd);


        img.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            Intent intent =new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

            startActivityForResult(intent,100);
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(addActivity.this);

                if (imageContent != null) {
                    String title = title_input.getText().toString().trim();
                    String nbrchambre = nbrchambre_input.getText().toString().trim();
                    String ville = ville_input.getText().toString().trim();
                    String description = description_input.getText().toString().trim();
                    int price = Integer.valueOf(price_input.getText().toString().trim());

                    RadioGroup radioGroup = findViewById(R.id.radioGroup);
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    RadioButton selectedRadioButton = findViewById(selectedId);

                    String vendreType = "";
                    if (selectedRadioButton != null) {
                        if (selectedRadioButton.getId() == R.id.louerradioBtn) {
                            vendreType = "à louer";
                        } else if (selectedRadioButton.getId() == R.id.vendreradioBtn) {
                            vendreType = "à vendre";
                        }
                    }

                    myDB.addHouse(title, nbrchambre, ville, description, price, imageContent, vendreType);

                    title_input.setText("");
                    nbrchambre_input.setText("");
                    ville_input.setText("");
                    description_input.setText("");
                    price_input.setText("");
                    img.setImageDrawable(null);
                }
            }
        });


        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(addActivity.this,MainActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(addActivity.this, LoginActivity.class));
//                Toast.makeText(MainActivity.this,"Test ",Toast.LENGTH_SHORT).show();

            }
        });
        addbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(addActivity.this,addActivity.class));
            }
        });

        support.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(addActivity.this,supportActivity.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(addActivity.this, settingsActivity.class));
            }
        });
    }
}