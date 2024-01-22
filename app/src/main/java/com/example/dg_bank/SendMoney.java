package com.example.dg_bank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SendMoney extends AppCompatActivity {

    TextView sender, receiver, balance;
    EditText amount;
    Button send_button;
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

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshBalance();
                Toast.makeText(SendMoney.this, "Balance Refreshed!", Toast.LENGTH_SHORT).show();
            }
        });
        refreshBalance();


        sender.setText(String.format("%s | %s", Data.CurrentUserID, Data.CurrentUserName));
        receiver.setText(String.format("%s | %s", Data.Receiver_ID, Data.sqlManager.getName(this, Data.Receiver_ID)));

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMoney();
            }
        });

    }


    public void refreshBalance()
    {
        balance.setText(Data.sqlManager.getBalance(this, Data.CurrentUserID));
    }

    public void sendMoney()
    {
        int sender_balance = Integer.parseInt(Data.sqlManager.getBalance(this, Data.CurrentUserID));
        int receiver_balance = Integer.parseInt(Data.sqlManager.getBalance(this, Data.Receiver_ID));
        int amountS = Integer.parseInt(amount.getText().toString());

        if(sender_balance>=amountS)
        {
            if(Data.sqlManager.setBalance(this, Data.Receiver_ID, Integer.toString(receiver_balance+amountS)))
            {
//                Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
                if(Data.sqlManager.setBalance(this, Data.CurrentUserID, Integer.toString(sender_balance-amountS)))
                {
                    Toast.makeText(this, "Transaction Successful", Toast.LENGTH_SHORT).show();
                    refreshBalance();
                }
                else
                {
                    Data.sqlManager.setBalance(this, Data.Receiver_ID, Integer.toString(receiver_balance));
                    Toast.makeText(this, "Transaction Unsuccessful\nCouldn't complete transaction from your account", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "Transaction Unsuccessful\nCouldn't send money to the receiver", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Insufficient Balance", Toast.LENGTH_SHORT).show();
        }
    }
}