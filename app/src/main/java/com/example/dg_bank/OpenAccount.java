package com.example.dg_bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class OpenAccount extends AppCompatActivity implements ZakatFragment.AccountOpenResultListener{
    TabLayout tab;
    ViewPager viewpager;



    @Override
    public void onAccountOpenResult(boolean success) {
        if (success) {
            // Account opened successfully, do any additional handling if needed
            Toast.makeText(this, "Account opened successfully", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK); // Set the result as RESULT_OK to indicate success
            finish();
        } else {
            // Account opening failed or canceled, handle accordingly
            Toast.makeText(this, "Account opening failed or canceled", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED); // Set the result as RESULT_CANCELED to indicate failure
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_account);
        tab = findViewById(R.id.tabLayout);
        viewpager = findViewById(R.id.view_pager);
        ViewPagerMessengerAdaptor adaptor = new ViewPagerMessengerAdaptor(getSupportFragmentManager());
        viewpager.setAdapter(adaptor);
        tab.setupWithViewPager(viewpager);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==3)
                {
                    PersonalInfoFragment PersonalInfofragment = (PersonalInfoFragment) adaptor.instantiateItem(viewpager, 0);
                    PersonalInfofragment.saveData(OpenAccount.this);
                    ContactFragment contact = (ContactFragment)  adaptor.instantiateItem(viewpager, 1);
                    contact.saveData();
                    AccountFragment account = (AccountFragment)  adaptor.instantiateItem(viewpager, 2);
                    account.saveData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // Set the result listener in the ZakatFragment
        ZakatFragment zakatFragment = (ZakatFragment) adaptor.instantiateItem(viewpager, 3);
        zakatFragment.setAccountOpenResultListener(this); // 'this' refers to the OpenAccount activity implementing the AccountOpenResultListener interface
    }
}