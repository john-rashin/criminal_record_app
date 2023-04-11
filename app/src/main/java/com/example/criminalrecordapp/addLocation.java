package com.example.criminalrecordapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addLocation extends AppCompatActivity {
EditText provinceTXT,municipalTXT;
Button addBTN;
DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        provinceTXT=findViewById(R.id.province);
        municipalTXT=findViewById(R.id.locationState);
        addBTN=findViewById(R.id.locationBTN);
        db=new DBHelper(this);

        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isValidInput(provinceTXT, "Province is required", 4)) {
                    return;
                }
                if (!isValidInput(municipalTXT, "Municipality is required", 4)) {
                    return;
                }
                String city = municipalTXT.getText().toString();
                String province= provinceTXT.getText().toString();

                Boolean insertLocation = db.insertLocation(city,province);

                if(insertLocation==true){
                    Intent gotoPolice = new Intent(addLocation.this,policeDepartment.class);
                    startActivity(gotoPolice);
                    finish();
                    Toast.makeText(getApplicationContext(), "Location inserted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error inserting record", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private boolean isValidInput(EditText editText, String errorMessage, int minLength) {
        if (editText.getText().toString().isEmpty()) {
            editText.setError(errorMessage);
            return false;
        } else if (editText.getText().toString().length() < minLength) {
            editText.setError(errorMessage + " must be at least " + minLength + " characters long");
            return false;
        }
        return true;
    }

}