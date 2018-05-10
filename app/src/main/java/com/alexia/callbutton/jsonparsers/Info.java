package com.alexia.callbutton.jsonparsers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {
@SerializedName("info")
@Expose
    private Surveys testDescription = new Surveys();

public Surveys getTestDescription(){
    return testDescription;
}


}
