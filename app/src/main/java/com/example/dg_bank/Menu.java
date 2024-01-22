package com.example.dg_bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class Menu extends AppCompatActivity {

    TextView account_name, account_id, balance;
    ImageButton money, settings, account, cards, book, statements;

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

        ImageButton refresh = findViewById(R.id.refreshButton);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
                Toast.makeText(Menu.this, "Balanced Refreshed!", Toast.LENGTH_SHORT).show();
            }
        });
        update();
    }

    public void update() {
        String name = "---";
        String b = "---";
        boolean accountExist = Data.sqlManager.doesExist(this, "Personal_Info", "User_ID", Data.CurrentUserID);
        if(accountExist) {
            if (Objects.equals(Data.sqlManager.getValue(this, "Personal_Info", "Gender", Data.CurrentUserID), "1")) {
                name = "Mr. ";
            } else {
                name = "Mrs. ";
            }
            name += Data.sqlManager.getName(this, Data.CurrentUserID);
            Data.CurrentUserName = name;
            b = Data.sqlManager.getValue(this, "Personal_Info", "Balance", Data.CurrentUserID);
        }

        account_name.setText(name);
        account_id.setText(Data.CurrentUserID);

        balance.setText(b);
        if(accountExist)
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

    public void goto_profilesetting(View v)
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