package org.openjfx.lab3;

import javafx.application.Platform;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

/**
 * The whole login process, run on its own thread for every click on "login".
 * The result goes back to the screen through Platform.runLater, because
 * JavaFX controls may only be touched from the JavaFX thread.
 */
public class LoginTask implements Runnable {

    private final String email;
    private final String password;
    private final List<User> users;
    private final AttemptTracker tracker;
    private final BlockingQueue<FailedAttempt> failures;
    private final Consumer<LoginResult> onResult;

    public LoginTask(String email, String password, List<User> users, AttemptTracker tracker,
                     BlockingQueue<FailedAttempt> failures, Consumer<LoginResult> onResult) {
        this.email = email;
        this.password = password;
        this.users = users;
        this.tracker = tracker;
        this.failures = failures;
        this.onResult = onResult;
    }

    @Override
    public void run() {
        LoginResult result;
        try {
            result = login();
        } catch (InterruptedException e) {
            return; // program is closing
        }
        Platform.runLater(() -> onResult.accept(result));
    }

    private LoginResult login() throws InterruptedException {
        boolean known = users.stream().anyMatch(u -> u.getName().equals(email));
        if (!known) {
            return LoginResult.error(-1); // unknown email: same message, no tracking
        }

        // 1. blocked? then reject without checking the password
        BlockCheckThread check = new BlockCheckThread(tracker, email);
        check.start();
        check.join();
        if (check.getMillisLeft() > 0) {
            return LoginResult.blocked(check.getMillisLeft());
        }

        // 2. check the details with Lab 1's validation (throws on invalid input)
        try {
            User attempt = new User(email, password);
            for (User user : users) {
                if (user.getName().equals(attempt.getName()) && user.getPassword().equals(attempt.getPassword())) {
                    tracker.reset(email);
                    return LoginResult.success();
                }
            }
        } catch (IllegalArgumentException invalidPassword) {
            // not a valid password at all, so it is a wrong one
        }

        // 3. wrong password of a known email: let a FailureWorker count it and wait for its answer
        FailedAttempt failed = new FailedAttempt(email);
        failures.put(failed);
        return failed.reply().take();
    }
}
