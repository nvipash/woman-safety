package com.alexia.callbutton;

import org.json.JSONException;
import org.json.JSONObject;

public class UserScore {
    int idScore;
    String userPhone;
    int score = 0;


    UserScore(JSONObject json) throws JSONException {
        idScore = json.getInt("id_score");
        userPhone = json.getString("user_phone");
        score = json.getInt("score");

    }
}
