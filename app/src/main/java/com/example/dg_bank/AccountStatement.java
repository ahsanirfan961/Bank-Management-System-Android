package com.example.dg_bank;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AccountStatement extends AppCompatActivity {
    TextView title, address, cnic, id, currency;
    ProgressBar progressBar;
    TableLayout table;
    String[][] transactions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);
        title = findViewById(R.id.title_text);
        address = findViewById(R.id.address_text);
        cnic = findViewById(R.id.cnic_text);
        id = findViewById(R.id.account_id_text);
        currency = findViewById(R.id.currency_text);
        progressBar = findViewById(R.id.pg_statement);
        table = findViewById(R.id.statement_table);
        new UpdateTask().execute();
    }
    private class UpdateTask extends AsyncTask<Void, Void, String[]>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(Void... voids) {
            String[] data = new String[3];
            data[0] = Data.sqlManager.getValue(AccountStatement.this, "Contact", "PAddress", Data.CurrentUserID);
            data[1] = Data.sqlManager.getValue(AccountStatement.this, "Personal_Info", "CNIC", Data.CurrentUserID);
            data[2] = Data.sqlManager.getValue(AccountStatement.this, "Account", "Currency", Data.CurrentUserID);
            ResultSet rs = Data.sqlManager.getQuery("SELECT date, debit, credit, balance FROM Statement WHERE User_ID = '" + Data.CurrentUserID + "'");
            List<String[]> list = new ArrayList<>();
            try {
                while (rs.next()) {
                    String[] temp = new String[4];
                    temp[0] = rs.getString("date");
                    temp[1] = rs.getString("debit");
                    temp[2] = rs.getString("credit");
                    temp[3] = rs.getString("balance");
                    list.add(temp);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            transactions = list.toArray(new String[0][0]);
            return data;
        }

        @Override
        protected void onPostExecute(String[] data) {
            super.onPostExecute(data);
            progressBar.setVisibility(View.INVISIBLE);
            title.setText(Data.CurrentUserName);
            address.setText(data[0]);
            cnic.setText(data[1]);
            id.setText(Data.CurrentUserID);
            currency.setText(getResources().getStringArray(R.array.Currency)[Integer.parseInt(data[2])]);

            TableRow header = new TableRow(AccountStatement.this);
            String[] headerText = {"Sr.no", "Date", "Debit", "Credit", "Balance"};
            for(int i=0;i<5;i++)
            {
                TextView tv = new TextView(AccountStatement.this);
                tv.setText(headerText[i]);
                tv.setBackgroundResource(R.drawable.table_header);
                tv.setPadding(5,5,5,5);
                // Use the same layout parameters for header and data columns
                if (i == 0) {
                    // For the ID column, use WRAP_CONTENT
                    header.addView(tv, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                } else {
                    // For other columns, use 0 width with layout weight
                    header.addView(tv, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                }
            }
            table.addView(header);

            for(int i=0; i<transactions.length;i++)
            {
                TableRow row = new TableRow(AccountStatement.this);
                TextView id = new TextView(AccountStatement.this);
                id.setText(String.valueOf(i+1));
                id.setBackgroundResource(R.drawable.table_cell);
                id.setPadding(5,5,5,5);
                row.addView(id, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                for(int j=0;j<4;j++)
                {
                    TextView tv = new TextView(AccountStatement.this);
                    if(transactions[i][j] == null)
                        transactions[i][j] = "None";
                    tv.setText(transactions[i][j]);
                    tv.setBackgroundResource(R.drawable.table_cell);
                    tv.setPadding(5,5,5,5);
                    row.addView(tv, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                }
                table.addView(row);
            }
        }
    }
}