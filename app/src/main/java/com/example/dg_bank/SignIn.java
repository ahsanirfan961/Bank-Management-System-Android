package com.example.dg_bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.*;
import java.util.Objects;

public class SignIn extends AppCompatActivity {
    EditText username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        username = findViewById(R.id.fathername_edit);
        password = findViewById(R.id.mname_edit);

    }
    public void goto_menu(View v) {
        String user = username.getText().toString().trim();
        String pass = password.getText().toString();
        if (user.equals("") || pass.equals("")) {
            Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] credentials = Data.sqlManager.getRow("Application_User", new String[]{"ID", "First_Name", "Last_Name", "Email", "Password"}, new String[]{"Email", user});
        if (credentials == null)
            Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
        else {
            if(Objects.equals(credentials[4], pass))
            {
                Data.CurrentUserID = credentials[0];
                Intent intent = new Intent(this, Menu.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
            }
        }
    }
}