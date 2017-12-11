package com.alexia.callbutton;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by V on 12/9/2017.
 */

public class InfoActivity extends AppCompatActivity{

    TextView mTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // call the super class onCreate to complete the creation of activity like
        // the view hierarchy
        super.onCreate(savedInstanceState);


        // set the user interface layout for this Activity
        // the layout file is defined in the project res/layout/main_activity.xml file
        setContentView(R.layout.card_questionnaire);

        // initialize member TextView so we can manipulate it later
        mTextView = (TextView) findViewById(R.id.textView2);
    }
}

