package com.alexia.callbutton;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class QuestionnaireActivity extends FragmentActivity {

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionnaite_activity);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

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
//
                        }
                        return true;
                    }
                });


    }

}