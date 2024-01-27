package com.example.dg_bank;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class SignIn extends AppCompatActivity {
    EditText username, password;
    ProgressBar progressBar;
    Button signIn;
    int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        username = findViewById(R.id.fathername_edit);
        password = findViewById(R.id.mname_edit);
        progressBar = findViewById(R.id.pg_sign_in);
        progressBar.setVisibility(View.INVISIBLE);
        signIn = findViewById(R.id.signin_button);
    }
    public void goto_menu(View v) {
        progressBar.setVisibility(View.VISIBLE);
        status = -1;
        String user = username.getText().toString().trim();
        String pass = password.getText().toString();
        if (user.equals("") || pass.equals("")) {
            Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        new SignInAsyncTask().execute(user, pass);
    }


    private class SignInAsyncTask extends AsyncTask<String, Void, String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            signIn.setEnabled(false);
        }
        @Override
        protected String doInBackground(String... strings) {
            if(Data.sqlManager.conn == null) {
                Data.sqlManager.getConn();
                status = -1;
                return null;
            }
            String[] credentials = Data.sqlManager.getRow("Application_User", new String[]{"ID", "First_Name", "Last_Name", "Email", "Password"}, new String[]{"Email", username.getText().toString().trim()});
            if (credentials == null)
                status = 0;
            else {
                if(Objects.equals(credentials[4], password.getText().toString().trim()))
                {
                    Data.CurrentUserID = credentials[0];
                    Data.CurrentUserName = credentials[1] + " " + credentials[2];
                    Data.accountExist = Data.sqlManager.doesExist("Personal_Info", "User_ID", Data.CurrentUserID);
                    if(Data.accountExist)
                    {
                        Data.CurrentBalance = Data.sqlManager.getBalance(Data.CurrentUserID);
                        Data.CurrentGender = Data.sqlManager.getValue("Personal_Info", "Gender", Data.CurrentUserID);
                    }
                    status = 1;
                }
                else
                    status = 0;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.INVISIBLE);
            signIn.setEnabled(true);
            if(status == 1)
            {
                Intent intent = new Intent(SignIn.this, Menu.class);
                startActivity(intent);
            }
            else if(status == 0)
                Toast.makeText(SignIn.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(SignIn.this, "Connection error", Toast.LENGTH_SHORT).show();
        }
    }

}