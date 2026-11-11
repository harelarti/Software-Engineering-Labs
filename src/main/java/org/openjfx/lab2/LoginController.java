package org.openjfx.lab2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Controller of login.fxml: checks the credentials when "login" is clicked.
 */
public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorText;

    @FXML
    void onLoginClick(ActionEvent event) throws IOException {
        for (User user : App.users) {
            if (user.getName().equals(username.getText().trim())
                    && user.getPassword().equals(password.getText())) {
                // Correct details: replace the screen with the welcome screen (new fxml)
                Parent welcome = FXMLLoader.load(App.class.getResource("welcome.fxml"));
                username.getScene().setRoot(welcome);
                return;
            }
        }
        // Wrong details: show the error inside the window (no pop-up)
        errorText.setText("user or password do not match");
    }
}
