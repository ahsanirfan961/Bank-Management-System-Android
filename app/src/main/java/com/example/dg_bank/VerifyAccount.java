package com.example.dg_bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class VerifyAccount extends AppCompatActivity {
    TextView sender;
    EditText receiver;
    Button verify;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_account);
        sender = findViewById(R.id.from_account);
        receiver = findViewById(R.id.amount);
        verify = findViewById(R.id.verify_button);
        progressBar = findViewById(R.id.pg_verify_account);
        update();
    }

    public void goto_send_money(View v)
    {
        String id = receiver.getText().toString();
        Data.Receiver_ID = id;
        new VerifyAccountTask().execute(id);
    }

    public void update()
    {
        String text = Data.CurrentUserID + " | " + Data.CurrentUserName;
        sender.setText(text);
    }

    public boolean isIdValid(String id) {
        return Data.sqlManager.doesExist(this, "Personal_Info", "User_ID", id);
    }

    private class VerifyAccountTask extends AsyncTask<String, Void, Boolean>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            return isIdValid(strings[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressBar.setVisibility(View.INVISIBLE);
            if(aBoolean)
            {
                Intent intent = new Intent(VerifyAccount.this, SendMoney.class);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(VerifyAccount.this, "Account with this id not found!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}