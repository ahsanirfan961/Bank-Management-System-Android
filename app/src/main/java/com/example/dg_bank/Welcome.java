package com.example.dg_bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }
    public void goto_signin(View v)
    {
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }
    public void goto_signup(View v)
    {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}