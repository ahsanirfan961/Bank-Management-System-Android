package com.example.dg_bank;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;

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

    private class UpdateTask extends AsyncTask<Void, Void, Boolean[]>
    {
        @Override
        protected Boolean[] doInBackground(Void... voids) {
            Boolean[] aBoolean = new Boolean[2];
            ResultSet rs = Data.sqlManager.getQuery("SELECT * FROM Card WHERE User_ID = '"+Data.CurrentUserID+"'");
            int rowCount = 0;
            try
            {
                while (rs.next()) {
                    rowCount++;
                }
                if(rowCount>0)
                    aBoolean[0] = true;
                else
                    aBoolean[0] = false;
                if(rowCount==3)
                    aBoolean[1] = false;
                else
                    aBoolean[1] = true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return aBoolean;
        }

        @Override
        protected void onPostExecute(Boolean[] aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean[0])
                myCards.setEnabled(true);
            else
                myCards.setEnabled(false);
            if(aBoolean[1])
                newCard.setEnabled(true);
            else
                newCard.setEnabled(false);
        }
    }

    public void goto_newCard(View v)
    {
        Intent intent = new Intent(this, NewCard.class);
        startActivity(intent);
    }


}