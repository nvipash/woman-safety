package com.alexia.callbutton;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.alexia.callbutton.fragments.ButtonFragment;
import com.alexia.callbutton.fragments.MapsFragment;
import com.alexia.callbutton.fragments.QuestionnaireFragment;
import com.alexia.callbutton.fragments.ReferenceFragment;
import com.alexia.callbutton.fragments.SettingsFragment;

import java.lang.reflect.Field;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private SharedPreferences preferences;
    private FragmentManager manager;
    private BottomNavigationView bottomNav;
    private LocationManager locationManager;
    private Location currentLocation;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = null;

            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    message = getString(R.string.notify_sms_sent);
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    message = getString(R.string.notify_sms_not_sent);
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE:
                    message = getString(R.string.notify_sms_no_service);
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    message = getString(R.string.notify_sms_no_pdu);
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    message = getString(R.string.notify_sms_airplane_mode);
                    break;
            }
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver(receiver, new IntentFilter("SMS_SENT"));
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        Objects.requireNonNull(bar).setDisplayHomeAsUpEnabled(false);
        preferences = MainActivity.this.getSharedPreferences("shared_pref", MODE_PRIVATE);
        manager = getSupportFragmentManager();
        bottomNav = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(bottomNavListener);
        bottomNav.setSelectedItemId(R.id.action_sos);
        removeShiftModeInBottomNav(bottomNav);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        checkLocationPermissions();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    public void replaceBottomNavWithoutStack(final Fragment fragment) {
        final FragmentTransaction fragmentTransaction = manager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    public void replaceWithStack(final Fragment fragment) {
        final FragmentTransaction fragmentTransaction = manager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        bottomNav.setVisibility(View.VISIBLE);
        setActionBarTitle("Woman Safety");
        showActionBar();
        super.onBackPressed();
    }

    public void setActionBarTitle(String title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
    }

    @SuppressLint("RestrictedApi")
    public void hideActionBar() {
        Objects.requireNonNull(getSupportActionBar()).setShowHideAnimationEnabled(false);
        getSupportActionBar().hide();
    }

    @SuppressLint("RestrictedApi")
    public void showActionBar() {
        Objects.requireNonNull(getSupportActionBar()).setShowHideAnimationEnabled(false);
        getSupportActionBar().show();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_help:
                    setActionBarTitle(getString(R.string.text_questionnaire));
                    replaceBottomNavWithoutStack(new QuestionnaireFragment());
                    return true;
                case R.id.action_map:
                    setActionBarTitle(getString(R.string.text_map));
                    replaceBottomNavWithoutStack(new MapsFragment());
                    return true;
                case R.id.action_sos:
                    setActionBarTitle(getString(R.string.text_sos));
                    replaceBottomNavWithoutStack(new ButtonFragment());
                    return true;
                case R.id.action_info:
                    setActionBarTitle(getString(R.string.text_info));
                    replaceBottomNavWithoutStack(new ReferenceFragment());
                    return true;
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                setActionBarTitle(getString(R.string.text_settings));
                replaceWithStack(new SettingsFragment());
                bottomNav.setVisibility(View.INVISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void dial(View v) {
        if (isPermissionGranted()) {
            sendSMSMessage();
            callAction();
        }
    }

    public void callActionPolice(View view) {
        String toDial = getString(R.string.police_number);
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
        String toDial = "tel:" + preferences.getString("phone",
                getString(R.string.free_violence_hotline));
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

    protected void sendSMSMessage() {
        String phoneNo = preferences.getString("phone",
                getString(R.string.free_violence_hotline));
        int newLineIndex = phoneNo.indexOf("\n");
        if (newLineIndex != -1) {
            phoneNo = phoneNo.substring(newLineIndex + 1);
        }
        String message = getString(R.string.violence_sms_alert);
        if (currentLocation != null) {
            String params = "?q=" + String.valueOf(currentLocation.getLatitude())
                    + "," + String.valueOf(currentLocation.getLongitude());
            message += getString(R.string.maps_sms_location) + params;
        }
        PendingIntent pi = PendingIntent
                .getActivity(getApplicationContext(), 0, new Intent("SMS_SENT"), 0);
        PendingIntent piDelivered = PendingIntent
                .getBroadcast(getApplicationContext(), 0, new Intent("SMS_DELIVERED"), 0);
        SmsManager.getDefault().sendTextMessage(phoneNo, null, message, pi, piDelivered);
    }

    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS}, 1);
                return false;
            }
        }//permission is automatically granted on sdk<23 upon installation
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        boolean isGranted = grantResults.length > 0 && grantResults[0]
                == PackageManager.PERMISSION_GRANTED;
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (isGranted) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        updateLocation();
                    }
                } else {
                    Toast.makeText(this, getString(R.string.permission_not_accepted),
                            Toast.LENGTH_LONG).show();
                }
            }
            case 1: {
                if (isGranted) {
                    Toast.makeText(getApplicationContext(),
                            R.string.permission_accepted, Toast.LENGTH_SHORT).show();
                    callAction();
                } else {
                    Toast.makeText(getApplicationContext(),
                            R.string.permission_not_accepted, Toast.LENGTH_SHORT).show();
                }
            }
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

    private void checkLocationPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                updateLocation();
            } else {
                requestLocationPermissions();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void updateLocation() {
        locationManager.requestLocationUpdates
                (LocationManager.GPS_PROVIDER, 2000, 10, this);
        currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    private void requestLocationPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATION);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null) {
            return;
        }
        currentLocation = location;
    }

    @Override
    public void onProviderDisabled(String s) {
    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }
}