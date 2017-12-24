package com.alexia.callbutton;


import org.json.JSONException;
import org.json.JSONObject;

public class Instructions {

    int idInstruction;
    String title;
    String instruction;
    int rangeStart;
    int rangeEnd;

    Instructions(JSONObject json) throws JSONException {
        idInstruction = json.getInt("idInstruction");
        title = json.getString("title");
        instruction = json.getString("instruction");
        rangeStart = json.getInt("rangeStart");
        rangeEnd = json.getInt("rangeEnd");

    }

}

