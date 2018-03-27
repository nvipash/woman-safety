package com.alexia.callbutton.Fragment;

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
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.alexia.callbutton.R;
import com.alexia.callbutton.SettingsActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;
import static com.alexia.callbutton.R.id.button_settings;

/**
 * Created by Alexia on 13.11.2017.
 */

public class SettingsFragment extends Fragment {

    Button settingsButton;
    Button addContact;
    ArrayList<String> phones = new ArrayList<>();
    ArrayAdapter adapter;
    ListView lvMain;
    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.settings_fragment, container, false);
        preferences = SettingsFragment.this.getActivity().getSharedPreferences("shared_pref", MODE_PRIVATE);
        Set<String> entries = preferences.getStringSet("phones", null);
        if (entries != null) {
            phones = new ArrayList(entries);
        }

        lvMain = (ListView) v.findViewById(R.id.lvMain);
        lvMain.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_single_choice, phones);
        String selectedNumber = preferences.getString("phone", null);
        int index = phones.indexOf(selectedNumber);
        lvMain.setAdapter(adapter);
        if (index != -1) {
            lvMain.setItemChecked(index, true);
        }


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

        settingsButton = (Button) v.findViewById(button_settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set phonesSet = new HashSet(phones);
                editor.putStringSet("phones", phonesSet);
                int position = lvMain.getCheckedItemPosition();
                if (position == -1) {
                    Toast.makeText(getActivity().getApplicationContext(), "Choose number before saving", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putString("phone", phones.get(position));
                }
                editor.apply();
            }
        });

        addContact = (Button) v.findViewById(R.id.addContact);
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                pickContact.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(pickContact, 1);
            }
        });

         if (ContextCompat.checkSelfPermission(getActivity(),
         Manifest.permission.READ_CONTACTS)
         != PackageManager.PERMISSION_GRANTED) {
             if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                     Manifest.permission.READ_CONTACTS)) {
             } else {
                 ActivityCompat.requestPermissions(getActivity(),
                         new String[]{Manifest.permission.READ_CONTACTS}, 1);
             }
         }

        return v;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingsButton = (Button) view.findViewById(button_settings);
        addContact = (Button) view.findViewById(R.id.addContact);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    assert contactData != null;
                    String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.Contacts.HAS_PHONE_NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
                    @SuppressLint("Recycle") Cursor c = getActivity().getContentResolver().query(contactData, projection, null, null, null);

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
                    }
                }
        }
    }
}
