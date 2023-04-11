package com.example.criminalrecordapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class addRecord extends AppCompatActivity {
  EditText nameTxt,ageTxt,dateNum,offenseID,locationID,departmentID;
  Button addCrime;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        nameTxt=findViewById(R.id.suspect);
        ageTxt=findViewById(R.id.age);
        dateNum=findViewById(R.id.Date);
        offenseID=findViewById(R.id.offense);
        locationID=findViewById(R.id.location);
        departmentID=findViewById(R.id.department);
        addCrime=findViewById(R.id.add);

        db = new DBHelper(this);

        dateNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerHelper datePickerHelper = new DatePickerHelper(addRecord.this, new DatePickerHelper.OnDateSetListener() {
                    @Override
                    public void onDateSet(String date) {
                        dateNum.setText(date);
                    }
                });
                datePickerHelper.showDatePicker();
            }
        });

        addCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //User Validation for correct input
                if (!isValidInput(nameTxt, "Name is required", 5)) {
                    return;
                }
                if (!isValidInput(ageTxt, "Age is required", 1)) {
                    return;
                }
                if (!isValidInput(dateNum, "Date is required", 8)) {
                    return;
                }
                if (!isValidInput(offenseID, "Offense is required", 1)) {
                    return;
                }
                if (!isValidInput(locationID, "Location is required", 1)) {
                    return;
                }
                if (!isValidInput(departmentID, "Department is required", 1)) {
                    return;
                }

             String name = nameTxt.getText().toString();
              int age = Integer.parseInt(ageTxt.getText().toString());
               String date = dateNum.getText().toString();
                int offense = Integer.parseInt(offenseID.getText().toString());
                 int location = Integer.parseInt(locationID.getText().toString());
                  int department = Integer.parseInt(departmentID.getText().toString());

              Boolean insertCrime = db.insertCriminalRecord(name,age,date,offense,location,department);
                if(insertCrime==true){
                    startActivity(new Intent(addRecord.this, addOffense.class));
                    finish();
                    Toast.makeText(getApplicationContext(), "Criminal inserted successfully", Toast.LENGTH_SHORT).show();
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