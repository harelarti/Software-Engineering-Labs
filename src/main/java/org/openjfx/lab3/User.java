package org.openjfx.lab3;

import java.util.regex.Pattern;

/** An immutable user whose credentials satisfy the laboratory's rules. */
public final class User {
    private static final Pattern EMAIL = Pattern.compile(
            "[A-Za-z0-9._%+\\-]+@[A-Za-z0-9][A-Za-z0-9.\\-]*\\.[A-Za-z]{2,}");
    /** Only letters, digits and !@#$%^&*()-_=+, with at least one of each kind. */
    private static final Pattern PASSWORD = Pattern.compile(
            "(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+])[A-Za-z0-9!@#$%^&*()\\-_=+]+");

    private final String name;
    private final String password;

    /** Rejects invalid credentials before initializing the object. */
    public User(String name, String password) {
        validateName(name);
        validatePassword(password);
        this.name = name;
        this.password = password;
    }

    private static void validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }
        if (name.length() > 50) {
            throw new IllegalArgumentException("Username is too long, try something shorter");
        }
        if (!EMAIL.matcher(name).matches()) {
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }
    }

    private static void validatePassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Please enter a valid password");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("Your password is too short, add more characters");
        }
        if (password.length() > 12) {
            throw new IllegalArgumentException("Your password is too long, try a shorter one");
        }

        if (!PASSWORD.matcher(password).matches()) {
            throw new IllegalArgumentException("Please enter a valid password");
        }
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return name + " " + password;
    }
}
