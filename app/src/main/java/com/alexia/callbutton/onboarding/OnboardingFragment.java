package com.alexia.callbutton.onboarding;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexia.callbutton.R;

public class OnboardingFragment extends Fragment {
    private static final String PAGE = "page";

    private int currentPage;

    public static OnboardingFragment newInstance(int page) {
        OnboardingFragment onboarding = new OnboardingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE, page);
        onboarding.setArguments(bundle);
        return onboarding;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getArguments().containsKey(PAGE))
            throw new RuntimeException("Fragment must contain a \"" + PAGE + "\" argument!");
        currentPage = getArguments().getInt(PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        int layoutResId;
        switch (currentPage) {
            case 0:
                layoutResId = R.layout.onboarding_call_layout;
                break;
            case 1:
                layoutResId = R.layout.onboarding_test_layout;
                break;
            default:
                layoutResId = R.layout.onboarding_map_layout;
        }
        View view = getActivity().getLayoutInflater().inflate(layoutResId,
                container, false);
        view.setTag(currentPage);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View background = view.findViewById(R.id.onboarding_background);
    }
}