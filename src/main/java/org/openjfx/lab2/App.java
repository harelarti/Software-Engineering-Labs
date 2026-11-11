package org.openjfx.lab2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

/**
 * Entry point: loads the valid users, then shows the login screen.
 */
public class App extends Application {

    /** The valid users read from the input file. */
    static ArrayList<User> users;

    @Override
    public void start(Stage stage) throws Exception {
        // Input file path can be passed as an argument; default is users.txt
        String file = getParameters().getRaw().isEmpty() ? "users.txt" : getParameters().getRaw().get(0);
        users = UserLoader.loadValidUsers(new File(file));

        FXMLLoader loader = new FXMLLoader(App.class.getResource("login.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Users Login");
        // Closing the window (x) ends the program
        stage.setOnCloseRequest(e -> Platform.exit());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
