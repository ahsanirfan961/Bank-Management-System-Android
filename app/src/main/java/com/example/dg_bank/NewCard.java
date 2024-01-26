package com.example.dg_bank;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class NewCard extends AppCompatActivity {

    Spinner card_spinner;
    Drawable visa, master, paypak;
    ImageView card_display;
    EditText tpin_text, title;
    ProgressBar progressBar;
    Button order;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);
        visa = getResources().getDrawable(R.drawable.visa);
        master = getResources().getDrawable(R.drawable.master);
        paypak = getResources().getDrawable(R.drawable.pakpay);
        card_display = findViewById(R.id.card_display);
        card_spinner = findViewById(R.id.card_spinner);
        tpin_text = findViewById(R.id.tpin_text);
        title = findViewById(R.id.card_title);
        progressBar = findViewById(R.id.pg_new_card);
        order = findViewById(R.id.order_card);
        new UpdateTask().execute();
        card_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (card_spinner.getSelectedItem().toString().equals("Visa Debit Card"))
                    card_display.setImageDrawable(visa);
                else if (card_spinner.getSelectedItem().toString().equals("Mastercard Debit Card"))
                    card_display.setImageDrawable(master);
                else
                    card_display.setImageDrawable(paypak);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        order.setOnClickListener(view -> {
            if(validate())
                new SaveTask().execute();
        });
    }

    private class UpdateTask extends AsyncTask<Void, Void, boolean[]>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected boolean[] doInBackground(Void... voids) {
            ResultSet rs = Data.sqlManager.getQuery("SELECT Card_Type FROM Card WHERE User_ID = '"+Data.CurrentUserID+"'");
            boolean[] cardExists = new boolean[3];
            for(int i=0;i<3;i++)
                cardExists[i] = true;
            try {
                while(rs.next())
                {
                    if(rs.getString(1).equals("Visa Debit Card"))
                        cardExists[0] = false;
                    else if(rs.getString(1).equals("Mastercard Debit Card"))
                        cardExists[1] = false;
                    else
                        cardExists[2] = false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return cardExists;
        }
        @Override
        protected void onPostExecute(boolean[] cardExists) {
            super.onPostExecute(cardExists);
            progressBar.setVisibility(View.INVISIBLE);
            MyArrayAdapter<String> adapter = new MyArrayAdapter<>(NewCard.this, android.R.layout.simple_dropdown_item_1line,getResources().getStringArray(R.array.Card_Type), cardExists);
            card_spinner.setAdapter(adapter);
            for(int i=0;i<3;i++)
                if(cardExists[i])
                {
                    card_spinner.setSelection(i);
                    break;
                }

        }
    }
    public boolean validate()
    {
        if(tpin_text.getText().toString().equals(""))
        {
            Toast.makeText(this, "Invalid T-Pin", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(title.getText().toString().equals(""))
        {
            Toast.makeText(this, "Invalid Card Title", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private class SaveTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String tpin = tpin_text.getText().toString();
            String card_type = card_spinner.getSelectedItem().toString();
            String card_title = title.getText().toString();
            StringBuilder number = new StringBuilder();
            for(int i=0;i<4;i++)
            {
                number.append((int) (Math.random() * 9999));
                if(i!=3)
                {
                    number.append(" ");
                }
            }
            String cvv = String.valueOf((int) (Math.random() * 999));
            String date = LocalDate.now().toString();
            date = date.substring(2, 4);
            int current_year = Integer.parseInt(date);
            String expr_year = String.valueOf((int) (Math.random() * (99-current_year+1))+current_year);
            int month = (int) (Math.random() * 12) + 1;
            String expr;
            if(month<10)
                 expr = "0"+month+"/"+expr_year;
            else
                 expr = month+"/"+expr_year;
            Data.sqlManager.insertRow("Card", new String[]{"ID", "User_ID", "t_pin_check", "t_pin", "card_title","card_type", "lock", "number", "expr", "cvv"}, new String[]{String.valueOf(Data.sqlManager.getNextID( "Card")), Data.CurrentUserID, "true", tpin, card_title, card_type, "false", number.toString(), expr, cvv});
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(NewCard.this, "Card Ordered Successfully", Toast.LENGTH_SHORT).show();
        }
    }
}