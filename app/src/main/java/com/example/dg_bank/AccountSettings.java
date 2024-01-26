package com.example.dg_bank;

import static com.example.dg_bank.Data.getStringIndexInArray;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

public class AccountSettings extends AppCompatActivity {
    Button issuePicker, expiryPicker;
    MyDatePicker issue_datepicker, expiry_datepicker;
    Spinner marital_spinner, education_spinner, residence_spinner, profession_spinner, country_spinner;
    EditText phone_edit, mobile_edit, paddress_edit, caddress_edit, city_edit, pcode_edit, email_edit;
    LinearLayout linearLayout;
    ProgressBar progressBar;
    Button updateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        linearLayout = findViewById(R.id.linearLayout_settings);
        issuePicker = findViewById(R.id.issue_picker_settings);
        expiryPicker = findViewById(R.id.expiry_picker_settings);
        marital_spinner = findViewById(R.id.marital_spinner_settings);
        education_spinner = findViewById(R.id.education_spinner_settings);
        residence_spinner = findViewById(R.id.residence_spinner_settings);
        profession_spinner = findViewById(R.id.profession_spinner_settings);
        country_spinner = findViewById(R.id.country_spinner_settings);
        phone_edit = findViewById(R.id.phone_entry_settings);
        mobile_edit = findViewById(R.id.mobile_entry_settings);
        paddress_edit = findViewById(R.id.paddress_entry_settings);
        caddress_edit = findViewById(R.id.caddress_entry_settings);
        city_edit = findViewById(R.id.city_entry_settings);
        pcode_edit = findViewById(R.id.postal_code_entry_settings);
        email_edit = findViewById(R.id.email_entry_settings);
        progressBar = findViewById(R.id.pg_account_settings);
        updateButton = findViewById(R.id.update_button);

        issue_datepicker = new MyDatePicker(this);
        expiry_datepicker = new MyDatePicker(this);

        issue_datepicker.setButton(issuePicker);
        expiry_datepicker.setButton(expiryPicker);

        issuePicker.setOnClickListener(v -> toggleCalendar(issue_datepicker));
        expiryPicker.setOnClickListener(v -> toggleCalendar(expiry_datepicker));

        updateButton.setOnClickListener(v -> new UpdateTask().execute());

        linearLayout.addView(issue_datepicker, linearLayout.indexOfChild(findViewById(R.id.issue_card_settings)) + 1);
        linearLayout.addView(expiry_datepicker, linearLayout.indexOfChild(findViewById(R.id.expiry_card_settings)) + 1);

        issue_datepicker.getLayoutParams().height = 0;
        issue_datepicker.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        expiry_datepicker.getLayoutParams().height = 0;
        expiry_datepicker.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;

        ArrayAdapter<CharSequence> adaptor;
        adaptor = ArrayAdapter.createFromResource(this, R.array.Marital_Status, android.R.layout.simple_spinner_dropdown_item);
        marital_spinner.setAdapter(adaptor);
        adaptor = ArrayAdapter.createFromResource(this, R.array.Education, android.R.layout.simple_spinner_dropdown_item);
        education_spinner.setAdapter(adaptor);
        adaptor = ArrayAdapter.createFromResource(this, R.array.Countries, android.R.layout.simple_spinner_dropdown_item);
        residence_spinner.setAdapter(adaptor);
        country_spinner.setAdapter(adaptor);
        adaptor = ArrayAdapter.createFromResource(this, R.array.Profession, android.R.layout.simple_spinner_dropdown_item);
        profession_spinner.setAdapter(adaptor);

        new LoadDataTask().execute();
    }

    private class LoadDataTask extends AsyncTask<Void, Void, String[]>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(Void... voids) {
            String[] data = new String[14];
            data[0] = Data.sqlManager.getValue(AccountSettings.this, "Personal_Info", "CNIC_Issue", Data.CurrentUserID);
            data[1] = Data.sqlManager.getValue(AccountSettings.this, "Personal_Info", "CNIC_Expiry", Data.CurrentUserID);
            data[2] = Data.sqlManager.getValue(AccountSettings.this, "Personal_Info", "Married", Data.CurrentUserID);
            data[3] = Data.sqlManager.getValue(AccountSettings.this, "Personal_Info", "Education", Data.CurrentUserID);
            data[4] = Data.sqlManager.getValue(AccountSettings.this, "Personal_Info", "Profession", Data.CurrentUserID);
            data[5] = Data.sqlManager.getValue(AccountSettings.this, "Personal_Info", "Residence", Data.CurrentUserID);
            data[6] = Data.sqlManager.getValue(AccountSettings.this, "Contact", "Phone", Data.CurrentUserID);
            data[7] = Data.sqlManager.getValue(AccountSettings.this, "Contact", "Mobile", Data.CurrentUserID);
            data[8] = Data.sqlManager.getValue(AccountSettings.this, "Contact", "PAddress", Data.CurrentUserID);
            data[9] = Data.sqlManager.getValue(AccountSettings.this, "Contact", "CAddress", Data.CurrentUserID);
            data[10] = Data.sqlManager.getValue(AccountSettings.this, "Contact", "City", Data.CurrentUserID);
            data[11] = Data.sqlManager.getValue(AccountSettings.this, "Contact", "Postal_Code", Data.CurrentUserID);
            data[12] = Data.sqlManager.getValue(AccountSettings.this, "Contact", "Email", Data.CurrentUserID);
            data[13] = Data.sqlManager.getValue(AccountSettings.this, "Contact", "Country", Data.CurrentUserID);
            return data;
        }

        @Override
        protected void onPostExecute(String[] data) {
            super.onPostExecute(data);
            progressBar.setVisibility(View.INVISIBLE);
            String[] dateComponents = data[0].split("-");
            issue_datepicker.updateDate(Integer.parseInt(dateComponents[0]), Integer.parseInt(dateComponents[1])-1, Integer.parseInt(dateComponents[2]));
            dateComponents = data[1].split("-");
            expiry_datepicker.updateDate(Integer.parseInt(dateComponents[0]), Integer.parseInt(dateComponents[1])-1, Integer.parseInt(dateComponents[2]));
            marital_spinner.setSelection(Integer.parseInt(data[2]));
            education_spinner.setSelection(Integer.parseInt(data[3]));
            profession_spinner.setSelection(Integer.parseInt(data[4]));
            residence_spinner.setSelection(Integer.parseInt(data[5]));
            phone_edit.setText(data[6]);
            mobile_edit.setText(data[7]);
            paddress_edit.setText(data[8]);
            caddress_edit.setText(data[9]);
            city_edit.setText(data[10]);
            pcode_edit.setText(data[11]);
            email_edit.setText(data[12]);
            country_spinner.setSelection(Integer.parseInt(data[13]));
        }
    }

    public void toggleCalendar(MyDatePicker datepicker) {
        if (datepicker.isCalendarOpen) {
            // If the calendar is open (height is wrap_content), set it to 0dp
            datepicker.getLayoutParams().height = 0;
            datepicker.isCalendarOpen = false;
        } else {
            // If the calendar is closed (height is 0dp), set it to wrap_content
            datepicker.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            datepicker.isCalendarOpen = true;
        }
        // Request a layout update to apply the height change
        datepicker.requestLayout();
    }

    public void update()
    {
        String CNIC_Expiry = expiryPicker.getText().toString();
        String[] dateComponents = CNIC_Expiry.split("-");
        CNIC_Expiry = dateComponents[2] + "-" + dateComponents[1] + "-" + dateComponents[0];
        String CNIC_Issue = issuePicker.getText().toString();
        dateComponents = CNIC_Issue.split("-");
        CNIC_Issue = dateComponents[2] + "-" + dateComponents[1] + "-" + dateComponents[0];
        String Marital_Status = getStringIndexInArray(marital_spinner.getSelectedItem().toString(), R.array.Marital_Status, getResources());
        String Education = getStringIndexInArray(education_spinner.getSelectedItem().toString(), R.array.Education, getResources());
        String Profession = getStringIndexInArray(profession_spinner.getSelectedItem().toString(), R.array.Profession, getResources());
        String Residence = getStringIndexInArray(residence_spinner.getSelectedItem().toString(), R.array.Countries, getResources());
        String Phone = phone_edit.getText().toString();
        String Mobile = mobile_edit.getText().toString();
        String PAddress = paddress_edit.getText().toString();
        String CAddress = caddress_edit.getText().toString();
        String City = city_edit.getText().toString();
        String Postal_Code = pcode_edit.getText().toString();
        String Email = email_edit.getText().toString();
        String Country = getStringIndexInArray(country_spinner.getSelectedItem().toString(), R.array.Countries, getResources());

        Data.sqlManager.setValue(this, "Personal_Info", "CNIC_Expiry", Data.CurrentUserID, CNIC_Expiry);
        Data.sqlManager.setValue(this, "Personal_Info", "CNIC_Issue", Data.CurrentUserID, CNIC_Issue);
        Data.sqlManager.setValue(this, "Personal_Info", "Married", Data.CurrentUserID, Marital_Status);
        Data.sqlManager.setValue(this, "Personal_Info", "Education", Data.CurrentUserID, Education);
        Data.sqlManager.setValue(this, "Personal_Info", "Profession", Data.CurrentUserID, Profession);
        Data.sqlManager.setValue(this, "Personal_Info", "Residence", Data.CurrentUserID, Residence);
        Data.sqlManager.setValue(this, "Contact", "Phone", Data.CurrentUserID, Phone);
        Data.sqlManager.setValue(this, "Contact", "Mobile", Data.CurrentUserID, Mobile);
        Data.sqlManager.setValue(this, "Contact", "PAddress", Data.CurrentUserID, PAddress);
        Data.sqlManager.setValue(this, "Contact", "CAddress", Data.CurrentUserID, CAddress);
        Data.sqlManager.setValue(this, "Contact", "City", Data.CurrentUserID, City);
        Data.sqlManager.setValue(this, "Contact", "Postal_Code", Data.CurrentUserID, Postal_Code);
        Data.sqlManager.setValue(this, "Contact", "Email", Data.CurrentUserID, Email);
        Data.sqlManager.setValue(this, "Contact", "Country", Data.CurrentUserID, Country);
    }

    private class UpdateTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            updateButton.setEnabled(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            update();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(AccountSettings.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
            updateButton.setEnabled(true);
        }
    }
}