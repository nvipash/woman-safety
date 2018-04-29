package com.alexia.callbutton.onboarding;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class OnboardingPagerAdapter extends FragmentPagerAdapter {
    OnboardingPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return OnboardingFragment
                        .newInstance(position);
            case 1:
                return OnboardingFragment
                        .newInstance(position);
            case 2:
                return OnboardingFragment
                        .newInstance(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}