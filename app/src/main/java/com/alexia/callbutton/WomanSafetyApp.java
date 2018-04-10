package com.alexia.callbutton;

import android.app.Application;

import java.util.ArrayList;

public class WomanSafetyApp extends Application {
    public int score;
    public ArrayList<String> phones;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<String> getPhones() {
        return phones;
    }

    public void setPhones(ArrayList<String> phones) {
        this.phones = phones;
    }
}