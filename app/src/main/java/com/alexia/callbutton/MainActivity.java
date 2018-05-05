package com.alexia.callbutton;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

public class MainActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private FragmentManager manager;
    private BottomNavigationView bottomNav;
    private View shadow;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0 ;

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String message = null;

            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    message = "Message sent!";
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    message = "Error. Message not sent.";
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE:
                    message = "Error: No service.";
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    message = "Error: Null PDU.";
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    message = "Error: Radio off.";
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
        preferences = MainActivity.this.getSharedPreferences("shared_pref", MODE_PRIVATE);
        manager = getSupportFragmentManager();
        bottomNav = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(bottomNavListener);
        bottomNav.setSelectedItemId(R.id.action_sos);
        shadow = findViewById(R.id.shadow);
        removeShiftModeInBottomNav(bottomNav);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
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
        fragmentTransaction.addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        bottomNav.setVisibility(View.VISIBLE);
        shadow.setVisibility(View.VISIBLE);
        setActionBarTitle("Woman Safety");
        super.onBackPressed();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setActionBarTitle(String title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_help:
                    setActionBarTitle("Опитування");
                    replaceBottomNavFragment(new QuestionnaireFragment());
                    return true;
                case R.id.action_map:
                    setActionBarTitle("Карта");
                    replaceBottomNavFragment(new MapsFragment());
                    return true;
                case R.id.action_sos:
                    setActionBarTitle("Допомога");
                    replaceBottomNavFragment(new ButtonFragment());
                    return true;
                case R.id.action_info:
                    setActionBarTitle("Довідка");
                    replaceBottomNavFragment(new ReferenceFragment());
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
                setActionBarTitle("Налаштування");
                replaceWithStack(new SettingsFragment());
                bottomNav.setVisibility(View.INVISIBLE);
                shadow.setVisibility(View.INVISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void dial(View v) {
        if (isPermissionGranted()) {
            callAction();
//            sendSMSMessage();
        }
    }

    public void callActionPolice(View view) {
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

    protected void sendSMSMessage() {
        String phoneNo = preferences.getString("phone", "0933797479");
        String message = "help, lalala";
        PendingIntent pi= PendingIntent.getActivity(getApplicationContext(), 0, new Intent("SMS_SENT"),0);
        PendingIntent piDelivered = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent("SMS_DELIVERED"), 0);

        SmsManager.getDefault().sendTextMessage(phoneNo, null, message, pi, piDelivered);
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