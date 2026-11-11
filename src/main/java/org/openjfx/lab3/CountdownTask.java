package org.openjfx.lab3;

import javafx.application.Platform;
import javafx.scene.control.Label;

/** Live countdown on the screen while blocked; calls onDone (on the JavaFX thread) at the end. */
public class CountdownTask implements Runnable {

    private final int seconds;
    private final Label label;
    private final Runnable onDone;

    public CountdownTask(int seconds, Label label, Runnable onDone) {
        this.seconds = seconds;
        this.label = label;
        this.onDone = onDone;
    }

    @Override
    public void run() {
        try {
            for (int left = seconds; left > 0; left--) {
                int shown = left;
                Platform.runLater(() -> label.setText(LoginResult.blockedMessage(shown)));
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            return; // window closed in the middle of the countdown
        }
        Platform.runLater(onDone);
    }
}
