package com.example.dg_bank;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MyCards extends AppCompatActivity {
    ListView cardList;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cards);
        cardList = findViewById(R.id.card_list);
        progressBar = findViewById(R.id.pg_my_cards);
        new UpdateTask().execute();
    }
    private class UpdateTask extends AsyncTask<Void, Void, Card[]>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Card[] doInBackground(Void... voids) {
            List<Card> cards = new ArrayList<>();
            ResultSet rs = Data.sqlManager.getQuery("SELECT card_title, card_type, lock, number, expr, cvv FROM card WHERE user_id = "+Data.CurrentUserID);
            try {
                while (rs.next())
                {
                    cards.add(new Card(rs.getString("number"), rs.getString("cvv"), rs.getString("expr"), rs.getBoolean("lock"), rs.getString("card_title"), rs.getString("card_type")));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return cards.toArray(new Card[0]);
        }

        @Override
        protected void onPostExecute(Card[] cards) {
            super.onPostExecute(cards);
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            CardAdaptor cardAdaptor = new CardAdaptor(MyCards.this, cards);
            cardList.setAdapter(cardAdaptor);
        }
    }
}