package com.wordpress.sreeharilive.weatherget.data;

import org.json.JSONObject;

/**
 * Created by sreehari on 2/13/17.
 */
public class Item implements JsonPopulator {
    private  Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populate(JSONObject data) {
    condition=new Condition();
        condition.populate(data.optJSONObject("condition"));

    }
}
