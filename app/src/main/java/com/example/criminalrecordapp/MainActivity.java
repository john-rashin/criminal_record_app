package com.example.criminalrecordapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    // Declare variables for the username and password fields, and the login button
     EditText mUsernameField;
     EditText mPasswordField;
     Button mLoginButton,create;


    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //  getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
       // getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");


        // Initialize the username and password fields, and the login button
        db = new DBHelper(this);
                mUsernameField = findViewById(R.id.email);
                mPasswordField = findViewById(R.id.password);
                mLoginButton = findViewById(R.id.loginBtn);
                create=findViewById(R.id.create);


        // Use the extras as needed
        mUsernameField.setText(username);
        mPasswordField.setText(password);

        // Set an OnClickListener on the create button
               create.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
              String user=mUsernameField.getText().toString();
              String pass=mPasswordField.getText().toString();

                        if(!validEmail(mUsernameField.getText().toString())){
                            mUsernameField.setError("Please Enter Valid Email Address");
                        }

                        if (user.isEmpty()) {
                            mUsernameField.setError("Email is required");
                            mUsernameField.requestFocus();
                            return;
                        }
                        if (pass.isEmpty()) {
                            mPasswordField.setError("Password is required");
                            mPasswordField.requestFocus();
                            return;
                        }else {
                            if (user.equals(user)) {
                                Boolean checkuser = db.checkUser(user);
                                if (checkuser == false) {
                                    Boolean insert = db.addUser(user, pass);
                                    if (insert == true) {
                                        Toast.makeText(MainActivity.this, "Registered successfully, Please Click Login Button", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Email address already exists! please Log in", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    }
        //Email Validation Using Regex
        public boolean validEmail(String email){
            Pattern pattern;
            Matcher matcher;
            final String EMAIL_PATTERN ="^[\\w]{1,}[\\w.+-]{0,}@[\\w-]{2,}([.][a-zA-Z]{2,}|[.][\\w-]{2,}[.][a-zA-Z]{2,})$";
            pattern =Pattern.compile(EMAIL_PATTERN);
            matcher=pattern.matcher(email);
            return matcher.matches();
        }
                });

               mLoginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String user = mUsernameField.getText().toString();
                        String pass = mPasswordField.getText().toString();

                        if (user.isEmpty()) {
                            mUsernameField.setError("Email is required");
                            mUsernameField.requestFocus();
                            return;
                        }
                        if (pass.isEmpty()) {
                            mPasswordField.setError("Password is required");
                            mPasswordField.requestFocus();
                            return;
                        }else {
                            Boolean checkuserpass = db.getUserData(user , pass);

                        if(checkuserpass==true){
                            mPasswordField.setText("");
                          String email= mUsernameField.getText().toString();
                            Intent dash= new Intent(MainActivity.this,dashboard.class);
                            dash.putExtra("email",email);
                            startActivity(dash);
                            Toast.makeText(MainActivity.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Wrong Email or Password,please check!", Toast.LENGTH_SHORT).show();
                        }
                        }
                    }
                });
            }
}