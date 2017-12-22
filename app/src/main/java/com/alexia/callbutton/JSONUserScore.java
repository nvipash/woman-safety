package com.alexia.callbutton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.Toast;


public class UserScore extends QuestionnaireSurveyActivity {

    private static String url = "http://192.168.214.51:9999/api/tests/score/?phone=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new PostScore().execute();

        startActivity(new Intent(UserScore.this, QuestionnaireInstructionActivity.class));
    }

    @SuppressLint("StaticFieldLeak")
    private class PostScore extends AsyncTask<Void, Void, Void> {
        int idScore = 1;
        String userPhone = "0933797479";
        int score = pointSum;


        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String questionUrl = url + String.valueOf(userPhone)+ "&score="+ String.valueOf(score)+"&survey="+String.valueOf(idScore);

            sh.makeServiceCall1(questionUrl);
            return null;
        }
    }
}