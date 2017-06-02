package com.wordpress.sreeharilive.weatherget.data;

import org.json.JSONObject;

/**
 * Created by sreehari on 2/13/17.
 */
public class Channel implements JsonPopulator {
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
        units= new Units();
        units.populate(data.optJSONObject("units"));
        item= new Item();
        item.populate(data.optJSONObject("item"));

    }
}
