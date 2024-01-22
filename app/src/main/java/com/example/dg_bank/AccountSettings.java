package com.example.dg_bank;

import static com.example.dg_bank.Data.getStringIndexInArray;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class AccountSettings extends AppCompatActivity {
    Button issuePicker, expiryPicker;
    MyDatePicker issue_datepicker, expiry_datepicker;
    Spinner marital_spinner, education_spinner, residence_spinner, profession_spinner, country_spinner;
    EditText phone_edit, mobile_edit, paddress_edit, caddress_edit, city_edit, pcode_edit, email_edit;
    LinearLayout linearLayout;

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

        issue_datepicker = new MyDatePicker(this);
        expiry_datepicker = new MyDatePicker(this);

        issue_datepicker.setButton(issuePicker);
        expiry_datepicker.setButton(expiryPicker);

        issuePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCalendar(issue_datepicker);
            }
        });
        expiryPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCalendar(expiry_datepicker);
            }
        });

        linearLayout.addView(issue_datepicker, linearLayout.indexOfChild(findViewById(R.id.issue_card_settings)) + 1);
        linearLayout.addView(expiry_datepicker, linearLayout.indexOfChild(findViewById(R.id.expiry_card_settings)) + 1);

        issue_datepicker.getLayoutParams().height = 0;
        issue_datepicker.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        expiry_datepicker.getLayoutParams().height = 0;
        expiry_datepicker.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;

        String issueDate = Data.sqlManager.getValue(this, "Personal_Info", "CNIC_Issue", Data.CurrentUserID);

        String[] dateComponents = issueDate.split("-");
        issue_datepicker.updateDate(Integer.parseInt(dateComponents[0]), Integer.parseInt(dateComponents[1])-1, Integer.parseInt(dateComponents[2]));

        String expiryDate = Data.sqlManager.getValue(this, "Personal_Info", "CNIC_Expiry", Data.CurrentUserID);
        dateComponents = expiryDate.split("-");
        expiry_datepicker.updateDate(Integer.parseInt(dateComponents[0]), Integer.parseInt(dateComponents[1])-1, Integer.parseInt(dateComponents[2]));

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


        profession_spinner.setSelection(Integer.parseInt(Data.sqlManager.getValue(this, "Personal_Info", "Profession", Data.CurrentUserID)));
        marital_spinner.setSelection(Integer.parseInt(Data.sqlManager.getValue(this, "Personal_Info", "Married", Data.CurrentUserID)));
        education_spinner.setSelection(Integer.parseInt(Data.sqlManager.getValue(this, "Personal_Info", "Education", Data.CurrentUserID)));
        residence_spinner.setSelection(Integer.parseInt(Data.sqlManager.getValue(this, "Personal_Info", "Residence", Data.CurrentUserID)));
        country_spinner.setSelection(Integer.parseInt(Data.sqlManager.getValue(this, "Contact", "Country", Data.CurrentUserID)));

        phone_edit.setText(Data.sqlManager.getValue(this, "Contact", "Phone", Data.CurrentUserID));
        mobile_edit.setText(Data.sqlManager.getValue(this, "Contact", "Mobile", Data.CurrentUserID));
        paddress_edit.setText(Data.sqlManager.getValue(this, "Contact", "PAddress", Data.CurrentUserID));
        caddress_edit.setText(Data.sqlManager.getValue(this, "Contact", "CAddress", Data.CurrentUserID));
        city_edit.setText(Data.sqlManager.getValue(this, "Contact", "City", Data.CurrentUserID));
        pcode_edit.setText(Data.sqlManager.getValue(this, "Contact", "Postal_Code", Data.CurrentUserID));
        email_edit.setText(Data.sqlManager.getValue(this, "Contact", "Email", Data.CurrentUserID));
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

    public void update(View view)
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

        Toast.makeText(this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
    }
}