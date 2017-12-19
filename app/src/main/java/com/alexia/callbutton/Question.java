package com.alexia.callbutton;


import org.json.JSONException;
import org.json.JSONObject;

class Question {
    int idQuestion;
    String question;
    int points;

    Question(JSONObject json) throws JSONException {
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
