package com.alexia.callbutton.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.alexia.callbutton.MainActivity;
import com.alexia.callbutton.R;

public class OnboardingActivity extends AppCompatActivity {
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
                    startActivity(main);
                    finish();
                } else if (page == 2) {
                    viewPagerButton.setText("Розпочати");
                }
            }
        });
    }
}