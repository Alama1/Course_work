package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class YourBookController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label you_races;

    @FXML
    private Button nextPageButton;

    @FXML
    private Button prevPageButton;

    @FXML
    private Button back_button;

    List raceId;
    String yourBook;
    String bookTime;
    int page = 0;
    private List listDest;
    private List listTime;


    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        raceId = new ArrayList<>();

        listDest = new ArrayList();
        listTime = new ArrayList();

        back_button.setOnAction(event -> {
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

        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getRaceId();
        while(result.next()){
            raceId.add(result.getInt(3));
        }
        Book_set(0);
        you_races.setText("Направление: "+ listDest.get(page).toString() + "\n" + "Время вылета: " + listTime.get(page).toString());

        nextPageButton.setOnAction(event -> {
            page++;
            try {
                Book_set(page);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            you_races.setText("Направление: "+ listDest.get(page).toString() + "\n" + "Время вылета: " + listTime.get(page).toString());
        });

        prevPageButton.setOnAction(event -> {
            page--;
            try {
                Book_set(page);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            you_races.setText("Направление: "+ listDest.get(page).toString() + "\n" + "Время вылета: " + listTime.get(page).toString());
        });
    }

    void Book_set(int race) throws SQLException, ClassNotFoundException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getRaceBook((Integer) raceId.get(race));
        while (result.next()) {
            listDest.add(result.getString(3));
            listTime.add(result.getString(4));
        }
    }
}
