package com.alexia.callbutton;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.alexia.callbutton.fragments.ButtonFragment;
import com.alexia.callbutton.fragments.MapsFragment;
import com.alexia.callbutton.fragments.QuestionnaireFragment;
import com.alexia.callbutton.fragments.QuestionnaireInstructionFragment;
import com.alexia.callbutton.fragments.QuestionnaireSelectionFragment;
import com.alexia.callbutton.fragments.QuestionnaireStartFragment;
import com.alexia.callbutton.fragments.QuestionnaireSurveyFragment;
import com.alexia.callbutton.fragments.SettingsFragment;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    private static boolean activityPass;
    private ViewPager viewPager;
    public static Bundle questionnaireResultBundle = new Bundle();
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = MainActivity.this.getSharedPreferences("shared_pref", MODE_PRIVATE);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.bottomnav);
        bottomNav.setSelectedItemId(R.id.action_sos);
        setUpBottomNavigationView(viewPager, bottomNav);
        removeShiftMode(bottomNav);
    }

    public void useUserScore(final Fragment fragment) {
        final FragmentManager supportFragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction()
                .replace(R.id.viewpager_container, fragment);
        if (activityPass) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
        activityPass = true;
    }

    public void setCurrentPagerItem(int item) {
        viewPager.setCurrentItem(item, false);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() >= 0 && viewPager.getCurrentItem() <= 3) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, false);
            BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.bottomnav);
            bottomNav.setSelectedItemId(R.id.action_help);
        }
    }

    public void dial(View v) {
        if (isPermissionGranted()) {
            callAction();
        }
    }

    public void callAction() {
        String toDial = "tel:" + preferences.getString("phone", "0933797479");
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(toDial));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
    }

    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }//permission is automatically granted on sdk<23 upon installation
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            "Permission granted", Toast.LENGTH_SHORT).show();
                    callAction();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @SuppressLint("RestrictedApi")
    public static void removeShiftMode(final BottomNavigationView bottomNav) {
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
        } catch (NoSuchFieldException ignored) {
        } catch (IllegalAccessException ignored) {
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUpBottomNavigationView(final ViewPager viewPager,
                                           final BottomNavigationView bottomNav) {

        bottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_sos:
                                viewPager.setCurrentItem(0, false);
                                return true;
                            case R.id.action_settings:
                                viewPager.setCurrentItem(1, false);
                                return true;
                            case R.id.action_map:
                                viewPager.setCurrentItem(2, false);
                                return true;
                            case R.id.action_help:
                                viewPager.setCurrentItem(3, false);
                                return true;
                        }
                        return true;
                    }
                });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position < 4) {
                    bottomNav.getMenu().getItem(position).setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setUpViewPager(viewPager);
    }

    public void setUpViewPager(final ViewPager viewPager) {
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        QuestionnaireFragment questionnaireFragment = new QuestionnaireFragment();
        ButtonFragment buttonFragment = new ButtonFragment();
        MapsFragment mapsFragment = new MapsFragment();
        SettingsFragment settingsFragment = new SettingsFragment();
        QuestionnaireSelectionFragment selectionFragment = new QuestionnaireSelectionFragment();
        QuestionnaireStartFragment startFragment = new QuestionnaireStartFragment();
        QuestionnaireSurveyFragment surveyFragment = new QuestionnaireSurveyFragment();
        QuestionnaireInstructionFragment instructionFragment
                = new QuestionnaireInstructionFragment();
        adapter.addFragment(buttonFragment);
        adapter.addFragment(settingsFragment);
        adapter.addFragment(mapsFragment);
        adapter.addFragment(questionnaireFragment);
        adapter.addFragment(selectionFragment);
        adapter.addFragment(startFragment);
        adapter.addFragment(surveyFragment);
        adapter.addFragment(instructionFragment);
        viewPager.setAdapter(adapter);
    }
}