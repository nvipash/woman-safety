package com.alexia.callbutton;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class QuestionnaireActivity extends FragmentActivity {

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionnaite_activity);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_help);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            //case R.id.action_help:
                            case R.id.action_sos: {
                                Intent intent1 = new Intent(QuestionnaireActivity.this, MainActivity.class);
                                QuestionnaireActivity.this.startActivity(intent1);
                            }
                            break;

                            //case R.id.action_map:
                            case R.id.action_settings: {
                                Intent intent2 = new Intent(QuestionnaireActivity.this, SettingsActivity.class);
                                QuestionnaireActivity.this.startActivity(intent2);
                            }
                            break;
                        }
                        return true;
                    }
                });


    }

    @Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask().execute();
    }

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.questionnaite_activity, container, false);
            return rootView;
        }
    }


    private class HttpRequestTask extends AsyncTask<Void, Void, QuestionnaireTest> {
        @Override
        protected QuestionnaireTest doInBackground(Void... params) {
            try {
                final String url = "http://url with data";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                QuestionnaireTest questionnaireTest = restTemplate.getForObject(url, QuestionnaireTest.class);
                return questionnaireTest;
            } catch (Exception e) {
                Log.e("QuestionnaireActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(QuestionnaireTest questionnaireTest) {
            TextView QuestionText = (TextView) findViewById(R.id.test_info);
            QuestionText.setText(questionnaireTest.getQuestion());
        }

    }


}