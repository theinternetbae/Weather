package com.example.kaligaswag.weather;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaligaswag.weather.data.Channel;
import com.example.kaligaswag.weather.data.Item;
import com.example.kaligaswag.weather.service.WeatherServiceCallback;
import com.example.kaligaswag.weather.service.YahooWeatherService;

public class MainActivity extends AppCompatActivity implements WeatherServiceCallback {


    private TextView temperatureTextView, locationTextView, conditionTextView;
    private YahooWeatherService service;
    private EditText inputLocation;
    private Button updateButton;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateButton = findViewById(R.id.button);
        inputLocation = findViewById(R.id.editTextLocation);
        temperatureTextView = findViewById(R.id.textViewTemperature);
        locationTextView = findViewById(R.id.textViewLocation);
        conditionTextView = findViewById(R.id.textViewCondition);

        service = new YahooWeatherService(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.show();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setMessage("Loading..");
                dialog.show();
                String loc = inputLocation.getText().toString();
                service.refreshWeather(loc + ", Indonesia");
            }
        });

        service.refreshWeather("Semarang, Indonesia");

    }

    @Override
    public void serviceSuccess(Channel channel) {
        dialog.hide();

        Item item = channel.getItem();


//        weatherIconImageView.setImageDrawable(weatherIconDrawable);

        conditionTextView.setText(item.getCondition().getDescription());
        temperatureTextView.setText(item.getCondition().getTemperature() + "\u00B0" + channel.getUnits().getTemperature());
        locationTextView.setText(service.getLocation());
    }

    @Override
    public void serviceFailur(Exception exception) {
        dialog.hide();
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
