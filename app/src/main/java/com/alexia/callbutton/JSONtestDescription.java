package com.alexia.callbutton;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DELL on 19/12/2017.
 */

public class JSONtestDescription {
    String testDescription;

    JSONtestDescription(JSONObject json) throws JSONException {
        testDescription = json.getString("description");
    }

    @Override
    public String toString() {
        return "description: " + testDescription;
    }
}