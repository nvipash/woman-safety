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
    TextView questionTextView;
    TextView questionIDTextView;
    FloatingActionButton yesButton;
    FloatingActionButton noButton;

    public static String url = "http://192.168.0.103:9090/api/tests/questions/?id=";

    static int currentId = 1;
    public int pointSum = 0;

    private String TAG = QuestionnaireSurveyFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.questionnaire_survey_fragment, container, false);
        yesButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButtonYes);
        noButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButtonNo);

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

    @SuppressLint("StaticFieldLeak")
    private class GetQuestion extends AsyncTask<Void, Void, Void> {
        Questionnaire questionnaire;
        String error = "0";

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String questionUrl = url + String.valueOf(currentId);
            String jsonStr = sh.makeServiceCall(questionUrl);
            if (jsonStr != null) {
                try {
                    currentId += 1;
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    questionnaire = new Questionnaire(jsonObject);

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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getActivity().getApplicationContext(),
//                                "Couldn't get json from server. Check LogCat for possible errors!",
//                                Toast.LENGTH_LONG)
//                                .show();
                    }
                });
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
                ((MainActivity) getActivity()).replaceViewPager(new QuestionnaireInstructionFragment());
            }
        }

    }
}
