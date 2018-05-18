package com.alexia.callbutton.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alexia.callbutton.MainActivity;
import com.alexia.callbutton.R;
import com.alexia.callbutton.WomanSafetyApp;
import com.alexia.callbutton.jsonparsers.HttpHandler;
import com.alexia.callbutton.jsonparsers.Questionnaire;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import static com.google.android.gms.internal.zzagr.runOnUiThread;

public class QuestionnaireSurveyFragment extends Fragment {
    private static int CURRENT_ID = 1;
    private String TAG = QuestionnaireSurveyFragment.class.getSimpleName();
    private ProgressBar progressBar;
    private TextView questionTextView;
    private TextView questionIDTextView;
    public int pointSum = 0;
    private Questionnaire questionnaire = new Questionnaire();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.questionnaire_survey_fragment,
                container, false);
        ImageButton oftenButton = (ImageButton) view.findViewById(R.id.often_button);
        ImageButton neverButton = (ImageButton) view.findViewById(R.id.never_button);
        ImageButton seldomButton = (ImageButton) view.findViewById(R.id.seldom_button);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setMax(16);
        progressBar.setProgress(0);
        questionTextView = (TextView) view.findViewById(R.id.question);
        questionIDTextView = (TextView) view.findViewById(R.id.question_id);
        ((MainActivity) getActivity()).hideActionBar();
        if (getActivity().getIntent().hasExtra("bundle") && savedInstanceState == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                savedInstanceState = Objects.requireNonNull(getActivity()
                        .getIntent().getExtras()).getBundle("bundle");
            }
        }

        oftenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pointSum = pointSum + questionnaire.points_often;
                new GetQuestion().execute();
            }
        });
        neverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pointSum = pointSum + questionnaire.points_never;
                new GetQuestion().execute();
            }
        });
        seldomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pointSum = pointSum + questionnaire.points_seldom;
                new GetQuestion().execute();
            }
        });
        new GetQuestion().execute();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @SuppressLint("StaticFieldLeak")
    private class GetQuestion extends AsyncTask<Void, Void, Void> {
        String error = "0";

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler handler = new HttpHandler();
            String url = getString(R.string.url_questionnaire_survey);
            String questionUrl = url + String.valueOf(CURRENT_ID);
            String jsonStr = handler.makeServiceCall(questionUrl);
            if (jsonStr != null) {
                try {
                    CURRENT_ID += 1;
                    progressBar.setProgress(CURRENT_ID);
                    JSONObject object = new JSONObject(jsonStr);
                    questionnaire = new Questionnaire(object);
                } catch (final JSONException jsonException) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Json parsing error: " + jsonException.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                this.error = "500";
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (!this.error.equals("500")) {
                questionIDTextView.setText(String.valueOf(questionnaire.idQuestion));
                questionTextView.setText(String.valueOf(questionnaire.question));
            } else {
                final WomanSafetyApp application = (WomanSafetyApp) getContext()
                        .getApplicationContext();
                application.setScore(pointSum);
                ((MainActivity) getActivity())
                        .replaceBottomNavWithoutStack(new QuestionnaireInstructionFragment());
                CURRENT_ID = 1;
                pointSum = 0;
            }
        }
    }
}