package com.alexia.callbutton.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexia.callbutton.R;

public class QuestionnaireViolenceInfoFragment extends Fragment {
    private String TAG = QuestionnaireViolenceInfoFragment.class.getSimpleName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.questionnaire_info_fragment, container, false);
    }
}