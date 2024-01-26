package com.example.dg_bank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class PasswordChange extends AppCompatActivity {

    Button save;
    TextInputEditText old_pass, new_pass, confirm_pass;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        save = findViewById(R.id.save_button_password);
        old_pass = findViewById(R.id.old_password_edit);
        new_pass = findViewById(R.id.new_password_edit);
        confirm_pass = findViewById(R.id.confirm_password_edit);
        progressBar = findViewById(R.id.pg_password_change);
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
        if(!new_p.equals(confirm))
        {
            Toast.makeText(this, "New Passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }
        new PasswordChangeTask().execute(old, new_p);
    }

    private class PasswordChangeTask extends android.os.AsyncTask<String, Void, Integer>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            save.setEnabled(false);
        }

        @Override
        protected Integer doInBackground(String... strings) {
            if(strings[0].equals(Data.sqlManager.getValue("Application_User","Password",Data.CurrentUserID)))
            {
                if(Data.sqlManager.setValue("Application_User","Password", Data.CurrentUserID, strings[1]))
                    return 1;
                else
                    return 0;
            }
            else
                return -1;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            progressBar.setVisibility(View.INVISIBLE);
            save.setEnabled(true);
            //Flags: 1 = Success, 0 = Failure, -1 = Wrong Password
            switch (integer)
            {
                case 1:
                    Toast.makeText(PasswordChange.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    Toast.makeText(PasswordChange.this, "Password Change Failed", Toast.LENGTH_SHORT).show();
                    break;
                case -1:
                    Toast.makeText(PasswordChange.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}