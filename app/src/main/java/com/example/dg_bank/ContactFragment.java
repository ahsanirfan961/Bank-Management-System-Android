package com.example.dg_bank;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


public class ContactFragment extends Fragment {

    Spinner country_spinner;
    EditText phone, mobile, paddress, caddress, city, postal_code, email;


    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_contact, container, false);
        country_spinner = rootView.findViewById(R.id.country_spinner);
        phone = rootView.findViewById(R.id.phone_entry);
        mobile = rootView.findViewById(R.id.mobile_entry);
        paddress = rootView.findViewById(R.id.paddress_entry);
        caddress = rootView.findViewById(R.id.caddress_entry);
        city = rootView.findViewById(R.id.city_entry);
        postal_code = rootView.findViewById(R.id.postal_code_entry);
        email = rootView.findViewById(R.id.email_entry);


        ArrayAdapter<CharSequence> adaptor = ArrayAdapter.createFromResource(getContext(),R.array.Countries, android.R.layout.simple_spinner_dropdown_item);
        country_spinner.setAdapter(adaptor);

        return rootView;
    }

    public void saveData()
    {
        Data.Phone = phone.getText().toString().trim();
        Data.Mobile = mobile.getText().toString().trim();
        Data.Permenant_Address = paddress.getText().toString().trim();
        Data.Current_Address = caddress.getText().toString().trim();
        Data.City = city.getText().toString().trim();
        Data.Country = country_spinner.getSelectedItem().toString();
        Data.Email = email.getText().toString().trim();
        Data.Postal_Code = postal_code.getText().toString().trim();
    }
}