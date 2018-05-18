package com.alexia.callbutton.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;

public class QuestionnaireSelection {
    public String chooseTestDescription;

    public QuestionnaireSelection(JSONObject json) throws JSONException {
        chooseTestDescription = json.getString("survey");
    }

    @Override
    public String toString() {
        return "survey: " + chooseTestDescription;
    }
}