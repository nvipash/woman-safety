package com.alexia.callbutton;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends Activity {
    Button settingsButton;
    EditText editText;

    SharedPreferences preferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_fragment);

        settingsButton = (Button)findViewById(R.id.button2);
        editText = (EditText)findViewById(R.id.editText);

        preferences = SettingsActivity.this.getSharedPreferences("shared_pref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("phone", editText.getText().toString());
                editor.commit();
            }
        });

    }
}
