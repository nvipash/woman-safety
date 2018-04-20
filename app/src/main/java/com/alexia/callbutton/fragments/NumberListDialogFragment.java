package com.alexia.callbutton.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.alexia.callbutton.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class NumberListDialogFragment extends DialogFragment {
    private ArrayList<String> phones;
    private ArrayAdapter<String> adapter;
    private ListView numberList;
    public SharedPreferences preferences;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view =
                LayoutInflater.from(getActivity()).inflate(R.layout.listview_layout, null);
        preferences = NumberListDialogFragment.this.getActivity()
                .getSharedPreferences("shared_pref", MODE_PRIVATE);
        Set<String> entries = preferences.getStringSet("phones", null);
        if (entries != null) {
            phones = new ArrayList<>(entries);
        }

        numberList = (ListView) view.findViewById(R.id.number_list);
        numberList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_single_choice, phones);
        final String selectedNumber = preferences.getString("phone", null);
        final SharedPreferences.Editor editor = preferences.edit();
        Log.e("SHARED_PREF", String.valueOf(preferences));
        int index = phones.indexOf(selectedNumber);
        numberList.setAdapter(adapter);
        if (index != -1) {
            numberList.setItemChecked(index, false);
        }

        DialogInterface.OnClickListener listenerOk = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int position = numberList.getCheckedItemPosition();
                if (position <= -1) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Номер не обрано", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putString("phone", phones.get(position));
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Номер телефону обраний", Toast.LENGTH_SHORT).show();
                    Log.e("SHARED_PREF", String.valueOf(preferences));
                }
                editor.apply();
                adapter.notifyDataSetChanged();
            }
        };
        DialogInterface.OnClickListener listenerCancel = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Номер не обрано", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        };
        return new AlertDialog.Builder(getContext()).setTitle("Оберіть контакт").setView(view)
                .setPositiveButton(android.R.string.ok, listenerOk)
                .setNegativeButton(android.R.string.cancel, listenerCancel).show();
    }
    public static NumberListDialogFragment newInstance() {
        NumberListDialogFragment dialogFragment = new NumberListDialogFragment();
        return dialogFragment;
    }
}