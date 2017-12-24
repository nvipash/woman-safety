package com.alexia.callbutton;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.ArrayList;


public class QuestionnaireSurveyActivity extends AppCompatActivity {

    TextView questionTextView;
    TextView questionIDTextView;

    FloatingActionButton yesButton;
    FloatingActionButton noButton;

    public static String url = "http://192.168.0.102:9090/api/tests/questions/?id=";

    static int currentId = 1;
    Question question;
    int pointSum = 0;

    private String TAG = QuestionnaireSurveyActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionnaire_survey_layout);

        yesButton = (FloatingActionButton) findViewById(R.id.floatingActionButtonYes);
        noButton = (FloatingActionButton) findViewById(R.id.floatingActionButtonNo);

        questionTextView = (TextView) findViewById(R.id.question);
        questionIDTextView = (TextView) findViewById(R.id.question_id);


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
    }


    @SuppressLint("StaticFieldLeak")
    private class GetQuestion extends AsyncTask<Void, Void, Void> {
        Question question;
        Instructions instructions;
        String error = "0";


        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String questionUrl = url + String.valueOf(currentId);
            String jsonStr = sh.makeServiceCall(questionUrl);
            if (jsonStr != null) {
                try {
                    currentId += 1;
                    JSONObject c = new JSONObject(jsonStr);
                    question = new Question(c);

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
                this.error = "500";

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (this.error != "500") {
                questionIDTextView.setText(String.valueOf(question.idQuestion));
                questionTextView.setText(String.valueOf(question.question));
            } else {
                startActivity(new Intent(QuestionnaireSurveyActivity.this, UserScore.class));
//                --- For passing data of "pointSum"
//                Intent intent = new Intent(QuestionnaireSurveyActivity.this, QuestionnaireInstructionActivity.class).putExtra("ARG_POINT_SUM", pointSum);
//                startActivity(intent);
//                Bundle extras = getIntent().getExtras();

//                Log.d("SCORE OF THE TEST", String.valueOf(extras.getInt("ARG_POINT_SUM")));
//                if (currentId > 12)
//                    questionTextView.setText(String.valueOf(instructions.instruction));
//                else
//                    questionTextView.setText("RUN AWAY");
            }
        }

    }


}


