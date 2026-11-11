package org.openjfx.lab3;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * Controller of login.fxml. Kept thin: reads the fields, starts a LoginTask and shows
 * its result. No counting or timing logic lives here.
 */
public class LoginController {

    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Label errorText;
    @FXML private Button loginButton;
    @FXML private Button testButton;

    private Thread countdown;

    @FXML
    void onLoginClick() {
        setBusy(true); // no double clicks while the login runs
        Thread login = new Thread(new LoginTask(username.getText().trim(), password.getText(),
                App.users, App.tracker, App.failures, this::showResult), "LoginTask");
        login.setDaemon(true);
        login.start();
    }

    /** Runs on the JavaFX thread (LoginTask calls it through Platform.runLater). */
    private void showResult(LoginResult result) {
        errorText.setText(result.message());
        switch (result.kind()) {
            case SUCCESS -> showWelcome();
            case ERROR -> setBusy(false);
            case BLOCKED -> {
                countdown = new Thread(new CountdownTask(result.secondsLeft(), errorText, () -> {
                    errorText.setText("");
                    setBusy(false);
                }), "CountdownTask");
                countdown.setDaemon(true);
                countdown.start();
            }
        }
    }

    @FXML
    void onTestClick() {
        testButton.setDisable(true);
        Thread test = new Thread(() -> {
            String report = ConcurrencyTest.run();
            System.out.println(report);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, report);
                alert.setHeaderText("Concurrency test");
                alert.show();
                testButton.setDisable(false);
            });
        }, "ConcurrencyTest");
        test.setDaemon(true);
        test.start();
    }

    /** Called when the program closes: stop the countdown if it is running. */
    void shutdown() {
        if (countdown != null) {
            countdown.interrupt();
        }
    }

    private void setBusy(boolean busy) {
        username.setDisable(busy);
        password.setDisable(busy);
        loginButton.setDisable(busy);
    }

    private void showWelcome() {
        try {
            Parent welcome = FXMLLoader.load(App.class.getResource("welcome.fxml"));
            username.getScene().setRoot(welcome);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
