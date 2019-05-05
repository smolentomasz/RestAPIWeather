package RestAPIWeather.controllers;

import org.springframework.web.client.HttpClientErrorException;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import RestAPIWeather.ErrorAlert;
import RestAPIWeather.models.Weather;
import RestAPIWeather.models.WeatherAPI;
import RestAPIWeather.models.WeatherModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainController implements Initializable {
    @FXML
    private TextField currentFieldCity;

    @FXML
    private TextField currentFieldCountry;

    @FXML
    private TextField forecastFieldCity;

    @FXML
    private TextField forecastFieldCountry;

    @FXML
    private ImageView currentWeatherIcon;

    @FXML
    private Label informationLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private ImageView weatherIcon1;

    @FXML
    private Label weather1Label;

    @FXML
    private ImageView weatherIcon2;

    @FXML
    private Label weather2Label;

    @FXML
    private ImageView weatherIcon3;

    @FXML
    private Label weather3Label;

    @FXML
    private ImageView weatherIcon4;

    @FXML
    private Label weather4Label;

    @FXML
    private ImageView weatherIcon5;

    @FXML
    private Label weather5Label;

    @FXML
    private Label forecastTitle;

    private ArrayList<Label> allLabels;
    private ArrayList<ImageView> allIcons;
    private Weather weatherRepository = new WeatherAPI();

    @FXML
    public void currentBtGetClick() {
        if(!currentFieldCity.getText().equals("") && !currentFieldCountry.getText().equals("")){
            try{
                String cityName = currentFieldCity.getText();
                String countryCode = currentFieldCountry.getText();

                WeatherModel wetherData = weatherRepository.getCurrentWeather(cityName, countryCode);

                currentFieldCity.setText("");
                currentFieldCountry.setText("");

                writeInformation(cityName, wetherData, titleLabel, informationLabel, currentWeatherIcon);
            }catch (HttpClientErrorException e) {
                ErrorAlert errorAlert = new ErrorAlert(e);
            }
        }
        else
            System.out.println("Please enter data!");
    }
    @FXML
    public void forecastBtGetClick(){
        if(!forecastFieldCity.getText().equals("") && !forecastFieldCountry.getText().equals("")){
            try {
                String cityName = forecastFieldCity.getText();
                String countryCode = forecastFieldCountry.getText();

                ArrayList<WeatherModel> forecastData = weatherRepository.getForecastWeather(cityName, countryCode);

                forecastFieldCity.setText("");
                forecastFieldCountry.setText("");


                for (int i = 0; i < 40; i++) {
                    if (i % 8 == 0) {
                        writeInformation(cityName, forecastData.get(i / 8), forecastTitle, allLabels.get(i / 8), allIcons.get(i / 8));
                    }
                }
            }catch (HttpClientErrorException e) {
                ErrorAlert errorAlert = new ErrorAlert(e);
            }
        }
        else
            System.out.println("Please enter data!");
    }
    private void writeInformation(String cityName, WeatherModel weatherModel, Label title, Label information, ImageView icon){
        Image image = new Image("http://openweathermap.org/img/w/" + weatherModel.getIcon() + ".png");
        icon.setImage(image);

        title.setText("Weather for " + cityName);

        information.setText(weatherModel.getDescription() + "\n" + weatherModel.getTemperature() + "\n" + weatherModel.getPressure() + "\n"
        + weatherModel.getHumidity() + "\n" + weatherModel.getWind());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allLabels = new ArrayList<>();
        allIcons = new ArrayList<>();
        allIcons.add(weatherIcon1);
        allIcons.add(weatherIcon2);
        allIcons.add(weatherIcon3);
        allIcons.add(weatherIcon4);
        allIcons.add(weatherIcon5);
        allLabels.add(weather1Label);
        allLabels.add(weather2Label);
        allLabels.add(weather3Label);
        allLabels.add(weather4Label);
        allLabels.add(weather5Label);

    }
}
