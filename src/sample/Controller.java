package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button authButton;

    @FXML
    private TextField login_field;

    @FXML
    private Button signUpButton;

    @FXML
    private Label login_alertfield;

    @FXML
    void initialize() {

        authButton.setOnAction(event -> {
            String login_text = login_field.getText().trim();
            String password_text = password_field.getText().trim();
            if (!login_text.equals("") && !password_text.equals("")) {
                try {
                    logInUser(login_text, password_text);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            else {
                login_alertfield.setText("Неверный формат данных!");
            }

        });
        signUpButton.setOnAction(event -> {
            signUpButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/signUp.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });

    }

    private void logInUser(String logintext, String passwordtext) throws SQLException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        CommonData.user = user;
        user.setUserName(logintext);
        user.setPassword(passwordtext);
        ResultSet result = dbHandler.getUser(user);


        int counter = 0;

        while (result.next()) {
            counter++;
            String userName = (result.getString(2));
            user.setCurrentUserName(userName);
            user.setCurrentUser(logintext);
        }
        if (counter >=1){
            authButton.getScene().getWindow().hide();
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
        } else {
            login_alertfield.setText("Неверный логин и/или пароль!");
        }
    }
}
