package com.example.dg_bank;

import static com.example.dg_bank.Data.convertBooltoString;
import static com.example.dg_bank.Data.getStringIndexInArray;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;


public class ZakatFragment extends Fragment {

    Spinner exemption_spinner;
    CheckBox zakat;
    EditText info;
    Button submit;

    public interface AccountOpenResultListener {
        void onAccountOpenResult(boolean success);
    }

    private AccountOpenResultListener accountOpenResultListener;

    public void setAccountOpenResultListener(AccountOpenResultListener listener) {
        this.accountOpenResultListener = listener;
    }



    public ZakatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_zakat, container, false);
        exemption_spinner = rootView.findViewById(R.id.exemption_spinner);
        zakat = rootView.findViewById(R.id.zakatBox);
        info = rootView.findViewById(R.id.info);
        submit = rootView.findViewById(R.id.submitButton);

        ArrayAdapter<CharSequence> adaptor = ArrayAdapter.createFromResource(getContext(), R.array.Exemption_Type, android.R.layout.simple_spinner_dropdown_item);
        exemption_spinner.setAdapter(adaptor);

        exemption_spinner.setEnabled(zakat.isChecked());
        info.setEnabled(zakat.isChecked());

        zakat.setOnClickListener(view -> {
            exemption_spinner.setEnabled(!exemption_spinner.isEnabled());
            info.setEnabled(!info.isEnabled());
        });

        submit.setOnClickListener(view -> new SubmitTask().execute());

        return rootView;
    }

    public void saveData() {
        Data.Zakaat = zakat.isChecked();
        Data.Zakat_Exemption_Type = exemption_spinner.getSelectedItem().toString();
        Data.Additional_Info = info.getText().toString().trim();
    }

    public boolean validate() {
        saveData();
        if (Data.FirstName.equals("") || Data.LastName.equals("") || Data.FatherName.equals("") || Data.MotherName.equals("") || Data.Designation.equals("") || Data.CNIC.equals("") || Data.Phone.equals("") || Data.Mobile.equals("") || Data.Permenant_Address.equals("") || Data.Current_Address.equals("") || Data.City.equals("") || Data.Postal_Code.equals("") || Data.Email.equals("") ||Data.Account_Title.equals("")|| Data.Gender.equals("Select") || Data.Marital_Status.equals("Select") || Data.Education.equals("Select") || Data.BirthPlace.equals("Select") || Data.Nationality.equals("Select") || Data.Residence.equals("Select") || Data.Profession.equals("Select") || Data.Country.equals("Select") || Data.Account_Type.equals("Select") || Data.Currency.equals("Select")) {
            Toast.makeText(getContext(), "All field required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Data.containsOnlyAlphabets(Data.FirstName) || !Data.containsOnlyAlphabets(Data.LastName) || !Data.containsOnlyAlphabets(Data.FatherName) || !Data.containsOnlyAlphabets(Data.MotherName) || !Data.containsOnlyAlphabets(Data.City) || !Data.containsOnlyAlphabets(Data.Designation) || !Data.containsOnlyAlphabets(Data.Account_Title)) {
            Toast.makeText(getContext(), "Incorrect information", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Data.isValidEmail(Data.Email)) {
            Toast.makeText(getContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (zakat.isChecked() && Data.Zakat_Exemption_Type.equals("Select")) {
            Toast.makeText(getContext(), "All fields required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (Data.CNIC.length() < 13) {
            Toast.makeText(getContext(), "CNIC must be of 13 digits", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Data.Phone.length() < 10) {
            Toast.makeText(getContext(), "Phone Number must be of 10 digits", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Data.Mobile.length() < 11) {
            Toast.makeText(getContext(), "Mobile Number must be of 11 digits", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private class SubmitTask extends AsyncTask<Void, Void, Boolean[]>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            submit.setEnabled(false);
        }

        @Override
        protected Boolean[] doInBackground(Void... voids) {
            Boolean[] result = new Boolean[4];
            if (validate()) {
                //Inserting personal information
                String[] columns = {"ID", "User_ID", "FirstName", "SecondName", "FatherName", "MotherName", "Gender", "DOB", "Married", "Nationality", "Education", "Residence", "Profession", "Designation", "CNIC", "CNIC_issue", "CNIC_expiry", "BirthPlace", "Balance"};
                String[] values = {String.valueOf(Data.sqlManager.getNextID("Personal_Info")), Data.CurrentUserID, Data.FirstName, Data.LastName, Data.FatherName, Data.MotherName, getStringIndexInArray(Data.Gender, R.array.Gender, getResources()), Data.DOB, getStringIndexInArray(Data.Marital_Status, R.array.Marital_Status, getResources()), getStringIndexInArray(Data.Nationality, R.array.Countries, getResources()), getStringIndexInArray(Data.Education, R.array.Education, getResources()), getStringIndexInArray(Data.Residence, R.array.Countries, getResources()), getStringIndexInArray(Data.Profession, R.array.Profession, getResources()), Data.Designation, Data.CNIC, Data.CNIC_Issue, Data.CNIC_Expiry, getStringIndexInArray(Data.BirthPlace, R.array.Countries, getResources()), "0"};
                result[0] = Data.sqlManager.insertRow( "Personal_Info", columns, values);

                //Inserting Contact Information
                String[] columns1 = {"ID","User_ID","Country","City","PAddress","CAddress","Postal_Code","Mobile","Phone","Email"};
                String[] values1 = {String.valueOf(Data.sqlManager.getNextID("Contact")), Data.CurrentUserID, getStringIndexInArray(Data.Country, R.array.Countries, getResources()),Data.City, Data.Permenant_Address, Data.Current_Address, Data.Postal_Code, Data.Mobile, Data.Phone, Data.Email };
                result[1] = Data.sqlManager.insertRow( "Contact", columns1, values1);

                //Inserting account information
                String[] columns2 = {"ID","User_ID","Type","Currency","Title","US_Nationality","US_Birth","US_Address","US_link"};
                String[] values2 = {String.valueOf(Data.sqlManager.getNextID("Account")), Data.CurrentUserID, getStringIndexInArray(Data.Account_Type, R.array.Account_Types, getResources()),getStringIndexInArray(Data.Currency, R.array.Currency, getResources()), Data.Account_Title, convertBooltoString(Data.US_National), convertBooltoString(Data.US_BirthPlace), convertBooltoString(Data.US_Address), convertBooltoString(Data.US_Links)};
                result[2] = Data.sqlManager.insertRow( "Account", columns2, values2);

                //Inserting zakaat Information
                String[] columns3 = {"ID","User_ID","Zakat_Exemption","Exemption_type","AdditionalInfo"};
                String[] values3 = {String.valueOf(Data.sqlManager.getNextID("Zakaat")), Data.CurrentUserID, convertBooltoString(Data.Zakaat), getStringIndexInArray(Data.Zakat_Exemption_Type, R.array.Exemption_Type, getResources()), Data.Additional_Info};
                result[3] = Data.sqlManager.insertRow( "Zakaat", columns3, values3);
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean[] booleans) {
            super.onPostExecute(booleans);
            submit.setEnabled(true);
            if (booleans[0] && booleans[1] && booleans[2] && booleans[3]) {
                if (accountOpenResultListener != null) {
                    accountOpenResultListener.onAccountOpenResult(true);
                }
            } else {
                if (accountOpenResultListener != null) {
                    accountOpenResultListener.onAccountOpenResult(false);
                }
            }
        }
    }
}