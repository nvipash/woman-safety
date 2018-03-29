package com.alexia.callbutton.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alexia.callbutton.R;
import com.alexia.callbutton.ViewPagerAdapter;

import java.lang.reflect.Field;


public class BottomNavigationFragment extends Fragment {
    //Fragments
    QuestionnaireFragment questionnaireFragment;
    ButtonFragment buttonFragment;
    MapsFragment mapsFragment;
    SettingsFragment settingsFragment;

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomnavigationview_fragment, container, false);
        BottomNavigationView bottomNav = (BottomNavigationView) view.findViewById(R.id.bottomnav);
        bottomNav.setSelectedItemId(R.id.action_sos);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setUpViewPager(viewPager, bottomNav);
        removeShiftMode(bottomNav);
        return view;
    }

    @SuppressLint("RestrictedApi")
    public static void removeShiftMode(BottomNavigationView bottomNav) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNav.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BottomNav", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BottomNav", "Unable to change value of shift mode", e);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUpViewPager(final ViewPager viewPager, final BottomNavigationView bottomNav) {

        bottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.action_help:
                                viewPager.setCurrentItem(3, false);
                                return true;

                            case R.id.action_map:
                                viewPager.setCurrentItem(2, false);
                                return true;

                            case R.id.action_sos:
                                viewPager.setCurrentItem(0, false);
                                return true;

                            case R.id.action_settings:
                                viewPager.setCurrentItem(1, false);
                                return true;
                        }
                        return true;
                    }
                });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNav.getMenu().getItem(position).setChecked(true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        setupViewPager(viewPager);
    }

    public void setupViewPager(ViewPager viewPager) {
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        questionnaireFragment = new QuestionnaireFragment();
        buttonFragment = new ButtonFragment();
        mapsFragment = new MapsFragment();
        settingsFragment = new SettingsFragment();
        adapter.addFragment(buttonFragment);
        adapter.addFragment(settingsFragment);
        adapter.addFragment(mapsFragment);
        adapter.addFragment(questionnaireFragment);
        viewPager.setAdapter(adapter);
    }
}
