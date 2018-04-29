package com.alexia.callbutton.onboarding;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.alexia.callbutton.R;

public class OnboardingPageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        int pagePosition = (int) page.getTag();
        int pageWidth = page.getWidth();
        float pageWidthTimesPosition = pageWidth * position;
        float absPosition = Math.abs(position);
        View title = page.findViewById(R.id.onboarding_title);
        title.setAlpha(1.0f - absPosition);
        View description = page.findViewById(R.id.onboarding_description);
        description.setTranslationY(-pageWidthTimesPosition / 2f);
        description.setAlpha(1.0f - absPosition);
        View image = page.findViewById(R.id.onboarding_image);
        if (pagePosition >= 0 && image != null) {
            image.setAlpha(1.0f - absPosition);
            image.setTranslationX(-pageWidthTimesPosition * 1.5f);
        }
    }
}