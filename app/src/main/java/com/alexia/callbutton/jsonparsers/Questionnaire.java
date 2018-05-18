package com.alexia.callbutton.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;

public class Questionnaire {
    public int idQuestion;
    public String question;
    public int points_often;
    public int points_seldom;
    public int points_never;

    public Questionnaire(JSONObject json) throws JSONException {
        idQuestion = json.getInt("idQuestion");
        question = json.getString("question");
        points_often = json.getInt("pointsOften");
        points_seldom = json.getInt("pointsSeldom");
        points_never = json.getInt("pointsNever");
    }
    public Questionnaire(){};

    @Override
    public String toString() {
        return "idQuestion: " + idQuestion + "\n" +
                "question: " + question + "\n" +
                "pointsOften: " + points_often + "\n" +
                "pointsSeldom: " + points_seldom + "\n" +
                "pointsNever: " + points_never;
    }
}