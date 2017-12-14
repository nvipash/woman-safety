package com.alexia.callbutton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by DELL on 14/12/2017.
 */

public class QuestionnaireChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.questionnaire_choose_layout);
    }

    public void onClickFirstSurvey(View view) {
        startActivity(new Intent(QuestionnaireChooseActivity.this, QuestionnaireStartActivity.class));
    }
}