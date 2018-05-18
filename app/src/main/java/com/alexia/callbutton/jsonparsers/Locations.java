package com.alexia.callbutton.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;

public class Locations {
    private int idLocation;
    public String name;
    public String description;
    public String phone;
    public double latitude;
    public double longitude;

    public Locations(JSONObject json) throws JSONException {
        idLocation = json.getInt("idLocation");
        name = json.getString("name");
        description = json.getString("description");
        phone = json.getString("phone");
        latitude = json.getDouble("latitude");
        longitude = json.getDouble("longitude");
    }

    public Locations(int idLocation, String name, String description,
                     String phone, double latitude, double longitude) {
        this.idLocation = idLocation;
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}