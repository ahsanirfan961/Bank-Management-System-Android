package com.example.dg_bank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class PasswordChange extends AppCompatActivity {

    Button save;
    TextInputEditText old_pass, new_pass, confirm_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        save = findViewById(R.id.save_button_password);
        old_pass = findViewById(R.id.old_password_edit);
        new_pass = findViewById(R.id.new_password_edit);
        confirm_pass = findViewById(R.id.confirm_password_edit);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
    }

    public void changePassword()
    {
        String old = Objects.requireNonNull(old_pass.getText()).toString().trim();
        String new_p = Objects.requireNonNull(new_pass.getText()).toString().trim();
        String confirm = Objects.requireNonNull(confirm_pass.getText()).toString().trim();

        if(old.equals("")||confirm.equals("")||new_p.equals(""))
        {
            Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show();
            return;
        }
        if(old.equals(Data.sqlManager.getValue(this,"Application_User","Password",Data.CurrentUserID)))
        {
            if(new_p.equals(confirm))
            {
                if(Data.sqlManager.setValue(this, "Application_User","Password", Data.CurrentUserID, new_p))
                {
                    Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Password change failed\nTry Again Later", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(this, "New Passwords don't match", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Incorrect old password", Toast.LENGTH_SHORT).show();
        }
    }
}