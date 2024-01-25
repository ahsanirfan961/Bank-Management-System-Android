package com.example.dg_bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class Menu extends AppCompatActivity {

    TextView account_name, account_id, balance;
    ImageButton money, settings, account, cards, book, statements;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        account_name = findViewById(R.id.account_name);
        account_id = findViewById(R.id.account_id);
        money = findViewById(R.id.sendMoney_button);
        settings = findViewById(R.id.profile_button);
        account = findViewById(R.id.accountButton);
        cards = findViewById(R.id.cardButton);
        book = findViewById(R.id.chequeBook_button);
        statements = findViewById(R.id.statementButton);
        balance = findViewById(R.id.balance);
        progressBar = findViewById(R.id.pg_menu);
        ImageButton refresh = findViewById(R.id.refreshButton);

        progressBar.setVisibility(View.INVISIBLE);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UpdateMenuTask().execute();
            }
        });
        update();
    }

    public void update() {
        String name = "---";
        String b = "---";
        if(Data.accountExist) {
            if (Objects.equals(Data.CurrentGender, "1")) {
                name = "Mr. ";
            } else {
                name = "Mrs. ";
            }
            name += Data.CurrentUserName;
            b = Data.CurrentBalance;
        }

        account_name.setText(name);
        account_id.setText(Data.CurrentUserID);
        balance.setText(b);
        if(Data.accountExist)
        {
            money.setEnabled(true);
            cards.setEnabled(true);
            book.setEnabled(true);
            statements.setEnabled(true);
        }
        else
        {
            money.setEnabled(false);
            cards.setEnabled(false);
            book.setEnabled(false);
            statements.setEnabled(false);
        }
    }

    private class UpdateMenuTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Data.accountExist = Data.sqlManager.doesExist(Menu.this, "Personal_Info", "User_ID", Data.CurrentUserID);
            if(Data.accountExist)
            {
                Data.CurrentBalance = Data.sqlManager.getBalance(Menu.this, Data.CurrentUserID);
                Data.CurrentGender = Data.sqlManager.getValue(Menu.this, "Personal_Info", "Gender", Data.CurrentUserID);
            }
            update();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(Menu.this, "Balanced Refreshed!", Toast.LENGTH_SHORT).show();
        }
    }


    public void goto_profileSetting(View v)
    {
        Intent intent = new Intent(this, ProfileSettings.class);
        startActivity(intent);
    }

    public void goto_verify(View v)
    {
        Intent intent = new Intent(this, VerifyAccount.class);
        startActivity(intent);
    }

    public void goto_account(View v)
    {
        Intent intent = new Intent(this, Account.class);
        startActivity(intent);
    }

    public void goto_card(View v)
    {
        Intent intent = new Intent(this, Cards.class);
        startActivity(intent);
    }
}