package com.example.dg_bank;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;


public class AccountFragment extends Fragment {

    Spinner account_spinner, currency_spinner;
    CheckBox u1, u2, u3, u4;
    EditText title;

    public AccountFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        account_spinner = rootView.findViewById(R.id.country_spinner);
        currency_spinner = rootView.findViewById(R.id.currency_spinner);
        u1 = rootView.findViewById(R.id.us1);
        u2 = rootView.findViewById(R.id.us2);
        u3 = rootView.findViewById(R.id.us3);
        u4 = rootView.findViewById(R.id.us4);
        title = rootView.findViewById(R.id.account_title);

        ArrayAdapter<CharSequence> adaptor = ArrayAdapter.createFromResource(getContext(),R.array.Account_Types, android.R.layout.simple_spinner_dropdown_item);
        account_spinner.setAdapter(adaptor);
        adaptor = ArrayAdapter.createFromResource(getContext(),R.array.Currency, android.R.layout.simple_spinner_dropdown_item);
        currency_spinner.setAdapter(adaptor);

        return rootView;
    }

    public void saveData()
    {
        Data.Account_Type = account_spinner.getSelectedItem().toString();
        Data.Currency = currency_spinner.getSelectedItem().toString();
        Data.US_National = u1.isChecked();
        Data.US_BirthPlace = u2.isChecked();
        Data.US_Address = u3.isChecked();
        Data.US_Links = u4.isChecked();
        Data.Account_Title = title.getText().toString().trim();
    }
}