package com.alexia.callbutton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


class QuestionStart {
    String testDescription;

    QuestionStart(JSONObject json) throws JSONException {
        testDescription = json.getString("description");
    }

    @Override
    public String toString() {
        return "description: " + testDescription + "\n";
    }
}

public class QuestionnaireStartActivity extends AppCompatActivity {
    TextView testDescriptionTextView;
    public static String url = "http://Your IP here:9090/api/tests/info";

    private String TAG = QuestionnaireStartActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionnaire_survey_start_layout);
    }

    public void onClickStart(View view) {
        startActivity(new Intent(QuestionnaireStartActivity.this, QuestionnaireSurveyActivity.class));
    }

    @SuppressLint("StaticFieldLeak")
    private class GetTestDescription extends AsyncTask<Void, Void, Void> {
        QuestionStart testDescription;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            testDescriptionTextView = (TextView) findViewById(R.id.survey_info);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String testDescriptionUrl = url;
            String jsonStr = sh.makeServiceCall(testDescriptionUrl);
            if (jsonStr != null) {
                try {
                    JSONObject c = new JSONObject(jsonStr);
                    testDescription = new QuestionStart(c);
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
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
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            testDescriptionTextView.setText((String.valueOf(testDescription.testDescription)));
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }
}



