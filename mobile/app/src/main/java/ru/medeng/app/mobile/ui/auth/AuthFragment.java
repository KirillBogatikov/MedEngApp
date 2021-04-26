package ru.medeng.app.mobile.ui.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import ru.medeng.app.mobile.R;

public class AuthFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_auth, container, false);

        ViewPager pager = root.findViewById(R.id.pager);
        pager.setAdapter(new PagerAdapter(getParentFragmentManager(), getContext()));

        TabLayout tabs = root.findViewById(R.id.tabs);
        tabs.setupWithViewPager(pager);

        return root;
    }
}