package com.alexia.callbutton;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class Question {
    int idQuestion;
    String question;
    int points;

    Question(JSONObject json) throws JSONException {
        idQuestion = json.getInt("idQuestion");
        question = json.getString("question");
        points = json.getInt("points");

    }

    @Override
    public String toString() {
        return "idQuestion: " + idQuestion + "\n" +
                "question: " + question + "\n" +
                "points: " + points;
    }
}

public class QuestionnaireSurveyActivity extends AppCompatActivity {

    TextView questionTextView;
    Button button;


    public static String url = "http://your IP here:9090/api/tests/questions/?id=";
    static int currentId = 1;

    private String TAG = QuestionnaireSurveyActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionnaire_survey_layout);

        questionTextView = (TextView) findViewById(R.id.question);
        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    new GetQuestion().execute();

            }
        });

    }


    @SuppressLint("StaticFieldLeak")
    private class GetQuestion extends AsyncTask<Void, Void, Void> {
        ArrayList<Question> objects = new ArrayList<>();
        Question question;
         String error="0";

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
//                    for(int i = 0; i < c.length(); i++) {
//                        Question obj = new Question(c.getJSONObject();
//                        objects.add(obj);
//                    }
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
                                .show() ;
                    }
                });
                this.error = "500";

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(this.error!="500")
            questionTextView.setText((String.valueOf(question.question)));
            else
                questionTextView.setText("Тут перехід на інструкцію");
        }


    }
}


