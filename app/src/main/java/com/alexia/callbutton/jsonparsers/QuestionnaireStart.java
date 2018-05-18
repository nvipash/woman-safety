package com.alexia.callbutton.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;

public class QuestionnaireStart {
    public String testDescription;

    public QuestionnaireStart(JSONObject json) throws JSONException {
        testDescription = json.getString("description");
    }

    @Override
    public String toString() {
        return "description: " + testDescription;
    }
}