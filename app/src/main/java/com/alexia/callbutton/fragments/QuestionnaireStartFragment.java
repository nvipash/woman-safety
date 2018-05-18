package com.alexia.callbutton.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alexia.callbutton.MainActivity;
import com.alexia.callbutton.jsonparsers.HttpHandler;
import com.alexia.callbutton.jsonparsers.QuestionnaireStart;
import com.alexia.callbutton.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.google.android.gms.internal.zzagr.runOnUiThread;

public class QuestionnaireStartFragment extends Fragment {
    private TextView testDescriptionTextView;
    private String TAG = QuestionnaireStartFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.questionnaire_survey_start_fragment,
                container, false);
        testDescriptionTextView = (TextView) view.findViewById(R.id.survey_info);
        new GetTestDescription().execute();
        ((MainActivity) getActivity()).hideActionBar();
        Button surveyStart = (Button) view.findViewById(R.id.survey_start);
        surveyStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).replaceBottomNavWithoutStack
                        (new QuestionnaireSurveyFragment());
            }
        });
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private class GetTestDescription extends AsyncTask<Void, Void, Void> {
        QuestionnaireStart testDescription;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler handler = new HttpHandler();
            String testDescriptionUrl = getString(R.string.url_questionnaire_start);
            String jsonStr = handler.makeServiceCall(testDescriptionUrl);
            if (jsonStr != null) {
                try {
                    JSONArray array = new JSONArray(jsonStr);
                    JSONObject object = array.getJSONObject(0);
                    testDescription = new QuestionnaireStart(object);
                    String.valueOf(object.getString("description"));
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
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
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            testDescriptionTextView.setText((String.valueOf(testDescription.testDescription)));
        }
    }
}