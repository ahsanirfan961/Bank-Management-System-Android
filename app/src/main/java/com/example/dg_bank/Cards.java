package com.example.dg_bank;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Cards extends AppCompatActivity {
    Button myCards, newCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);
        myCards = findViewById(R.id.my_cards);
        newCard = findViewById(R.id.new_card);
        new UpdateTask().execute();
    }

    private class UpdateTask extends AsyncTask<Void, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(Void... voids) {
            if(Data.sqlManager.doesExist("Card", "User_ID", Data.CurrentUserID))
                return true;
            else
                return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean)
                myCards.setEnabled(true);
            else
                myCards.setEnabled(false);
        }
    }

    public void goto_newCard(View v)
    {
        Intent intent = new Intent(this, NewCard.class);
        startActivity(intent);
    }


}