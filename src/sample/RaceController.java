package sample;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RaceController {
    String carrier;
    String destination;
    String departureDate;
    String flight_time;
    String transplant;
    String price;
    String seats_left;
    String[][] data;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label greeter;

    @FXML
    private Label race_list;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getRace();
        int counter = 0;
        while (result.next()) {
            carrier = (result.getString(2));
            destination = (result.getString(3));
            departureDate = (result.getString(4));
            flight_time = (result.getString(5));
            transplant = (result.getString(6));
            price = (result.getString(7));
            seats_left = (result.getString(8));
            counter++;
        }
        race_list.setText("Перевозчик: " + carrier + "\n" + " Направление: " + destination + "\n" +  " Время отправки: " + departureDate + "\n" +  " Время полета: " + flight_time + " Часа "  + "\n" +  " Цена: " + price + "\n" + " Мест осталось: " + seats_left);
    }
}
