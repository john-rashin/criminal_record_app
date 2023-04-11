package com.example.criminalrecordapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class dashboard extends AppCompatActivity {
  TextView tv1;
  CardView viewRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
       //Setting welcome message to users
        tv1=findViewById(R.id.userDisplay);
       String data = getIntent().getExtras().getString("email");
        tv1.setText(data);
        viewRecord=findViewById(R.id.view);



    }
    //add criminal
    public void add(View view){
        startActivity(new Intent(dashboard.this,addRecord.class));
    }
    //add offense
    public void onClickOffence(View view){
        startActivity(new Intent(dashboard.this,addOffense.class));
    }
    //Police Department
    public void onClickDepartment(View view){
        startActivity(new Intent(dashboard.this,policeDepartment.class));
    }
    //View Record
    public void onClickView(View view){
        startActivity(new Intent(dashboard.this,viewRecord.class));
    }
    //add Location
    public void onClickLocation(View view){
        startActivity(new Intent(dashboard.this,addLocation.class));
    }
    //goto Information
    public void onClickEdit(View view){
        startActivity(new Intent(dashboard.this,Password.class));
    }
}