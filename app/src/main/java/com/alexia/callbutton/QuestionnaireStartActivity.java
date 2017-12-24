package com.alexia.callbutton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


class QuestionStart {
    String testDescription;

    QuestionStart(JSONObject json) throws JSONException {
        testDescription = json.getString("description");
    }

    @Override
    public String toString() {
        return "description: " + testDescription;
    }
}

public class QuestionnaireStartActivity extends AppCompatActivity {
    TextView testDescriptionTextView;

    public static String url = "http://192.168.214.51:9999/api/tests/info";

    private String TAG = QuestionnaireStartActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionnaire_survey_start_layout);
        new GetTestDescription().execute();
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
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    testDescription = new QuestionStart(jsonObject);
                    String.valueOf(jsonObject.getString("description"));
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
        protected void onPostExecute(Void result) {
            testDescriptionTextView.setText((String.valueOf(testDescription.testDescription)));
        }
    }
}