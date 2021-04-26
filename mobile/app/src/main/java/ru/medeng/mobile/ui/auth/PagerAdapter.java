package ru.medeng.mobile.ui.auth;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

import ru.medeng.mobile.R;

public class PagerAdapter extends FragmentPagerAdapter {
    private final String[] titles;

    public PagerAdapter(FragmentManager fm, Context context) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        titles = context.getResources().getStringArray(R.array.auth_pages);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0: return new LoginFragment();
            case 1: return new SignupFragment();
        };
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
         return titles[position];
    }
}
