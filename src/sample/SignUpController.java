package sample;

import java.io.IOException;
import java.net.URL;
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

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private TextField secondname_field;

    @FXML
    private TextField firstname_field;

    @FXML
    private Button reg_button;

    @FXML
    private Label signUp_warning;

    @FXML
    void initialize() {
        reg_button.setOnAction(event -> {
            SignUpNewUser();
        });
    }

    private void SignUpNewUser() {

        DatabaseHandler dbHandler = new DatabaseHandler();

        String firstName = firstname_field.getText();
        String lastName = secondname_field.getText();
        String login = login_field.getText();
        String password = password_field.getText();

        User user = new User(firstName, lastName, login, password);

        if (!firstname_field.getText().equals("") && !secondname_field.getText().equals("") && !login_field.getText().equals("") && !password_field.getText().equals("")){
            dbHandler.SignUpUser(user);
            reg_button.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/sample.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            }
        else
            signUp_warning.setText("Неверный формат данных!");

    }
}
