package com.alexia.callbutton.onboarding;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alexia.callbutton.MainActivity;
import com.alexia.callbutton.R;

public class OnboardingActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_LOCATION = 99;
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding_layout);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        final Button viewPagerButton = (Button) findViewById(R.id.viewpager_button);
        viewPager.setAdapter(new OnboardingPagerAdapter(getSupportFragmentManager()));
        viewPager.setPageTransformer(false, new OnboardingPageTransformer());
        viewPagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page++;
                viewPager.setCurrentItem(page);
                if (page > 2) {
                    Intent main = new Intent(OnboardingActivity.this, MainActivity.class);
                    checkLocationPermission();
                    startActivity(main);
                    finish();
                } else if (page == 1) {
                    checkCallSmsPermission();
                } else if (page == 2) {
                    viewPagerButton.setText("Розпочати");
                }
            }
        });
    }

    public boolean checkCallSmsPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.CALL_PHONE, Manifest.permission.READ_CONTACTS,
                                Manifest.permission.SEND_SMS}, 1);
                return false;
            }
        }
        return true;
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(getApplicationContext(),
                        R.string.permission_accepted, Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            R.string.permission_not_accepted, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}