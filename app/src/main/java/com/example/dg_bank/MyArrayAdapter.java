package com.example.dg_bank;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.Arrays;

public class MyArrayAdapter<T> extends ArrayAdapter<T> {

    private boolean[] isEnabledArray;

    public MyArrayAdapter(Context context, int resource, T[] objects, boolean[] isEnabledArray) {
        super(context, resource, objects);
        this.isEnabledArray = isEnabledArray;
    }
    @Override
    public boolean isEnabled(int position) {
        return isEnabledArray[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Your custom implementation for the appearance of each item
        // This is just a basic example, you might want to customize it based on your needs
        return super.getView(position, convertView, parent);
    }
}
