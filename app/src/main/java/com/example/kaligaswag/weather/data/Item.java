package com.example.kaligaswag.weather.data;

import org.json.JSONObject;

/**
 * Created by Kaligaswag on 01/12/2017.
 */

public class Item implements JSONpopulator {
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));
    }
}
