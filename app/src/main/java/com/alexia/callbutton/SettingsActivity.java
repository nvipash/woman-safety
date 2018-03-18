package com.alexia.callbutton;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SettingsActivity extends FragmentActivity {
    Button settingsButton;
    Button addContact;
    ArrayList<String> phones = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView lvMain;
    SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_fragment);

        settingsButton = (Button) findViewById(R.id.button_settings);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_settings);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        //                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_help: {
                                Intent intent1 = new Intent(SettingsActivity.this, QuestionnaireActivity.class);
                                SettingsActivity.this.startActivity(intent1);
                            }
                            break;

                            case R.id.action_map: {
                                Intent intent3 = new Intent(SettingsActivity.this, MapsActivity.class);
                                SettingsActivity.this.startActivity(intent3);
                            }
                            break;
                            // case R.id.action_settings:
                            case R.id.action_sos: {
                                Intent intent2 = new Intent(SettingsActivity.this, MainActivity.class);
                                SettingsActivity.this.startActivity(intent2);
                            }
                            break;
                            //
                        }
                        return true;
                    }
                });


        Menu menu = bottomNavigationView.getMenu();


        preferences = SettingsActivity.this.getSharedPreferences("shared_pref", MODE_PRIVATE);
        Set<String> entries = preferences.getStringSet("phones", null);
        if (entries != null) {
            phones = new ArrayList(entries);
        }

        lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice, phones);
        String selectedNumber = preferences.getString("phone", null);
        int index = phones.indexOf(selectedNumber);
        lvMain.setAdapter(adapter);
        if (index != -1) {
            lvMain.setItemChecked(index, true);
        }

        settingsButton = (Button) findViewById(R.id.button_settings);
        addContact = (Button) findViewById(R.id.addContact);

        final SharedPreferences.Editor editor = preferences.edit();
        lvMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long arg3) {

                adapter.remove(phones.get(position));
                adapter.notifyDataSetChanged();

                return false;
            }

        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set phonesSet = new HashSet(phones);
                editor.putStringSet("phones", phonesSet);
                int position = lvMain.getCheckedItemPosition();
                if (position == -1) {
                    Toast.makeText(getApplicationContext(), "Choose number before saving", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putString("phone", phones.get(position));
                }
                editor.apply();
            }
        });

        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                pickContact.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(pickContact, 1);
            }
        });

        if (ContextCompat.checkSelfPermission(SettingsActivity.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(SettingsActivity.this,
                    Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(SettingsActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS}, 1);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    assert contactData != null;
                    String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.Contacts.HAS_PHONE_NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
                    @SuppressLint("Recycle") Cursor c = getContentResolver().query(contactData, projection, null, null, null);

                    if (!c.moveToFirst()) {
                        return;
                    }
                    String number = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    String name = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    if (number.equalsIgnoreCase("1") || name.equalsIgnoreCase("1")) {
                        String n = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String nam = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        phones.add(nam + "\n" + n);
                        adapter.notifyDataSetChanged();
                    } else {

                    }
                }
        }


    }

}


