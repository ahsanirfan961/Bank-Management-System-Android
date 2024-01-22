package com.example.dg_bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerifyAccount extends AppCompatActivity {
    TextView sender;
    EditText receiver;
    Button verify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_account);
        sender = findViewById(R.id.from_account);
        receiver = findViewById(R.id.amount);
        verify = findViewById(R.id.verify_button);
        update();
    }

    public void goto_send_money(View v)
    {
        String id = receiver.getText().toString();
        if(isIdValid(id)) {
            Data.Receiver_ID = id;
            Intent intent = new Intent(this, SendMoney.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Account with this id not found!", Toast.LENGTH_SHORT).show();
        }
    }

    public void update()
    {
        String text = Data.CurrentUserID + " | " + Data.CurrentUserName;
        sender.setText(text);
    }

    public boolean isIdValid(String id) {
        return Data.sqlManager.doesExist(this, "Personal_Info", "User_ID", id);
    }

}