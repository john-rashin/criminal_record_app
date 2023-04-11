package com.example.criminalrecordapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    DBHelper db;
    TextView idNum;
    EditText nameEditText, ageEditText,dateEditText,
            offenseTypeEditText,offenseDescriptionEditText,
            municipalityEditText,provinceEditText,stationNameEditText,
            stationAddressEditText;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_button:
                // Retrieve input from EditTexts
                String newName = nameEditText.getText().toString();
                String newAge = ageEditText.getText().toString();
                String newDate = dateEditText.getText().toString();
                String newOffenseType = offenseTypeEditText.getText().toString();
                String newOffenseDescription = offenseDescriptionEditText.getText().toString();
                String newMunicipality = municipalityEditText.getText().toString();
                String newProvince = provinceEditText.getText().toString();
                String newStationName = stationNameEditText.getText().toString();
                String newStationAddress = stationAddressEditText.getText().toString();
                String newId = idNum.getText().toString();
                //call the saveUpdates method and pass the values
                db.saveUpdates(newName, newAge, newDate, newOffenseType, newOffenseDescription, newMunicipality, newProvince, newStationName, newStationAddress, Integer.parseInt(newId));
                Toast.makeText(this, "Update Saved", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditActivity.this, viewRecord.class));
                finish();
                return true;
            case R.id.delete_button:
                String Id = idNum.getText().toString();
                db.deleteRecord(Integer.parseInt(Id));
                Toast.makeText(this, "Record Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditActivity.this, dashboard.class));
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        db = new DBHelper(this);

     idNum = findViewById(R.id.idNum);
     nameEditText =  findViewById(R.id.name);
     ageEditText=  findViewById(R.id.age);
     dateEditText = findViewById(R.id.date);
     offenseTypeEditText =findViewById(R.id.offenseType);
     offenseDescriptionEditText =  findViewById(R.id.offenseDes);
     municipalityEditText =  findViewById(R.id.municipality);
     provinceEditText= findViewById(R.id.province);
     stationNameEditText =  findViewById(R.id.stationName);
     stationAddressEditText =  findViewById(R.id.stationAddress);

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerHelper datePickerHelper = new DatePickerHelper(EditActivity.this, new DatePickerHelper.OnDateSetListener() {
                    @Override
                    public void onDateSet(String date) {
                        dateEditText.setText(date);
                    }
                });
                datePickerHelper.showDatePicker();
            }
        });

        int target = getIntent().getExtras().getInt("id");
        String name = getIntent().getExtras().getString("name");
        int age = getIntent().getExtras().getInt("age");
        String date = getIntent().getExtras().getString("date");
        String offenseType = getIntent().getExtras().getString("offenseType");
        String offenseDescription = getIntent().getExtras().getString("offenseDescription");
        String municipality = getIntent().getExtras().getString("municipality");
        String province = getIntent().getExtras().getString("province");
        String stationName = getIntent().getExtras().getString("stationName");
        String stationAddress = getIntent().getExtras().getString("stationAddress");

        idNum.setText(String.valueOf(target));
        nameEditText.setText(name);
        ageEditText.setText(String.valueOf(age));
        dateEditText.setText(date);
        offenseTypeEditText.setText(offenseType);
        offenseDescriptionEditText.setText(offenseDescription);
        municipalityEditText.setText(municipality);
        provinceEditText.setText(province);
        stationNameEditText.setText(stationName);
        stationAddressEditText.setText(stationAddress);

    }

}