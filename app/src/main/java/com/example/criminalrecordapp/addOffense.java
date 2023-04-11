package com.example.criminalrecordapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addOffense extends AppCompatActivity {
EditText offenseTypeTxt,messageTxt;
Button addOffenseBtn;
DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offense);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        offenseTypeTxt=findViewById(R.id.typeOffense);
        messageTxt=findViewById(R.id.message);
        addOffenseBtn=findViewById(R.id.addOffense);

        db=new DBHelper(this);

        addOffenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isValidInput(offenseTypeTxt, "Offense Type is required", 4)) {
                    return;
                }
                if (!isValidInput(messageTxt, "Description is required", 15)) {
                    return;
                }
                String offense_type= offenseTypeTxt.getText().toString();
                String message=messageTxt.getText().toString();

                Boolean insertOffense = db.insertOffense(offense_type, message);
                if(insertOffense==true){
                    Intent gotoLocation = new Intent(addOffense.this,addLocation.class);
                    startActivity(gotoLocation);
                    finish();
                    Toast.makeText(getApplicationContext(), "Offense inserted successfully", Toast.LENGTH_SHORT).show();
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