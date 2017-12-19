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

/**
 * Created by DELL on 14/12/2017.
 */
public class QuestionnaireChooseActivity extends AppCompatActivity {
    TextView chooseTestDescriptionTextView;

    public static String url = "http://192.168.0.102:9090/api/tests/info";

    private String TAG = QuestionnaireChooseActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionnaire_choose_layout);
        new GetChooseTestDescription().execute();
    }

    public void onClickFirstSurvey(View view) {
        startActivity(new Intent(QuestionnaireChooseActivity.this, QuestionnaireStartActivity.class));
    }

    @SuppressLint("StaticFieldLeak")
    private class GetChooseTestDescription extends AsyncTask<Void, Void, Void> {
        JSONsurveyTitle chooseTestDescription;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            chooseTestDescriptionTextView = (TextView) findViewById(R.id.survey_text_1);
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String chooseTestDescriptionUrl = url;
            String jsonStr = sh.makeServiceCall(chooseTestDescriptionUrl);
            if (jsonStr != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    chooseTestDescription = new JSONsurveyTitle(jsonObject);
                    String.valueOf(jsonObject.getString("survey"));
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                            @Override
                            public void run() {Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();}
                        });
                 }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                        @Override
                        public void run() {Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();}
                    });
                }
                return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            chooseTestDescriptionTextView.setText((String.valueOf(chooseTestDescription.chooseTestDescription)));
        }
    }
}