package com.example.dg_bank;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

public class PersonalInfoFragment extends Fragment {

    Button dobPicker, issuePicker, expiryPicker;
    MyDatePicker DOB_datePicker, issue_datePicker, expiry_datePicker;
    LinearLayout linearLayout;
    Spinner gender_spinner, marital_spinner, education_spinner, birth_place_spinner, nationality_spinner, residence_spinner, profession_spinner;
    EditText fname_edit, lname_edit, mname_edit, fathername_edit, designation_entry, cnic_edit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personal_info, container, false);
        linearLayout = rootView.findViewById(R.id.linearLayout);
        dobPicker = rootView.findViewById(R.id.DOB_picker);
        issuePicker = rootView.findViewById(R.id.issue_picker);
        expiryPicker = rootView.findViewById(R.id.expiry_picker);
        gender_spinner = rootView.findViewById(R.id.gender_spinner);
        marital_spinner = rootView.findViewById(R.id.marital_spinner);
        education_spinner = rootView.findViewById(R.id.education_spinner);
        birth_place_spinner = rootView.findViewById(R.id.birth_place_spinner);
        nationality_spinner = rootView.findViewById(R.id.nationality_spinner);
        residence_spinner = rootView.findViewById(R.id.residence_spinner);
        profession_spinner = rootView.findViewById(R.id.profession_spinner);
        fname_edit = rootView.findViewById(R.id.fname_edit);
        lname_edit = rootView.findViewById(R.id.lname_edit);
        mname_edit = rootView.findViewById(R.id.mname_edit);
        fathername_edit = rootView.findViewById(R.id.fathername_edit);
        cnic_edit = rootView.findViewById(R.id.cnic_edit);
        designation_entry = rootView.findViewById(R.id.designation_entry);

        DOB_datePicker = new MyDatePicker(getContext());
        issue_datePicker = new MyDatePicker(getContext());
        expiry_datePicker = new MyDatePicker(getContext());

        DOB_datePicker.setButton(dobPicker);
        issue_datePicker.setButton(issuePicker);
        expiry_datePicker.setButton(expiryPicker);

        dobPicker.setOnClickListener(v -> toggleCalendar(DOB_datePicker));
        issuePicker.setOnClickListener(v -> toggleCalendar(issue_datePicker));
        expiryPicker.setOnClickListener(v -> toggleCalendar(expiry_datePicker));


        linearLayout.addView(DOB_datePicker, linearLayout.indexOfChild(rootView.findViewById(R.id.DOB_card)) + 1);
        linearLayout.addView(issue_datePicker, linearLayout.indexOfChild(rootView.findViewById(R.id.issue_card)) + 1);
        linearLayout.addView(expiry_datePicker, linearLayout.indexOfChild(rootView.findViewById(R.id.expiry_card)) + 1);

        DOB_datePicker.getLayoutParams().height = 0;
        DOB_datePicker.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        issue_datePicker.getLayoutParams().height = 0;
        issue_datePicker.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        expiry_datePicker.getLayoutParams().height = 0;
        expiry_datePicker.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;


        ArrayAdapter<CharSequence> adaptor = ArrayAdapter.createFromResource(getContext(),R.array.Gender, android.R.layout.simple_spinner_dropdown_item);
        gender_spinner.setAdapter(adaptor);
        adaptor = ArrayAdapter.createFromResource(getContext(), R.array.Marital_Status, android.R.layout.simple_spinner_dropdown_item);
        marital_spinner.setAdapter(adaptor);
        adaptor = ArrayAdapter.createFromResource(getContext(), R.array.Education, android.R.layout.simple_spinner_dropdown_item);
        education_spinner.setAdapter(adaptor);
        adaptor = ArrayAdapter.createFromResource(getContext(), R.array.Countries, android.R.layout.simple_spinner_dropdown_item);
        birth_place_spinner.setAdapter(adaptor);
        adaptor = ArrayAdapter.createFromResource(getContext(), R.array.Countries, android.R.layout.simple_spinner_dropdown_item);
        nationality_spinner.setAdapter(adaptor);
        adaptor = ArrayAdapter.createFromResource(getContext(), R.array.Countries, android.R.layout.simple_spinner_dropdown_item);
        residence_spinner.setAdapter(adaptor);
        adaptor = ArrayAdapter.createFromResource(getContext(), R.array.Profession, android.R.layout.simple_spinner_dropdown_item);
        profession_spinner.setAdapter(adaptor);
        return rootView;
    }

    public void toggleCalendar(MyDatePicker datePicker) {
        if (datePicker.isCalendarOpen) {
            // If the calendar is open (height is wrap_content), set it to 0dp
            datePicker.getLayoutParams().height = 0;
            datePicker.isCalendarOpen = false;
        } else {
            // If the calendar is closed (height is 0dp), set it to wrap_content
            datePicker.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            datePicker.isCalendarOpen = true;
        }
        // Request a layout update to apply the height change
        datePicker.requestLayout();
    }

    public void saveData() {

        Data.FirstName = fname_edit.getText().toString().trim();
        Data.LastName = lname_edit.getText().toString().trim();
        Data.FatherName = fathername_edit.getText().toString().trim();
        Data.MotherName = mname_edit.getText().toString().trim();
        Data.CNIC = cnic_edit.getText().toString().trim();
        Data.Designation = designation_entry.getText().toString().trim();
        Data.DOB = dobPicker.getText().toString();
        Data.CNIC_Expiry = expiryPicker.getText().toString();
        Data.CNIC_Issue = issuePicker.getText().toString();
        Data.Gender = gender_spinner.getSelectedItem().toString();
        Data.Marital_Status = marital_spinner.getSelectedItem().toString();
        Data.Education = education_spinner.getSelectedItem().toString();
        Data.Profession = profession_spinner.getSelectedItem().toString();
        Data.Residence = residence_spinner.getSelectedItem().toString();
        Data.BirthPlace = birth_place_spinner.getSelectedItem().toString();
        Data.Nationality = nationality_spinner.getSelectedItem().toString();
    }


}





