package com.example.helpcasamobile;

import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class FactureActivity extends AppCompatActivity {
    LinearLayout home,support,settings;
    EditText numcheque,commissionAgence;
    Button valide;
    EditText title_input, price_input;
    RadioGroup radioGroup;

    RadioButton radioButtoncheque, radioButtonespece;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facture);

        settings=findViewById(R.id.settingsBtn);
        home=findViewById(R.id.homebtn);
        support=findViewById(R.id.supportBtn);
        commissionAgence = findViewById(R.id.commissionagence);

        numcheque = findViewById(R.id.numcheque);

        radioGroup = findViewById(R.id.radioGroup2);
        radioButtoncheque = findViewById(R.id.radioButton);
        radioButtonespece = findViewById(R.id.radioButton2);

        title_input = findViewById(R.id.tittleupdate);
        price_input = findViewById(R.id.priceupdate);

        Button valideBtnpaiement = findViewById(R.id.validebtnpaiement);




        Intent intent = getIntent();
        if (intent.hasExtra("title") && intent.hasExtra("price")&& intent.hasExtra("house_type")) {
            String title = intent.getStringExtra("title");
            String price = intent.getStringExtra("price");
            String house_type = intent.getStringExtra("house_type");

            title_input.setText(title);
            price_input.setText(price);

            //Update
            if ("à louer".equals(house_type)) {
                calculateAndSetCommission(Double.parseDouble(price));
            } else if ("à vendre".equals(house_type)) {
                calculateAndSetCommission2(Double.parseDouble(price));
            }

        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
        title_input.setEnabled(false);
        price_input.setEnabled(false);

        EditText datePaimentEditText = findViewById(R.id.datepaiment);

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateStr = dateFormat.format(currentDate);

        datePaimentEditText.setText(currentDateStr);


        datePaimentEditText.setEnabled(false);
        datePaimentEditText.setFocusable(false);
        datePaimentEditText.setClickable(false);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton2) { // Assuming radioButton2 is the ID of the 'Espece' radio button
                    numcheque.setEnabled(false);
                    numcheque.setFocusable(false);
                    numcheque.setClickable(false);
                } else {
                    numcheque.setEnabled(true);
                    numcheque.setFocusableInTouchMode(true);
                    numcheque.setClickable(true);
                }
            }
        });
        valideBtnpaiement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);

                String civility = "";
                if (selectedRadioButton != null) {
                    if (selectedRadioButton.getId() == R.id.radioButton) {
                    } else if (selectedRadioButton.getId() == R.id.radioButton2) {
                        numcheque.setEnabled(false);
                    }
                }
                Toast.makeText(FactureActivity.this, "Saved succes", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(FactureActivity.this,MainActivity.class));

            }
        });




        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FactureActivity.this,MainActivity.class));
            }
        });

        support.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FactureActivity.this,supportActivity.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FactureActivity.this, settingsActivity.class));
            }
        });
    }
    private void calculateAndSetCommission(double price) {
        double commission;

        if (price < 300) {
            commission = 0.05 * price;
        } else if (price < 500) {
            commission = 0.10 * price;
        } else if (price < 700) {
            commission = 0.15 * price;
        } else {
            commission = 0.20 * price;
        }

        commissionAgence.setText(String.valueOf(commission));
        commissionAgence.setEnabled(false);

    }
        //Update
    private void calculateAndSetCommission2(double price) {
        double commission;

        if (price < 100000) {
            commission = 0.01 * price;
        } else if (price < 300000) {
            commission = 0.02 * price;
        } else {
            commission = 0.03 * price;
        }

        commissionAgence.setText(String.valueOf(commission));
        commissionAgence.setEnabled(false);
    }


}