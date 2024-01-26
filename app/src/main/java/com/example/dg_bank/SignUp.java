package com.example.dg_bank;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;
import java.sql.Statement;

public class SignUp extends AppCompatActivity {

    EditText fname, lname, username, password, repassword;
    CheckBox terms;
    Button signupButton;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize views
        fname = findViewById(R.id.fname_edit);
        lname = findViewById(R.id.lname_edit);
        username = findViewById(R.id.fathername_edit);
        password = findViewById(R.id.mname_edit);
        repassword = findViewById(R.id.repassword_entry);
        terms = findViewById(R.id.terms);
        signupButton = findViewById(R.id.signup_button);
        progressBar = findViewById(R.id.pg_sign_up);

        // Set click listener for the sign-up button
        signupButton.setOnClickListener(v -> {
            if (terms.isChecked()) {
                // If terms are accepted, save user data to the database
                saveUserDataToDatabase();
            } else {
                Toast.makeText(SignUp.this, "Please accept the terms and conditions.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserDataToDatabase() {
        // Retrieve user information from EditText views
        String firstName = fname.getText().toString().trim();
        String lastName = lname.getText().toString().trim();
        String userName = username.getText().toString().trim();
        String userPassword = password.getText().toString();
        String confirmPassword = repassword.getText().toString();

        if(firstName.equals("") || lastName.equals("") || userName.equals("") || userPassword.equals("")|| confirmPassword.equals(""))
        {
            Toast.makeText(this, "All fields required!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if password and confirm password match
        if (!userPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return;
        }
        new SubmitTask().execute(firstName, lastName, userName, userPassword);
    }

    private class SubmitTask extends AsyncTask<String, Void, Boolean>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            signupButton.setEnabled(false);
            if(!Data.sqlManager.checkConn(SignUp.this)) {
                progressBar.setVisibility(View.INVISIBLE);
                signupButton.setEnabled(true);
                cancel(true);
            }
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            if(Data.sqlManager.doesExist("Application_User", "Email", strings[2]))
                return false;
            Data.sqlManager.insertRow("Application_User", new String[]{"ID", "First_Name", "Last_Name", "Email", "Password"}, new String[]{Integer.toString(Data.sqlManager.getNextID("Application_User")), strings[0], strings[1], strings[2], strings[3]});
            return true;
        }

        @Override
        protected void onPostExecute(Boolean dontExist) {
            super.onPostExecute(dontExist);
            progressBar.setVisibility(View.INVISIBLE);
            signupButton.setEnabled(true);
            if(dontExist)
                Toast.makeText(SignUp.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(SignUp.this, "Username already exists!", Toast.LENGTH_SHORT).show();
        }
    }


}
