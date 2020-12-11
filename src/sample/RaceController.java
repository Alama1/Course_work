package sample;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RaceController {
    private List racesDest;
    ResultSet result2;
    String carrier;
    String destination;
    String destination_choise;
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
    private Button book_button;

    @FXML
    private Label success_label;

    @FXML
    private Button back_button;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        racesDest = new ArrayList();
        DatabaseHandler dbHandler = new DatabaseHandler();
        book_button.setVisible(false);
        success_label.setVisible(false);
        ResultSet result = dbHandler.getRace();
        int counter = 0;
        back_button.setOnAction(event1 -> {
            back_button.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/mainThing.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
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
                    book_button.setVisible(true);
                    carrier = result2.getString(2);
                    destination_choise = result2.getString(3);
                    departureDate = result2.getString(4);
                    flight_time = result2.getString(5);
                    transplant = result2.getString(6);
                    price = result2.getString(7);
                    seats_left = result2.getString(8);
                    if (transplant == "-")
                        transplant = "Без пересадок";
                    race_info.setText("Перевозчик: " + carrier + "\n" + "Направление: " + destination_choise + "\n" + "Время вылета: " + departureDate + "\n" + "Время полета: " + flight_time + " часа" + "\n" +
                            "Пересадки: " + transplant + "\n" + "Цена: " + price + "\n" + "Осталось мест: " + seats_left);
                    book_button.setOnAction(event1 -> {
                        dbHandler2.BookSet(CommonData.user.getCurrentUser(), chosen_box+1);
                        success_label.setVisible(true);
                    });

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
    }

}
