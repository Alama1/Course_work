package sample;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class YourBookController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label you_races;

    int race_id;
    String your_book;
    String book_time;


    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getRaceId();
        while(result.next()){
            race_id = result.getInt(3);
        }
        Book_set();
        you_races.setText("Вы забронировали самолет с направлением: " + "\n" + your_book + "\n" + "Время отлета: " + book_time);
    }

    void Book_set() throws SQLException, ClassNotFoundException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getRaceBook(race_id);
        while(result.next()){
            your_book = result.getString(3);
            book_time = result.getString(4);
        }
    }

}
