package com.alexia.callbutton;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DELL on 19/12/2017.
 */

public class JSONsurveyTitle {
    String chooseTestDescription;

    JSONsurveyTitle(JSONObject json) throws JSONException {
        chooseTestDescription = json.getString("survey");
    }

    @Override
    public String toString() {
        return "survey: " + chooseTestDescription;
    }
}
