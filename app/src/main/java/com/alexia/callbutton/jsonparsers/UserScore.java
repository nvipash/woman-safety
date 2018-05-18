package com.alexia.callbutton.jsonparsers;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import com.alexia.callbutton.MainActivity;
import com.alexia.callbutton.R;
import com.alexia.callbutton.fragments.QuestionnaireInstructionFragment;
import com.alexia.callbutton.fragments.QuestionnaireSurveyFragment;

public class UserScore extends QuestionnaireSurveyFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new PostScore().execute();
        ((MainActivity) getActivity()).replaceWithStack(new QuestionnaireInstructionFragment());
    }

    @SuppressLint("StaticFieldLeak")
    private class PostScore extends AsyncTask<Void, Void, Void> {
        int idScore = 1;
        String userPhone = "0933797479";
        int score = pointSum;

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String url = getString(R.string.url_user_score);
            String questionUrl = url + String.valueOf(userPhone) + "&score="
                    + String.valueOf(score) + "&survey=" + String.valueOf(idScore);
            sh.makeServiceCall1(questionUrl);
            return null;
        }
    }
}