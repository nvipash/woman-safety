package com.alexia.callbutton;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SettingsActivity extends Activity {
    Button settingsButton;
    EditText editText;
    Button addContact;
    ArrayList<String> phones = new ArrayList<>();
//    String[] phones = { "0933797479", "0632478177", "0500213201" };
    SharedPreferences preferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_fragment);

        settingsButton = (Button)findViewById(R.id.button2);
        editText = (EditText)findViewById(R.id.editText);

        addContact = (Button)findViewById(R.id.addContact);

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

                editor.putString("phone", phones.get(lvMain.getCheckedItemPosition()));
                editor.commit();
            }
        });

        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(pickContact, 1);
            }
        });
        if (ContextCompat.checkSelfPermission(SettingsActivity.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(SettingsActivity.this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(SettingsActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},1) ;
//                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
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
                    Cursor c = getContentResolver().query(contactData, null, null, null, null);

                    if (c.moveToFirst()) {
//                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                        String number = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (number.equalsIgnoreCase("1")) {
                            Cursor pho = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID, null, null);


                            pho.moveToFirst();
                            String cNumber = pho.getString(pho.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            phones.add(cNumber);
//                            String nameContact = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
//                            Toast.makeText(getApplicationContext(), "number = " + cNumber + " - name - " + nameContact, Toast.LENGTH_SHORT).show();


                        } else {

                        }

                    }
                }
        }


    }
}


