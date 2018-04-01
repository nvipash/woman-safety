package com.alexia.callbutton.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;

public class QuestionnaireInstruction {
    private int idInstruction;
    public String title;
    public String instruction;
    private int rangeStart;
    private int rangeEnd;

    public QuestionnaireInstruction(JSONObject json) throws JSONException {
        idInstruction = json.getInt("idInstruction");
        title = json.getString("title");
        instruction = json.getString("instruction");
        rangeStart = json.getInt("rangeStart");
        rangeEnd = json.getInt("rangeEnd");
    }

    @Override
    public String toString() {
        return "idInstruction: " + idInstruction + "\n" +
                "title: " + title + "\n" +
                "instruction: " + instruction + "\n" +
                "rangeStart: " + rangeStart + "\n" +
                "rangeEnd: " + rangeEnd;
    }
}