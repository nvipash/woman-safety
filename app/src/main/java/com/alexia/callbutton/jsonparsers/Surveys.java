package com.alexia.callbutton.jsonparsers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Surveys {
@SerializedName("id_survey")
@Expose
int id;

@SerializedName("survey")
@Expose
String survey;

@SerializedName("description")
@Expose
String description;

public int getId(){
    return id;
}

public String getDescription(){
    return description;
}

public String getSurvey(){
    return survey;
}





}
