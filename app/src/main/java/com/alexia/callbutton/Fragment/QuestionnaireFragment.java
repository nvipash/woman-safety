package com.alexia.callbutton.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexia.callbutton.QuestionnaireActivity;
import com.alexia.callbutton.QuestionnaireChooseActivity;
import com.alexia.callbutton.R;

/**
 * Created by nvipash on 22/11/2017.
 */

public class QuestionnaireFragment extends Fragment {

    public QuestionnaireFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.questionnaire_fragment, container, false);
    }

//    public void onClickChooseSurvey(View view) {
//        Intent intentChooseSurvey = new Intent(QuestionnaireFragment.this, QuestionnaireChooseFragment.class);
//        startActivity(intentChooseSurvey);
//    }
}
