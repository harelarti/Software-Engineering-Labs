package org.openjfx.lab3;

/**
 * Failure counter and block end time of one email.
 * Every method is synchronized: several threads update the same email at once.
 */
public class UserState {

    private int failures;
    /** When the block ends (System.currentTimeMillis), 0 = not blocked. */
    private long blockedUntil;

    /** Milliseconds of block left, 0 if not blocked. */
    public synchronized long blockedMillisLeft(long now) {
        expireBlock(now);
        return blockedUntil == 0 ? 0 : blockedUntil - now;
    }

    /**
     * Counts one failed login. The n-th failure starts a block of blockMillis.
     * A failure that arrives while already blocked is not counted.
     */
    public synchronized LoginResult recordFailure(int n, long blockMillis, long now) {
        expireBlock(now);
        if (blockedUntil != 0) {
            return LoginResult.blocked(blockedUntil - now);
        }
        failures++;
        if (failures >= n) {
            blockedUntil = now + blockMillis;
            return LoginResult.blocked(blockMillis);
        }
        return LoginResult.error(n - failures);
    }

    /** A successful login resets the counter. */
    public synchronized void reset() {
        failures = 0;
        blockedUntil = 0;
    }

    public synchronized int getFailures() {
        return failures;
    }

    /** When the block time has passed, the counter starts over (n new attempts). */
    private void expireBlock(long now) {
        if (blockedUntil != 0 && now >= blockedUntil) {
            failures = 0;
            blockedUntil = 0;
        }
    }
}
