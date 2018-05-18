package com.alexia.callbutton.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alexia.callbutton.R;

public class ButtonFragment extends Fragment {
    public SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.button_fragment, container, false);
    }

    public void callActionPolice_onClick() {
        String toDial = getString(R.string.police_number);
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + toDial));
        ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);
    }

    public void callAction() {
        String toDial = "tel:" + preferences.getString("phone",
                getString(R.string.free_violence_hotline));
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(toDial));
        ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callAction();
                    callActionPolice_onClick();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            R.string.permission_not_accepted, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}