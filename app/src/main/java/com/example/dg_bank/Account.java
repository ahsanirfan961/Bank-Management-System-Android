package com.example.dg_bank;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.sourceforge.jtds.jdbc.cache.SQLCacheKey;

public class Account extends AppCompatActivity {
    Button createAccount;
    Button accountSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        createAccount = findViewById(R.id.create_account);
        accountSetting = findViewById(R.id.account_setting);
        update();
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

     private void update()
     {
         if(Data.sqlManager.doesExist(this, "Personal_Info", "User_ID", Data.CurrentUserID))
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        update();
    }
}