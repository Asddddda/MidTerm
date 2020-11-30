package com.own.midterm.presenter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.own.midterm.view.AccountFragment;
import com.own.midterm.view.ClazzFragment;
import com.own.midterm.view.MainFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new ClazzFragment();
        } else if (position == 1){
            return new MainFragment();
        } else {
            return new AccountFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
