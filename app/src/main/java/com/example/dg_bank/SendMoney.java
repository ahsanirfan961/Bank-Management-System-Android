package com.example.dg_bank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SendMoney extends AppCompatActivity {

    TextView sender, receiver, balance;
    EditText amount;
    Button send_button;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);
        ImageButton refresh = findViewById(R.id.refreshButton_send);
        sender = findViewById(R.id.sender);
        receiver = findViewById(R.id.receiver);
        balance = findViewById(R.id.balance_send);
        amount = findViewById(R.id.amount);
        send_button = findViewById(R.id.send_button);
        progressBar = findViewById(R.id.pg_send_money);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RefreshBalanceTask().execute();
                Toast.makeText(SendMoney.this, "Balance Refreshed!", Toast.LENGTH_SHORT).show();
            }
        });
        new RefreshBalanceTask().execute();
        new UpdateTask().execute();
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SendMoneyTask().execute();
            }
        });

    }

    private class UpdateTask extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            sender.setText(String.format("%s | %s", Data.CurrentUserID, Data.CurrentUserName));
            receiver.setText(String.format("%s | %s", Data.Receiver_ID, Data.sqlManager.getName(SendMoney.this, Data.Receiver_ID)));
            return null;
        }
    }

    private class RefreshBalanceTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            balance.setText(Data.sqlManager.getBalance(SendMoney.this, Data.CurrentUserID));
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private class SendMoneyTask extends AsyncTask<Void, Void, Integer>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            int sender_balance = Integer.parseInt(Data.sqlManager.getBalance(SendMoney.this, Data.CurrentUserID));
            int receiver_balance = Integer.parseInt(Data.sqlManager.getBalance(SendMoney.this, Data.Receiver_ID));
            int amountS = Integer.parseInt(amount.getText().toString());

            if(sender_balance>=amountS)
            {
                if(Data.sqlManager.setBalance(SendMoney.this, Data.Receiver_ID, Integer.toString(receiver_balance+amountS)))
                {
                    if(Data.sqlManager.setBalance(SendMoney.this, Data.CurrentUserID, Integer.toString(sender_balance-amountS)))
                        return 1;
                    else
                    {
                        Data.sqlManager.setBalance(SendMoney.this, Data.Receiver_ID, Integer.toString(receiver_balance));
                        return 0;
                    }
                }
                else
                    return 0;
            }
            else
                return -1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.INVISIBLE);
            if(result == 1)
            {
                Toast.makeText(SendMoney.this, "Transaction Successful", Toast.LENGTH_SHORT).show();
                new RefreshBalanceTask().execute();
            }
            else if(result == 0)
            {
                Toast.makeText(SendMoney.this, "Transaction Unsuccessful", Toast.LENGTH_SHORT).show();
            }
            else if (result == -1)
            {
                Toast.makeText(SendMoney.this, "Insufficient Balance", Toast.LENGTH_SHORT).show();
            }
        }
    }
}