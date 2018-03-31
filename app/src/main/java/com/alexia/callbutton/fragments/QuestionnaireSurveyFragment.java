package com.alexia.callbutton.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alexia.callbutton.MainActivity;
import com.alexia.callbutton.R;
import com.alexia.callbutton.jsonparsers.HttpHandler;
import com.alexia.callbutton.jsonparsers.Questionnaire;

import org.json.JSONException;
import org.json.JSONObject;

import static com.alexia.callbutton.MainActivity.questionnaireResultBundle;
import static com.google.android.gms.internal.zzagr.runOnUiThread;

public class QuestionnaireSurveyFragment extends Fragment {
    private static int currentId = 1;
    private String TAG = QuestionnaireSurveyFragment.class.getSimpleName();
    private TextView questionTextView;
    private TextView questionIDTextView;
    public int pointSum = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.questionnaire_survey_fragment,
                container, false);
        FloatingActionButton yesButton = (FloatingActionButton)
                view.findViewById(R.id.floatingActionButtonYes);
        FloatingActionButton noButton = (FloatingActionButton)
                view.findViewById(R.id.floatingActionButtonNo);

        questionTextView = (TextView) view.findViewById(R.id.question);
        questionIDTextView = (TextView) view.findViewById(R.id.question_id);
        if (getActivity().getIntent().hasExtra("bundle") && savedInstanceState == null) {
            savedInstanceState = getActivity().getIntent().getExtras().getBundle("bundle");
        }

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetQuestion().execute();
                pointSum++;
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        Questionnaire questionnaire;
        String error = "0";

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler handler = new HttpHandler();
            String url = "http://192.168.0.103:9090/api/tests/questions/?id=";
            String questionUrl = url + String.valueOf(currentId);
            String jsonStr = handler.makeServiceCall(questionUrl);
            if (jsonStr != null) {
                try {
                    currentId += 1;
                    JSONObject object = new JSONObject(jsonStr);
                    questionnaire = new Questionnaire(object);

                } catch (final JSONException jsonException) {
                    Log.e(TAG, "Json parsing error: " + jsonException.getMessage());
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
                Log.e(TAG, "Couldn't get json from server.");
                this.error = "500";
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (this.error != "500") {
                questionIDTextView.setText(String.valueOf(questionnaire.idQuestion));
                questionTextView.setText(String.valueOf(questionnaire.question));
            } else {
//                --- For passing data of "pointSum"
                Fragment fragment = new QuestionnaireInstructionFragment();
                questionnaireResultBundle.putInt("ARG_POINT_SUM", pointSum);
                fragment.setArguments(questionnaireResultBundle);
                Log.e("ARG_POINT_SUM", Integer.toString(pointSum));
                ((MainActivity) getActivity()).setCurrentPagerItem(7);
                currentId = 1;
            }
        }
    }
}