package sample;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class RaceController {
    private List racesDest;
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
    private Label races;
    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        racesDest = new ArrayList();
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getRace();
        int counter = 0;
        while (result.next()) {
            destination = (result.getString(3));
            racesDest.add(result.getString(3));
        }
        races.setText(racesDest.get(1).toString());
    }



}
