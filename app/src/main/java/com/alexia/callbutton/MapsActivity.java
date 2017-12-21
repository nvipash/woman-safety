package com.alexia.callbutton;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_layout);




        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_map);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        //                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_help: {
                                Intent intent1 = new Intent(MapsActivity.this, QuestionnaireActivity.class);
                                MapsActivity.this.startActivity(intent1);
                            }
                            break;

                            case R.id.action_settings:{
                                Intent intent2 = new Intent(MapsActivity.this, SettingsActivity.class);
                                MapsActivity.this.startActivity(intent2);
                            }
                            case R.id.action_sos: {
                                Intent intent2 = new Intent(MapsActivity.this, MainActivity.class);
                                MapsActivity.this.startActivity(intent2);
                            }
                            break;
                            //
                        }
                        return true;
                    }
                });
    }
 
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    public void onClickTest(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(49.84407, 24.026155471801758);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Lviv(I hope so)"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
