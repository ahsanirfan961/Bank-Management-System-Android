package com.example.dg_bank;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class NewCard extends AppCompatActivity {

    Spinner card_spinner;
    Drawable visa, master, paypak;
    ImageView card_display;
    CheckBox tpin_box;
    EditText tpin_text;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);
        visa = getResources().getDrawable(R.drawable.visa);
        master = getResources().getDrawable(R.drawable.mastercard);
        paypak = getResources().getDrawable(R.drawable.pakpay);
        card_display = findViewById(R.id.card_display);
        card_spinner = findViewById(R.id.card_spinner);
        tpin_box = findViewById(R.id.tpin_box);
        tpin_text = findViewById(R.id.tpin_text);
        tpin_text.setEnabled(false);
        ArrayAdapter<CharSequence> adaptor = ArrayAdapter.createFromResource(this, R.array.Card_Type, android.R.layout.simple_spinner_dropdown_item);
        card_spinner.setAdapter(adaptor);
        card_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (card_spinner.getSelectedItem().toString().equals("Visa Card"))
                {
                    card_display.setImageDrawable(visa);
                }
                else if (card_spinner.getSelectedItem().toString().equals("Master Card"))
                {
                    card_display.setImageDrawable(master);
                }
                else
                {
                    card_display.setImageDrawable(paypak);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tpin_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tpin_text.setEnabled(tpin_box.isChecked());
            }
        });
    }
}