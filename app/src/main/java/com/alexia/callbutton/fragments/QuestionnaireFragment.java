package com.alexia.callbutton.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alexia.callbutton.MainActivity;
import com.alexia.callbutton.R;

public class QuestionnaireFragment extends Fragment {

    public QuestionnaireFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.questionnaire_fragment, container, false);
        Button selectionTest = (Button) view.findViewById(R.id.survey_start);
        selectionTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ((MainActivity) getActivity()).setCurrentPagerItem(4);
            }
        });
        return view;
    }
}
