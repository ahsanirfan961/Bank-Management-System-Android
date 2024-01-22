package com.example.dg_bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.sql.SQLException;
import java.sql.Statement;

public class ProfileSettings extends AppCompatActivity {

    EditText fname, lname, username;
    ImageButton fname_edit, lname_edit, username_edit;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        fname = findViewById(R.id.fname_editText);
        lname = findViewById(R.id.lname_editText);
        username = findViewById(R.id.username_editText);
        fname_edit = findViewById(R.id.fname_editButton);
        lname_edit = findViewById(R.id.lname_editButton);
        username_edit = findViewById(R.id.username_editButton);
        saveButton = findViewById(R.id.save_button_password);

        //Disabling the buttons on create view
        fname.setEnabled(false);
        lname.setEnabled(false);
        username.setEnabled(false);
        saveButton.setEnabled(false);

        fname_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname.setEnabled(!fname.isEnabled());
                saveButton.setEnabled(fname.isEnabled()||lname.isEnabled()||username.isEnabled());
            }
        });
        lname_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lname.setEnabled(!lname.isEnabled());
                saveButton.setEnabled(fname.isEnabled()||lname.isEnabled()||username.isEnabled());
            }
        });
        username_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.setEnabled(!username.isEnabled());
                saveButton.setEnabled(fname.isEnabled()||lname.isEnabled()||username.isEnabled());
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        update();
    }

    public void goto_change_p(View v)
    {
        Intent intent = new Intent(this, PasswordChange.class);
        startActivity(intent);
    }
    
    public void save()
    {
        String firstName = fname.getText().toString().trim();
        String lastName = lname.getText().toString().trim();
        String userName = username.getText().toString().trim();
        if (Data.sqlManager.conn != null)
        {
            try {
                Statement statement = Data.sqlManager.conn.createStatement();
                int rowsAffected = statement.executeUpdate("UPDATE Application_User SET First_Name = '"+firstName+"', Last_Name = '"+lastName+"', Email = '"+userName+"' WHERE ID = '"+Data.CurrentUserID+"'");
                if(rowsAffected>0)
                {
                    Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update()
    {
        fname.setText(Data.sqlManager.getValue(this, "Application_User", "First_Name", Data.CurrentUserID));
        lname.setText(Data.sqlManager.getValue(this, "Application_User", "Last_Name", Data.CurrentUserID));
        username.setText(Data.sqlManager.getValue(this, "Application_User", "Email", Data.CurrentUserID));
    }
}