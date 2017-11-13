package com.alexia.callbutton;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class SettingsActivity extends FragmentActivity {
    Button settingsButton;
    EditText editText;
    String[] phones = { "0933797479", "0632478177", "0500213201" };
    SharedPreferences preferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        settingsButton = (Button)findViewById(R.id.button2);
        editText = (EditText)findViewById(R.id.editText);

        preferences = SettingsActivity.this.getSharedPreferences("shared_pref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        final ListView lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, phones);
        lvMain.setAdapter(adapter);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("phone", phones[lvMain.getCheckedItemPosition()]);
                editor.commit();
            }
        });

    }
}
