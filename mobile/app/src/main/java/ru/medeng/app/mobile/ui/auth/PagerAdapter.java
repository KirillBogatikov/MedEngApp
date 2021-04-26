package ru.medeng.app.mobile.ui.auth;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

import ru.medeng.app.mobile.R;

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
        int id = 0;
        switch(position) {
            case 0: id = R.layout.fragment_login; break;
            case 1: id = R.layout.fragment_signup; break;
        };
        return PageFragment.newInstance(id);
    }

    @Override
    public CharSequence getPageTitle(int position) {
         return titles[position];
    }
}
