package com.alexia.callbutton.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alexia.callbutton.R;

public class DangerDescriptionFragment extends android.support.v4.app.Fragment {
    private String TAG = DangerDescriptionFragment.class.getSimpleName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_fragment, container, false);
        return view;
    }
}
