package com.alexia.callbutton.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexia.callbutton.MainActivity;
import com.alexia.callbutton.R;

import java.util.Objects;

public class QuestionnaireViolenceInfoFragment extends Fragment {
    private String TAG = QuestionnaireViolenceInfoFragment.class.getSimpleName();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) Objects.requireNonNull(getActivity()))
                .setActionBarTitle(getString(R.string.questionnaire_info_title));
        return inflater.inflate(R.layout.questionnaire_info_fragment, container, false);
    }
}