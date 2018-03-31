package com.alexia.callbutton.fragments;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alexia.callbutton.MainActivity;
import com.alexia.callbutton.R;
import com.alexia.callbutton.jsonparsers.HttpHandler;
import com.alexia.callbutton.jsonparsers.QuestionnaireInstruction;

import org.json.JSONException;
import org.json.JSONObject;

import static com.google.android.gms.internal.zzagr.runOnUiThread;

public class QuestionnaireInstructionFragment extends Fragment {
    TextView surveyInstructionTitle;
    TextView surveyInstruction;
    public static String url = "http://192.168.0.103:9090/api/tests/instruction/?count=";
    private String TAG = QuestionnaireSurveyFragment.class.getSimpleName();
    int score = MainActivity.questionnaireResultBundle.getInt("ARG_POINT_SUM");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.questionnaire_survey_end_fragment, container, false);
        surveyInstruction = (TextView) view.findViewById(R.id.survey_instruction);
        surveyInstructionTitle = (TextView) view.findViewById(R.id.survey_instruction_title);

        Button surveyEnd = (Button) view.findViewById(R.id.finish_test_button);
        surveyEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((MainActivity) getActivity()).replaceViewPager(new QuestionnaireSelectionFragment());
            }
        });
        new QuestionnaireInstructionFragment.GetInstruction().execute();
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private class GetInstruction extends AsyncTask<Void, Void, Void> {
        QuestionnaireInstruction instruction;

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            String instructionUrl = url + String.valueOf(score);
            String jsonStr = sh.makeServiceCall(instructionUrl);
            if (jsonStr != null) {
                try {
                    JSONObject c = new JSONObject(jsonStr);
                    instruction = new QuestionnaireInstruction(c);
                    Log.d("JSONValue", String.valueOf(instruction));
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
            surveyInstructionTitle.setText(String.valueOf(instruction.title));
            surveyInstruction.setText(String.valueOf(instruction.instruction));
        }
    }
}
