package org.openjfx.lab3;

/**
 * What a login attempt ended with; LoginTask sends it to the controller.
 * attemptsLeft is -1 when the email is unknown (no tracking), secondsLeft is set only when blocked.
 */
public record LoginResult(Kind kind, int attemptsLeft, int secondsLeft) {

    public enum Kind { SUCCESS, ERROR, BLOCKED }

    static LoginResult success() {
        return new LoginResult(Kind.SUCCESS, 0, 0);
    }

    static LoginResult error(int attemptsLeft) {
        return new LoginResult(Kind.ERROR, attemptsLeft, 0);
    }

    static LoginResult blocked(long millisLeft) {
        // round up, so the countdown never ends before the block does
        return new LoginResult(Kind.BLOCKED, 0, (int) ((millisLeft + 999) / 1000));
    }

    /** The text shown under the email field. */
    String message() {
        return switch (kind) {
            case SUCCESS -> "";
            case ERROR -> attemptsLeft < 0 ? "פרטים שגויים" : "פרטים שגויים, נותרו " + attemptsLeft + " ניסיונות";
            case BLOCKED -> blockedMessage(secondsLeft);
        };
    }

    static String blockedMessage(int seconds) {
        return "החשבון חסום, נותרו " + seconds + " שניות";
    }
}
