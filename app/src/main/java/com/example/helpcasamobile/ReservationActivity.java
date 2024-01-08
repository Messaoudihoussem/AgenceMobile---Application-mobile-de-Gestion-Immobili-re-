package com.example.helpcasamobile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ReservationActivity extends AppCompatActivity {


    String id, title, price,house_type;

    EditText usernameClient, nomClient, prenomClient, adresseClient, villeClient, codePostalClient, emailClient, telMobileClient, telFixeClient;
    EditText title_input, price_input;
    RadioGroup radioGroup;
    LinearLayout home,support,settings;
    RadioButton radioButtonMr, radioButtonMme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);


        settings=findViewById(R.id.settingsBtn);
        home=findViewById(R.id.homebtn);
        support=findViewById(R.id.supportBtn);

        usernameClient = findViewById(R.id.usernameclient);
        nomClient = findViewById(R.id.nomclient);
        prenomClient = findViewById(R.id.prenomclient);
        adresseClient = findViewById(R.id.adresseclient);
        villeClient = findViewById(R.id.villeclient);
        codePostalClient = findViewById(R.id.codepostalclient);
        emailClient = findViewById(R.id.emailclient);
        telMobileClient = findViewById(R.id.telmobileclient);
        telFixeClient = findViewById(R.id.telfixeclient);

        radioGroup = findViewById(R.id.radioGroup2);
        radioButtonMr = findViewById(R.id.radioButton);
        radioButtonMme = findViewById(R.id.radioButton2);

        title_input = findViewById(R.id.tittleupdate);
        price_input = findViewById(R.id.priceupdate);

        Button valideBtnClient = findViewById(R.id.validebtnclient);

        SharedPreferences sharedPreferences = getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("username", "");

        if (savedUsername != null && !savedUsername.isEmpty()) {
            usernameClient.setText(savedUsername);
            usernameClient.setEnabled(false); // Make it non-editable
        } else {
            // Handle the case where the savedUsername is not available
            Toast.makeText(this, "Username not found in SharedPreferences", Toast.LENGTH_SHORT).show();
        }
        title_input.setEnabled(false);
        price_input.setEnabled(false);


        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReservationActivity.this,MainActivity.class));
            }
        });

        support.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReservationActivity.this,supportActivity.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReservationActivity.this, settingsActivity.class));
            }
        });
        valideBtnClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);

                String civility = "";
                if (selectedRadioButton != null) {
                    if (selectedRadioButton.getId() == R.id.radioButton) {
                        civility = "Mr";
                    } else if (selectedRadioButton.getId() == R.id.radioButton2) {
                        civility = "Mme";
                    }
                }

                String username = usernameClient.getText().toString().trim();
                String nom = nomClient.getText().toString().trim();
                String prenom = prenomClient.getText().toString().trim();
                String adresse = adresseClient.getText().toString().trim();
                String ville = villeClient.getText().toString().trim();
                String codePostal = codePostalClient.getText().toString().trim();
                String email = emailClient.getText().toString().trim();
                String telMobile = telMobileClient.getText().toString().trim();
                String telFixe = telFixeClient.getText().toString().trim();

                if (TextUtils.isEmpty(nom) || TextUtils.isEmpty(prenom) ||
                        TextUtils.isEmpty(adresse) || TextUtils.isEmpty(ville) || TextUtils.isEmpty(codePostal) ||
                        TextUtils.isEmpty(email) || TextUtils.isEmpty(telMobile)) {
                    Toast.makeText(ReservationActivity.this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidEmail(email)) {
                    Toast.makeText(ReservationActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                    return;
                }

                DBHelper dbHelper = new DBHelper(ReservationActivity.this);
                dbHelper.addClient(username, civility, nom, prenom, adresse, ville, codePostal, telMobile, telFixe, email);

                nomClient.setText("");
                prenomClient.setText("");
                adresseClient.setText("");
                villeClient.setText("");
                codePostalClient.setText("");
                emailClient.setText("");
                telMobileClient.setText("");
                telFixeClient.setText("");

                Intent factureIntent = new Intent(ReservationActivity.this, FactureActivity.class);
                factureIntent.putExtra("title", title);
                factureIntent.putExtra("price", price);
                factureIntent.putExtra("house_type", house_type);
                startActivity(factureIntent);


            }
        });

        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    private void saveUsernameToSharedPreferences(String username) {
        String sharedPrefName = "mySharedPreferences";
        SharedPreferences sharedPreferences = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("username", username);

        editor.apply();
    }

void getAndSetIntentData() {
    if (getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("price")&& getIntent().hasExtra("house_type")) {
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        price = getIntent().getStringExtra("price");
        house_type = getIntent().getStringExtra("house_type");

        title_input.setText(title);
        price_input.setText(price);

        Log.d("house_type", house_type);

        Log.d("id", title + " " + price);
        Toast.makeText(this, house_type, Toast.LENGTH_SHORT).show();

    } else {
        Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
    }
}


}
