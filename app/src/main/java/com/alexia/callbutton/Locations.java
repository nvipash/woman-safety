package com.alexia.callbutton;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Daria Savinkina on 3/25/2018.
 */

public class Locations {

    int idLocation;
    String name;
    String description;
    String phone;
    double latitude;
    double longitude;

    Locations(JSONObject json) throws JSONException {
        idLocation = json.getInt("idLocation");
        name = json.getString("name");
        description = json.getString("description");
        phone = json.getString("phone");
        latitude=json.getDouble("latitude");
        longitude=json.getDouble("longitude");

    }
}
