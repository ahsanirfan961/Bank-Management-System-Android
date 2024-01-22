package com.example.dg_bank;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerMessengerAdaptor extends FragmentPagerAdapter {

    public ViewPagerMessengerAdaptor(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new PersonalInfoFragment();
        else if (position == 1)
            return new ContactFragment();
        else if (position == 2)
            return new AccountFragment();
        else
            return new ZakatFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return "Personal Details";
        else if (position == 1)
            return "Contact Details";
        else if (position == 2)
            return "Account Details";
        else
            return "Zakaat Exemption";
    }
}
