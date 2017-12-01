package com.example.kaligaswag.weather.service;

import com.example.kaligaswag.weather.data.Channel;

/**
 * Created by Kaligaswag on 01/12/2017.
 */

public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);
    void serviceFailur(Exception exception);
}
