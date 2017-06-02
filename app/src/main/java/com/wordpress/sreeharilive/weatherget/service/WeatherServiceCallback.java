package com.wordpress.sreeharilive.weatherget.service;

import com.wordpress.sreeharilive.weatherget.data.Channel;

/**
 * Created by sreehari on 2/13/17.
 */
public interface WeatherServiceCallback {
    void servicesuccess(Channel channel);
    void servicefailure(Exception exception);
}
