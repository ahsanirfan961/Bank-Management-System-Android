package com.example.dg_bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Cards extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);
    }

    public void goto_newCard(View v)
    {
        Intent intent = new Intent(this, NewCard.class);
        startActivity(intent);
    }
}