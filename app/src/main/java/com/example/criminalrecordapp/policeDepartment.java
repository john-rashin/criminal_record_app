package com.example.criminalrecordapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class policeDepartment extends AppCompatActivity {
EditText nameTxt,addressTxt;
Button departmentBTN;
DBHelper db;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_department);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        nameTxt=findViewById(R.id.stationName);
        addressTxt=findViewById(R.id.stationAddress);
        departmentBTN=findViewById(R.id.departmentBtn);

        db=new DBHelper(this);

        departmentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isValidInput(nameTxt, "Station Name is required", 4)) {
                    return;
                }
                if (!isValidInput(addressTxt, "Station Address is required", 4)) {
                    return;
                }
                String station_name=nameTxt.getText().toString();
                String station_address=addressTxt.getText().toString();

                Boolean insertDepartment = db.insertDepartment(station_name,station_address);
                if(insertDepartment==true){
                    Toast.makeText(getApplicationContext(), "Department inserted successfully", Toast.LENGTH_SHORT).show();
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