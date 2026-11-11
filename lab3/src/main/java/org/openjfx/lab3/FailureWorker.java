package org.openjfx.lab3;

import java.util.concurrent.BlockingQueue;

/**
 * Daemon thread that takes failed attempts from the queue, updates the counter
 * and answers whether attempts are left or the email is now blocked.
 * take() sleeps while the queue is empty, so there is no busy wait.
 */
public class FailureWorker extends Thread {

    private final BlockingQueue<FailedAttempt> queue;
    private final AttemptTracker tracker;

    public FailureWorker(String name, BlockingQueue<FailedAttempt> queue, AttemptTracker tracker) {
        super(name);
        this.queue = queue;
        this.tracker = tracker;
        setDaemon(true); // does not keep the program alive after the window closes
    }

    @Override
    public void run() {
        try {
            while (true) {
                FailedAttempt attempt = queue.take();
                attempt.reply().put(tracker.recordFailure(attempt.email()));
            }
        } catch (InterruptedException e) {
            // interrupted: stop
        }
    }
}
