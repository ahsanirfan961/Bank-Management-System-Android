package com.example.dg_bank;

import android.content.Context;
import android.os.Build;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

public class MyDatePicker extends DatePicker {
    Button date;
    boolean isCalendarOpen;
    public MyDatePicker(Context context) {
        super(context);
        date = new Button(context);
        isCalendarOpen = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.init(2004, 7, 14, new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int years, int months, int days) {
                    months++; // Incrementing months by 1 because it's zero-indexed in DatePicker.

                    String months_string;
                    if (months < 10)
                        months_string = "0" + months;
                    else
                        months_string = String.valueOf(months);
                    String days_string;
                    if (days < 10)
                        days_string = "0" + days;
                    else
                        days_string = String.valueOf(days);

                    date.setText(days_string + "-" + months_string + "-" + years);
                }
            });
        }
    }

    public void setButton(Button button)
    {
        date = button;
    }


}
