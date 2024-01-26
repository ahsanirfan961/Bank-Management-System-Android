package com.example.dg_bank;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChequeBook extends AppCompatActivity {
    NumberPicker nChequeBooks;
    Spinner nCheques;
    AutoCompleteTextView specialInstructions, standingInstructions;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheque_book);
        nChequeBooks = findViewById(R.id.spinner_cheque_books);
        nCheques = findViewById(R.id.cheques_spinner);
        specialInstructions = findViewById(R.id.special_instructions);
        standingInstructions = findViewById(R.id.standing_instructions);
        progressBar = findViewById(R.id.pg_cheque);
        nChequeBooks.setMinValue(1);
        nChequeBooks.setMaxValue(3);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Cheques, android.R.layout.simple_spinner_dropdown_item);
        nCheques.setAdapter(adapter);
    }

    public void order(View view) {
        new OrderTask().execute();
    }
    
    private class OrderTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String numberOfBooks = String.valueOf(nChequeBooks.getValue());
            String numberOfCheques = String.valueOf(nCheques.getSelectedItem());
            String specialInstructions = String.valueOf(ChequeBook.this.specialInstructions.getText());
            String standingInstructions = String.valueOf(ChequeBook.this.standingInstructions.getText());
            if(specialInstructions.equals("")){
                specialInstructions = "None";
            }
            if(standingInstructions.equals("")){
                standingInstructions = "None";
            }
            Data.sqlManager.insertRow(ChequeBook.this, "Cheque", new String[]{"ID", "User_ID", "No_of_ChequeBook", "checks", "special1", "special2"}, new String[]{String.valueOf(Data.sqlManager.getNextID(ChequeBook.this,"Cheque")), Data.CurrentUserID, numberOfBooks, numberOfCheques, specialInstructions, standingInstructions});
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(ChequeBook.this, "Ordered Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
}