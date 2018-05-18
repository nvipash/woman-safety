package com.alexia.callbutton.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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
import com.alexia.callbutton.jsonparsers.QuestionnaireSelection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.google.android.gms.internal.zzagr.runOnUiThread;

public class QuestionnaireFragment extends Fragment {
    private TextView testDescriptionTextView;
    private String TAG = QuestionnaireFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.questionnaire_fragment,
                container, false);
        testDescriptionTextView = view.findViewById(R.id.survey_text_1);
        new GetChooseTestDescription().execute();
        ((MainActivity) getActivity()).setActionBarTitle("Оберіть опитування");
        CardView firstTest = view.findViewById(R.id.surveyCard_1);
        firstTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).replaceWithStack(new QuestionnaireStartFragment());
                ((MainActivity) getActivity()).hideActionBar();
            }
        });

        Button violenceInfoButton = view.findViewById(R.id.violence_info_button);
        violenceInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity())
                        .replaceWithStack(new QuestionnaireViolenceInfoFragment());
            }
        });
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private class GetChooseTestDescription extends AsyncTask<Void, Void, Void> {
        QuestionnaireSelection chooseTestDescription;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler handler = new HttpHandler();
            String chooseTestDescriptionUrl = getString(R.string.url_questionnaire_description);
            String jsonStr = handler.makeServiceCall(chooseTestDescriptionUrl);
            if (jsonStr != null) {
                try {
                    JSONArray array = new JSONArray(jsonStr);
                    JSONObject object = array.getJSONObject(0);
                    chooseTestDescription = new QuestionnaireSelection(object);
                    String.valueOf(object.getString("survey"));
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
            testDescriptionTextView.setText((String.valueOf
                    (chooseTestDescription.chooseTestDescription)));
        }
    }
}