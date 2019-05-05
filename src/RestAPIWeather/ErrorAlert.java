package RestAPIWeather;

import org.springframework.web.client.HttpClientErrorException;

import javafx.scene.control.Alert;

public class ErrorAlert extends Alert {
    public ErrorAlert(HttpClientErrorException e) {
        super(AlertType.ERROR);
        setContentText(e.getStatusText());
        setHeaderText(e.getLocalizedMessage());
        setTitle("Error!");
        show();
    }
}
