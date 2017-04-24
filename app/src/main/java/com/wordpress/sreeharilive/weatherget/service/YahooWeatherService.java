package com.wordpress.sreeharilive.weatherget.service;

import android.net.Uri;
import android.os.AsyncTask;

import com.wordpress.sreeharilive.weatherget.data.Channel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by sreehari on 2/13/17.
 */

public class YahooWeatherService {
    private WeatherServiceCallback callback;
    private Exception error;
    private String temperatureUnit = "C";
    private String Location;
    public YahooWeatherService(WeatherServiceCallback callback) {
        this.callback = callback;
    }

    public String getLocation() {
        return Location;
    }

    public void refreshweather(String l){
        this.Location = l;

        new AsyncTask<String,Void,String>(){
            @Override
            protected String doInBackground(String ... strings){
                String YQL=String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='c'",strings[0]);
                String endpoint=String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));
                try {
                    URL url=new URL(endpoint);
                    URLConnection connection=url.openConnection();
                    InputStream inputStream=connection.getInputStream();
                    BufferedReader reader =new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result= new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null)
                    {
                        result.append(line);
                    }

                   return result.toString();
                } catch (Exception e) {
                    error=e;
                }
               return null;
            }

            @Override
            protected void onPostExecute(String s){
                if(s==null&&error!=null)
                {
                    callback.servicefailure(error);
                    return;
                }
                try {
                    JSONObject data=new JSONObject(s);
                    JSONObject queryResults=data.optJSONObject("query");
                    int count=queryResults.optInt("count");

                    if(count==0)
                    {
                        callback.servicefailure(new LocationWeatherException("NO wetaher information found for"+ Location));
                        return;
                    }

                    Channel channel= new Channel();
                    channel.populate(queryResults.optJSONObject("results").optJSONObject("channel"));

                    callback.servicesuccess(channel);
                } catch (JSONException e) {
                    callback.servicefailure(e);
                }
            }
        }.execute(Location);
    }
    public class LocationWeatherException extends Exception
    {
        public LocationWeatherException(String detailMessage)
        {
            super(detailMessage);
        }
    }
}