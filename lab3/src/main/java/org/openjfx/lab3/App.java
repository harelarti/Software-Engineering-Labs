package org.openjfx.lab3;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Entry point. Reads n and t from the command line (e.g. "3 30"), starts the two
 * FailureWorker threads and shows the login screen.
 */
public class App extends Application {

    /** The valid users read from users.txt. */
    static ArrayList<User> users;
    /** Per-email failure counters and blocks. */
    static AttemptTracker tracker;
    /** Failed-login events, consumed by the FailureWorker threads. */
    static final BlockingQueue<FailedAttempt> failures = new LinkedBlockingQueue<>();

    private LoginController controller;

    @Override
    public void start(Stage stage) throws Exception {
        int[] nt = parseArgs(getParameters().getRaw());
        while (nt == null) {
            nt = askForArgs(); // wrong or missing arguments: ask the user to type them
        }
        tracker = new AttemptTracker(nt[0], nt[1]);
        users = UserLoader.loadValidUsers(new File("users.txt"));

        for (int i = 1; i <= 2; i++) {
            new FailureWorker("FailureWorker-" + i, failures, tracker).start();
        }

        FXMLLoader loader = new FXMLLoader(App.class.getResource("login.fxml"));
        stage.setScene(new Scene(loader.load()));
        controller = loader.getController();
        stage.setTitle("Users Login (n=" + nt[0] + ", t=" + nt[1] + "s)");
        // Closing the window (x) ends the program, see stop()
        stage.setOnCloseRequest(e -> Platform.exit());
        stage.show();
    }

    /** Called by JavaFX when the program exits: stop the countdown thread. */
    @Override
    public void stop() {
        if (controller != null) {
            controller.shutdown();
        }
    }

    /** Returns {n, t} if args are exactly two integers >= 1, otherwise null. */
    static int[] parseArgs(List<String> args) {
        if (args.size() != 2) {
            return null;
        }
        try {
            int n = Integer.parseInt(args.get(0).trim());
            int t = Integer.parseInt(args.get(1).trim());
            return n >= 1 && t >= 1 ? new int[]{n, t} : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /** Asks for n and t in a dialog; exits if the user cancels. */
    private static int[] askForArgs() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Missing or invalid arguments");
        dialog.setHeaderText("Expected two integers >= 1: n (max failed logins) and t (block seconds)");
        dialog.setContentText("n t:");
        String answer = dialog.showAndWait().orElse(null);
        if (answer == null) {
            Platform.exit();
            System.exit(0);
        }
        return parseArgs(List.of(answer.trim().split("\\s+")));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
