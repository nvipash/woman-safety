package com.alexia.callbutton;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

public class WomanSafetyApp extends Application {
    public int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    protected void attachBaseContext(Context base) {
        //for working with Dalvik Runtime
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}