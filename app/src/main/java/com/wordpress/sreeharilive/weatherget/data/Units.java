package com.wordpress.sreeharilive.weatherget.data;

import org.json.JSONObject;

/**
 * Created by sreehari on 2/13/17.
 */
public class Units implements JsonPopulator {
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {
     temperature=data.optString("temperature");


    }
}
