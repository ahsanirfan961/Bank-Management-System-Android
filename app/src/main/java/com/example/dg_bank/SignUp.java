package com.example.dg_bank;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;
import java.sql.Statement;

public class SignUp extends AppCompatActivity {

    EditText fname, lname, username, password, repassword;
    CheckBox terms;
    Button signupButton;

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

        // Set click listener for the sign-up button
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (terms.isChecked()) {
                    // If terms are accepted, save user data to the database
                    saveUserDataToDatabase();
                } else {
                    Toast.makeText(SignUp.this, "Please accept the terms and conditions.", Toast.LENGTH_SHORT).show();
                }
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

        // Perform database insertion
        try {
             // Assuming you have a method in SQLServerConnect class to establish the connection
            if (Data.sqlManager.conn != null) {
                // Create a prepared statement to insert data into the database
                int ID = Data.sqlManager.getNextID(this, "Application_User");
                String insertQuery = "INSERT INTO Application_User (ID, First_Name, Last_Name, Email, Password) VALUES ('"+Integer.toString(ID)+"', '"+firstName+"','"+lastName+"' , '"+userName+"', '"+userPassword+"')";
                Statement statement = Data.sqlManager.conn.createStatement();

                // Execute the insert query
                int rowsAffected = statement.executeUpdate(insertQuery);
                Log.println(Log.ASSERT,"3","Reached down");
                if (rowsAffected > 0) {
                    Toast.makeText(this, "User registered successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to register user.", Toast.LENGTH_SHORT).show();
                }

                // Close the prepared statement and connection
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Database error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}
