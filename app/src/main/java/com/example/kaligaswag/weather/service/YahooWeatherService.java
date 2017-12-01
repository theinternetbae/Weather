package com.example.kaligaswag.weather.service;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.kaligaswag.weather.data.Channel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Kaligaswag on 01/12/2017.
 */

public class YahooWeatherService {
    private WeatherServiceCallback weatherServiceCallback;
    private String location;
    private Exception error;

    public YahooWeatherService(WeatherServiceCallback weatherServiceCallback) {
        this.weatherServiceCallback = weatherServiceCallback;
    }

    public String getLocation() {
        return location;
    }

    public void refreshWeather(String l){
        this.location = l;
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {

                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='c'", strings[0]);
                String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));

                try {
                    URL url = new URL(endpoint);

                    URLConnection connection = url.openConnection();

                    InputStream inputStream = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    return result.toString();
                } catch (Exception e) {
                    error = e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {

                if (s == null && error != null) {
                    weatherServiceCallback.serviceFailur(error);
                    return;
                }

                try {
                    JSONObject data = new JSONObject(s);

                    JSONObject queryResults = data.optJSONObject("query");

                    int count = queryResults.optInt("count");
                    if (count == 0) {
                        weatherServiceCallback.serviceFailur(new locationWeatherException("No weather information for " + location));
                        return;
                    }

                    Channel channel = new Channel();
                    channel.populate(queryResults.optJSONObject("results").optJSONObject("channel"));

                    weatherServiceCallback.serviceSuccess(channel);

                } catch (JSONException e) {
                    weatherServiceCallback.serviceFailur(e);
                }
            }
        }.execute(location);
    }

    public class locationWeatherException extends Exception{
        public locationWeatherException(String message) {
            super(message);
        }
    }
}
