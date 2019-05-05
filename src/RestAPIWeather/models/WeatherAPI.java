package RestAPIWeather.models;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

public class WeatherAPI implements Weather {
    @Override
    public WeatherModel getCurrentWeather(String city, String country) throws HttpClientErrorException {
        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> exchange = rest.exchange(
                "http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&units=metric&appid=79aa469e0644e8e4dc65ff0a3f932a11",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                String.class);
        if(exchange.getStatusCode() == HttpStatus.OK){
            String inputData =  exchange.getBody();
            JsonObject newObject = new Gson().fromJson(inputData, JsonObject.class);
            WeatherModel currentWeatherModel = createModel(newObject);
            return currentWeatherModel;
        }
        return null;
    }

    @Override
    public ArrayList<WeatherModel> getForecastWeather(String city, String country) throws HttpClientErrorException{
        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> exchange = rest.exchange(
                "http://api.openweathermap.org/data/2.5/forecast?q=" + city + "," + country + "&units=metric&appid=79aa469e0644e8e4dc65ff0a3f932a11",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                String.class);
        if(exchange.getStatusCode() == HttpStatus.OK){
            String inputData =  exchange.getBody();
            ArrayList<WeatherModel> forecastWeatherList = new ArrayList<>();

            JsonObject newObject = new Gson().fromJson(inputData, JsonObject.class);
            JsonArray inputWether =  newObject.get("list").getAsJsonArray();

            for(int i=0;i<40;i++){
                if(i%8==0){
                    JsonObject objectWeatherForecast = new Gson().fromJson(inputWether.get(i+7).toString(), JsonObject.class);
                    WeatherModel forecastWeatherModel = createModel(objectWeatherForecast);
                    forecastWeatherList.add(forecastWeatherModel);
                }
            }
            return forecastWeatherList;
        }
        return null;
    }
    public WeatherModel createModel(JsonObject mainObject){
        JsonArray inputWeather =  mainObject.get("weather").getAsJsonArray();
        JsonObject inputMain =  mainObject.get("main").getAsJsonObject();
        JsonObject inputWind =  mainObject.get("wind").getAsJsonObject();
        JsonObject objectWeather = new Gson().fromJson(inputWeather.get(0).toString(), JsonObject.class);
        WeatherModel weatherModel = new WeatherModel("Description: " + objectWeather.get("description").getAsString(), "Temperature: " + inputMain.get("temp") + "\u00b0C",
                inputMain.get("pressure") + " hPa", "Humidity: " + inputMain.get("humidity") + " %", "Wind: " + inputWind.get("speed") + " m/s" + ", " + inputWind.get("deg") + "\u00b0",
                objectWeather.get("icon").getAsString());
        return weatherModel;
    }
}
