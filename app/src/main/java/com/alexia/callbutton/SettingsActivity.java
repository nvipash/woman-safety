package com.alexia.callbutton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class SettingsActivity extends FragmentActivity {
    Button settingsButton;
    EditText editText;
    String[] phones = {"0933797479", "0632478177", "0500213201", "0980969198"};
    SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        settingsButton = (Button) findViewById(R.id.button_settings);
        editText = (EditText) findViewById(R.id.editText);

        preferences = SettingsActivity.this.getSharedPreferences("shared_pref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        final ListView lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, phones);
        lvMain.setAdapter(adapter);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_help:
                            {Intent intent1 = new Intent(SettingsActivity.this , QuestionnaireActivity.class);
                                SettingsActivity.this.startActivity(intent1);}
                            break;

                            //case R.id.action_map:
                           // case R.id.action_settings:
                            case R.id.action_sos:
                            {Intent intent2 = new Intent(SettingsActivity.this , MainActivity.class);
                                SettingsActivity.this.startActivity(intent2);}
                            break;
//
                        }
                        return true;
                    }
                });


       Menu menu = bottomNavigationView.getMenu();
        menu.getItem(3).setChecked(true);
        menu.getItem(0).setChecked(false);
        menu.getItem(1).setChecked(false);
        menu.getItem(2).setChecked(false);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("phone", phones[lvMain.getCheckedItemPosition()]);
                editor.commit();

                Intent intent1 = new Intent(SettingsActivity.this , MainActivity.class);
                SettingsActivity.this.startActivity(intent1);
            }
        });

    }
}
