package com.alexia.callbutton;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.alexia.callbutton.fragments.ButtonFragment;
import com.alexia.callbutton.fragments.MapsFragment;
import com.alexia.callbutton.fragments.QuestionnaireFragment;
import com.alexia.callbutton.fragments.SettingsFragment;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    private static boolean ACTIVITY_PASS;
    private SharedPreferences preferences;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = MainActivity.this.getSharedPreferences("shared_pref", MODE_PRIVATE);
        manager = getSupportFragmentManager();
        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(bottomNavListener);
        bottomNav.setSelectedItemId(R.id.action_sos);
        removeShiftModeInBottomNav(bottomNav);
    }

    public void replaceBottomNavFragment(final Fragment fragment) {
        final FragmentTransaction fragmentTransaction = manager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    public void replaceWithStack(final Fragment fragment) {
        final FragmentTransaction fragmentTransaction = manager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (ACTIVITY_PASS) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
        ACTIVITY_PASS = true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_help:
                    replaceBottomNavFragment(new QuestionnaireFragment());
                    return true;
                case R.id.action_map:
                    replaceBottomNavFragment(new MapsFragment());
                    return true;
                case R.id.action_sos:
                    replaceBottomNavFragment(new ButtonFragment());
                    return true;
                case R.id.action_settings:
                    replaceBottomNavFragment(new SettingsFragment());
                    return true;
            }
            return true;
        }
    };

    public void dial(View v) {
        if (isPermissionGranted()) {
            callAction();
        }
    }
    public void callActionPolice_onClick(View view) {
        String toDial = "102";
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + toDial));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
    }


    public void callAction() {
        String toDial = "tel:" + preferences.getString("phone", "0933797479");
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(toDial));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
    }

    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }//permission is automatically granted on sdk<23 upon installation
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            "Дозвіл надано", Toast.LENGTH_SHORT).show();
                    callAction();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "У наданні дозволу відмовлено", Toast.LENGTH_SHORT).show();
                }
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @SuppressLint("RestrictedApi")
    public static void removeShiftModeInBottomNav(final BottomNavigationView bottomNav) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNav.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException ignored) {
        } catch (IllegalAccessException ignored) {
        }
    }
}