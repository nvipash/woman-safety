package com.alexia.callbutton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alexia.callbutton.onboarding.OnboardingActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getApplicationContext()
                .getSharedPreferences("onboarding_pref", MODE_PRIVATE);
        if (preferences.getBoolean("first_launch", true)) {
            startActivity(new Intent(SplashActivity.this, OnboardingActivity.class));

            preferences.edit().putBoolean("first_launch", false).apply();
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
        finish();
    }
}