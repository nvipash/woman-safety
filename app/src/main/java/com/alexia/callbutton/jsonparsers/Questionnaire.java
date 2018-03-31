package com.alexia.callbutton.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;

public class Questionnaire {
    public int idQuestion;
    public String question;
    private int points;

    public Questionnaire(JSONObject json) throws JSONException {
        idQuestion = json.getInt("idQuestion");
        question = json.getString("question");
        points = json.getInt("points");
    }

    @Override
    public String toString() {
        return "idQuestion: " + idQuestion + "\n" +
                "question: " + question + "\n" +
                "points: " + points;
    }
}
