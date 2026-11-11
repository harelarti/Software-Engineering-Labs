package org.openjfx.lab3;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/** A failed login of a known email, plus a one-slot queue the FailureWorker answers through. */
public record FailedAttempt(String email, BlockingQueue<LoginResult> reply) {

    public FailedAttempt(String email) {
        this(email, new ArrayBlockingQueue<>(1));
    }
}
