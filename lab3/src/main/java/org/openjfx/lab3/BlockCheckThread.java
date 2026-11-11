package org.openjfx.lab3;

/** Checks whether an email is blocked. LoginTask starts it and waits for it with join(). */
public class BlockCheckThread extends Thread {

    private final AttemptTracker tracker;
    private final String email;
    private long millisLeft;

    public BlockCheckThread(AttemptTracker tracker, String email) {
        super("BlockCheck-" + email);
        this.tracker = tracker;
        this.email = email;
    }

    @Override
    public void run() {
        millisLeft = tracker.blockedMillisLeft(email);
    }

    /** Valid after join(): 0 if not blocked. */
    public long getMillisLeft() {
        return millisLeft;
    }
}
