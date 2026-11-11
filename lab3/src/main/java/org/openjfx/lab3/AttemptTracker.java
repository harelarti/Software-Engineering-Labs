package org.openjfx.lab3;

import java.util.concurrent.ConcurrentHashMap;

/** Holds the UserState of every tracked email (in memory only), plus n and t. */
public class AttemptTracker {

    private final ConcurrentHashMap<String, UserState> states = new ConcurrentHashMap<>();
    private final int n;
    private final long blockMillis;

    /** @param n max failed logins, @param t block length in seconds */
    public AttemptTracker(int n, int t) {
        this.n = n;
        this.blockMillis = t * 1000L;
    }

    /** computeIfAbsent is atomic, so two threads never get two different states for one email. */
    UserState stateOf(String email) {
        return states.computeIfAbsent(email, e -> new UserState());
    }

    public long blockedMillisLeft(String email) {
        return stateOf(email).blockedMillisLeft(System.currentTimeMillis());
    }

    public LoginResult recordFailure(String email) {
        return stateOf(email).recordFailure(n, blockMillis, System.currentTimeMillis());
    }

    public void reset(String email) {
        stateOf(email).reset();
    }
}
