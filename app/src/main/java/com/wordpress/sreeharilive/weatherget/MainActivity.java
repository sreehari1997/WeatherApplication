package com.wordpress.sreeharilive.weatherget;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wordpress.sreeharilive.weatherget.data.Channel;
import com.wordpress.sreeharilive.weatherget.data.Item;
import com.wordpress.sreeharilive.weatherget.service.WeatherServiceCallback;
import com.wordpress.sreeharilive.weatherget.service.YahooWeatherService;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements WeatherServiceCallback {
    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView condtionTextView;
    private TextView locationTextView;

    private YahooWeatherService service;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherIconImageView=(ImageView)findViewById(R.id.weatherIconImageView);
        temperatureTextView=(TextView)findViewById(R.id.temperatureTextView);
        condtionTextView=(TextView)findViewById(R.id.conditionTextView);
        locationTextView=(TextView)findViewById(R.id.locationTextView);

        service= new YahooWeatherService(this);
        dialog= new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        service.refreshweather("Kalady,IN");
    }

    @Override
    public void servicesuccess(Channel channel) {
        dialog.hide();

        Item item = channel.getItem();
        int resourceId=getResources().getIdentifier("drawable/icon_"+ item.getCondition().getCode(),null,getPackageName());
        Drawable weatherIconDrawable = getResources().getDrawable(resourceId);

        weatherIconImageView.setImageDrawable(weatherIconDrawable);
        temperatureTextView.setText(item.getCondition().getTemperature()+"\u00B0"+channel.getUnits().getTemperature());
        condtionTextView.setText(item.getCondition().getDescription());
        locationTextView.setText(service.getLocation());
    }

    @Override
    public void servicefailure(Exception exception) {
        dialog.hide();
        Toast.makeText(this,exception.getMessage(),Toast.LENGTH_LONG).show();

    }
}
