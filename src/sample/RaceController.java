package sample;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;

public class RaceController {
    private List racesDest;
    ResultSet result2;
    String carrier;
    String destination;
    String destination2;
    String departureDate;
    String flight_time;
    String transplant;
    String price;
    String seats_left;
    String[][] data;
    int chosen_box;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<?> choice_race;

    @FXML
    private Label race_info;

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
        choice_race.setItems(FXCollections.observableArrayList(racesDest));
        choice_race.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                chosen_box = newValue.intValue();
            }
        });
        choice_race.setOnAction(event -> {
            DatabaseHandler dbHandler2 = new DatabaseHandler();
            try {
                result2 = dbHandler.getRaceAllInf(chosen_box+1);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            while(true){
                try {
                    if (!result2.next()) break;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    carrier = result2.getString(2);
                    destination2 = result2.getString(3);
                    departureDate = result2.getString(4);
                    flight_time = result2.getString(5);
                    transplant = result2.getString(6);
                    price = result2.getString(7);
                    seats_left = result2.getString(8);
                    if (transplant == "-")
                        transplant = "Без пересадок";
                    race_info.setText("Перевозчик: " + carrier + "\n" + "Направление: " + destination2 + "\n" + "Время вылета: " + departureDate + "\n" + "Время полета: " + flight_time + " часа" + "\n" +
                            "Пересадки: " + transplant + "\n" + "Цена: " + price + "\n" + "Осталось мест: " + seats_left);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

}
