package com.alexia.callbutton;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.alexia.callbutton.Fragment.ButtonFragment;
import com.alexia.callbutton.Fragment.MapsFragment;
import com.alexia.callbutton.Fragment.QuestionnaireFragment;
import com.alexia.callbutton.Fragment.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    private ViewPager viewPager;
    BottomNavigationView bottomNavigationView;

    //Fragments
    QuestionnaireFragment questionnaireFragment;
    ButtonFragment buttonFragment;
    MapsFragment mapsFragment;
    SettingsFragment settingsFragment;
    MenuItem prevMenuItem;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        preferences = MainActivity.this.getSharedPreferences("shared_pref", MODE_PRIVATE);

        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.action_sos);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.action_help:
                                viewPager.setCurrentItem(3);
                                return true;

                            case R.id.action_map:
                                viewPager.setCurrentItem(2);
                                return true;

                            case R.id.action_sos:
                                viewPager.setCurrentItem(0);
                                return true;

                            case R.id.action_settings:
                                viewPager.setCurrentItem(1);
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
                bottomNavigationView.getMenu().getItem(position).setChecked(true);

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

    public void dial(View v) {
        if (isPermissionGranted()) {
            call_action();
        }
    }

    public void call_action() {
        Log.d("phone:", preferences.getString("phone", ""));
        String toDial = "tel:" + preferences.getString("phone", "0933797479");
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(toDial));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
                Log.v("TAG", "Permission is granted");
                return true;
            } else {
                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    call_action();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request

        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
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
