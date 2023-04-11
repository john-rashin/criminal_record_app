package com.example.criminalrecordapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Password extends AppCompatActivity {
    EditText emaill, currentPassEditText, newPassEditText, retypePassEditText;
    Button resetButton;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        emaill = findViewById(R.id.email);
        currentPassEditText = findViewById(R.id.currentPass);
        newPassEditText = findViewById(R.id.newPass);
        retypePassEditText = findViewById(R.id.retypePassword);

        resetButton = findViewById(R.id.resetBtn);

        db = new DBHelper(this);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emaill.getText().toString();
                String currentPass = currentPassEditText.getText().toString();
                String newPass = newPassEditText.getText().toString();
                String retypePass = retypePassEditText.getText().toString();
                if (!newPass.equals(retypePass)) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
                if (db.validatePassword(email, currentPass)) {
                    db.resetPassword(email, newPass);
                    Intent intent = new Intent(Password.this, MainActivity.class);
                    intent.putExtra("username", email);
                    intent.putExtra("password", newPass);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Password reset successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Current password is not correct", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}