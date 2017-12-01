package com.example.kaligaswag.weather.data;

import org.json.JSONObject;

/**
 * Created by Kaligaswag on 01/12/2017.
 */

public class Units implements JSONpopulator {
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {
        temperature = data.optString("temperature");
    }
}
