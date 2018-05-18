package com.alexia.callbutton.fragments;

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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.alexia.callbutton.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends PreferenceFragmentCompat {
    private ArrayList<String> phones = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    public SharedPreferences preferences;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference_fragment, rootKey);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = SettingsFragment.this.getActivity()
                .getSharedPreferences("shared_pref", MODE_PRIVATE);
        Set<String> entries = preferences.getStringSet("phones", null);
        if (entries != null) {
            phones = new ArrayList<>(entries);
        }
        adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_single_choice, phones);
        final SharedPreferences.Editor editor = preferences.edit();
        Preference selectNumber = findPreference("number_list");
        selectNumber.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference contactPreference) {
                if (preferences.getBoolean("first_run", true)) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            R.string.before_add_number,
                            Toast.LENGTH_LONG).show();
                    Intent pickContactAtFirst = new Intent(Intent.ACTION_PICK,
                            ContactsContract.Contacts.CONTENT_URI);
                    pickContactAtFirst.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                    startActivityForResult(pickContactAtFirst, 1);
                    preferences.edit().putBoolean("first_run", false).apply();
                } else {
                    Set<String> phonesSet = new HashSet<>(phones);
                    editor.putStringSet("phones", phonesSet);
                    editor.apply();
                    FragmentManager manager = getFragmentManager();
                    SettingsNumberListDialogFragment dialog =
                            new SettingsNumberListDialogFragment();
                    dialog.show(manager, null);
                }
                return true;
            }
        });

        Preference addNumber = findPreference("add_number");
        addNumber.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference contactPreference) {
                Intent pickContact = new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI);
                pickContact.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(pickContact, 1);
                return true;
            }
        });

        Preference infoWidget = findPreference("info_widget");
        infoWidget.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference contactPreference) {
                Toast.makeText(getActivity().getApplicationContext(),
                        R.string.how_to_add_widget,
                        Toast.LENGTH_LONG).show();
                return true;
            }
        });

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_CONTACTS)) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_CONTACTS}, 1);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri contactData = data.getData();
            assert contactData != null;
            String[] projection = new String[]{
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.Contacts.HAS_PHONE_NUMBER,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
            @SuppressLint("Recycle") Cursor query = getActivity()
                    .getContentResolver()
                    .query(contactData, projection, null, null, null);
            assert query != null;
            if (!query.moveToFirst()) {
                return;
            }
            String number = query.getString(query.getColumnIndexOrThrow(ContactsContract.
                    Contacts.HAS_PHONE_NUMBER));
            String name = query.getString(query.getColumnIndexOrThrow(ContactsContract.
                    CommonDataKinds.Phone.DISPLAY_NAME));
            if (number.equalsIgnoreCase("1") || name.equalsIgnoreCase("1")) {
                String displayNumber = query.getString(query.getColumnIndexOrThrow(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                String displayName = query.getString(query.getColumnIndexOrThrow(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                phones.add(displayName + "\n" + displayNumber);
                adapter.notifyDataSetChanged();
            }
        }
    }
}