package com.example.dg_bank;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Account extends AppCompatActivity {
    Button createAccount;
    Button accountSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        createAccount = findViewById(R.id.create_account);
        accountSetting = findViewById(R.id.account_setting);
        new UpdateTask().execute();
    }
     public void goto_openAccount(View v)
     {
         Intent intent = new Intent(this, OpenAccount.class);
         startActivityForResult(intent, 1);
     }

    public void goto_settings(View v)
    {
        Intent intent = new Intent(this, AccountSettings.class);
        startActivity(intent);
    }

     private class UpdateTask extends AsyncTask<Void, Void, Boolean>
     {
         @Override
         protected Boolean doInBackground(Void... voids) {
             if(Data.sqlManager.doesExist("Personal_Info", "User_ID", Data.CurrentUserID))
                 return true;
             else
                return false;
         }

         @Override
         protected void onPostExecute(Boolean aBoolean) {
             super.onPostExecute(aBoolean);
             if(aBoolean)
             {
                 createAccount.setEnabled(false);
                 accountSetting.setEnabled(true);
             }
             else
             {
                 createAccount.setEnabled(true);
                 accountSetting.setEnabled(false);
             }
         }
     }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        new UpdateTask().execute();
    }
}