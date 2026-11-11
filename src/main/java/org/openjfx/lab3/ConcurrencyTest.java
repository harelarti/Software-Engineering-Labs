package org.openjfx.lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Proves no failure update is lost: 20 threads record 499 failures each (9,980 in total)
 * on the same email with n = 10,000. Then 19 more failures must not block and the 20th must.
 */
public class ConcurrencyTest {

    static final int THREADS = 20;
    static final int FAILURES_PER_THREAD = 499;
    static final int N = 10_000;

    public static String run() {
        AttemptTracker tracker = new AttemptTracker(N, 60);
        String email = "test@concurrency.com";
        CountDownLatch start = new CountDownLatch(1); // release all threads together
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREADS; i++) {
            Thread thread = new Thread(() -> {
                try {
                    start.await();
                } catch (InterruptedException e) {
                    return;
                }
                for (int j = 0; j < FAILURES_PER_THREAD; j++) {
                    tracker.recordFailure(email);
                }
            });
            threads.add(thread);
            thread.start();
        }
        start.countDown();
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                return "interrupted";
            }
        }

        int expected = THREADS * FAILURES_PER_THREAD;
        int counted = tracker.stateOf(email).getFailures();
        StringBuilder report = new StringBuilder()
                .append("20 threads x 499 failures, n = 10,000\n")
                .append("expected: ").append(expected).append(", counted: ").append(counted)
                .append(", lost updates: ").append(expected - counted).append('\n');

        boolean blockedEarly = false;
        for (int i = 0; i < 19; i++) {
            blockedEarly |= tracker.recordFailure(email).kind() == LoginResult.Kind.BLOCKED;
        }
        boolean blockedAt20th = tracker.recordFailure(email).kind() == LoginResult.Kind.BLOCKED;
        report.append("19 more failures blocked: ").append(blockedEarly).append('\n')
                .append("20th failure blocked: ").append(blockedAt20th).append('\n')
                .append(counted == expected && !blockedEarly && blockedAt20th ? "PASSED" : "FAILED");
        return report.toString();
    }
}
