package com.example.kaligaswag.weather.data;

import org.json.JSONObject;

/**
 * Created by Kaligaswag on 01/12/2017.
 */

public class Channel implements JSONpopulator {
    private Item item;
    private Units units;

    public Item getItem() {
        return item;
    }

    public Units getUnits() {
        return units;
    }

    @Override
    public void populate(JSONObject data) {
        units = new Units();
        units.populate(data.optJSONObject("units"));

        item = new Item();
        item.populate(data.optJSONObject("item"));
    }
}
