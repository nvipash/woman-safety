package com.alexia.callbutton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class QuestionnaireInstructionActivity extends AppCompatActivity {

    TextView surveyInstructionTitle;
    TextView surveyInstruction;
    public static String url = "http://192.168.43.186:9090/api/tests/instruction/?count=";
    private String TAG = QuestionnaireSurveyActivity.class.getSimpleName();
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionnaire_instruction_layout);
        surveyInstruction = (TextView) findViewById(R.id.survey_instruction);
        surveyInstructionTitle = (TextView) findViewById(R.id.survey_instruction_title);
        new GetInstruction().execute();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_help);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_help: {
                                Intent intent1 = new Intent(QuestionnaireInstructionActivity.this, QuestionnaireActivity.class);
                                QuestionnaireInstructionActivity.this.startActivity(intent1);
                            }
                            break;

                            case R.id.action_map: {
                                Intent intent3 = new Intent(QuestionnaireInstructionActivity.this, MapsActivity.class);
                                QuestionnaireInstructionActivity.this.startActivity(intent3);
                            }
                            break;

                            case R.id.action_sos: {
                                Intent intent2 = new Intent(QuestionnaireInstructionActivity.this, MainActivity.class);
                                QuestionnaireInstructionActivity.this.startActivity(intent2);
                            }
                            break;
                            case R.id.action_settings: {
                                Intent intent2 = new Intent(QuestionnaireInstructionActivity.this, SettingsActivity.class);
                                QuestionnaireInstructionActivity.this.startActivity(intent2);
                            }
                            break;
                        }
                        return true;
                    }
                });
    }

    public void onClickFinishTest(View view) {
        Toast questionnaireFinishedInfo = Toast.makeText(getApplicationContext(),
                "Опитування успішно завершено", Toast.LENGTH_SHORT);
        questionnaireFinishedInfo.show();
        startActivity(new Intent(QuestionnaireInstructionActivity.this, QuestionnaireChooseActivity.class));
    }

    @SuppressLint("StaticFieldLeak")
    private class GetInstruction extends AsyncTask<Void, Void, Void> {
        Instructions instruction;

        @Override
        protected Void doInBackground(Void... arg0) {
            //    --- For passing data
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                score = extras.getInt("ARG_POINT_SUM");
            }
            HttpHandler sh = new HttpHandler();
            String instructionUrl = url + String.valueOf(score);
            String jsonStr = sh.makeServiceCall(instructionUrl);
            if (jsonStr != null) {
                try {
                    JSONObject c = new JSONObject(jsonStr);
                    instruction = new Instructions(c);
                    Log.d("JSONValue", String.valueOf(instruction));
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
            surveyInstructionTitle.setText(String.valueOf(instruction.title));
            surveyInstruction.setText(String.valueOf(instruction.instruction));
        }
    }
}
