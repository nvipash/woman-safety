package com.alexia.callbutton.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexia.callbutton.R;

/**
 * Created by Alexia on 05.11.2017.
 */

public class ButtonFragment extends Fragment {

    public ButtonFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.button_fragment, container, false);

    }
}
