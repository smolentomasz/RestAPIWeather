package RestAPIWeather.models;

import java.util.ArrayList;

import RestAPIWeather.models.WeatherModel;

public interface Weather {
    WeatherModel getCurrentWeather(String city, String country);

    ArrayList<WeatherModel> getForecastWeather(String city, String country);
}
