package ru.medeng.mobile.ui.guest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import ru.medeng.mobile.R;


public class AuthFragment extends Fragment {
    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_auth, container, false);

        ViewPager pager = root.findViewById(R.id.pager);
        pager.setAdapter(new PagerAdapter(getParentFragmentManager(), getContext()));

        TabLayout tabs = root.findViewById(R.id.tabs);
        tabs.setupWithViewPager(pager);

        return root;
    }
}