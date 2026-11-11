import java.util.regex.Pattern;

/** An immutable user whose credentials satisfy the laboratory's rules. */
public final class User {
    private static final Pattern EMAIL = Pattern.compile(
            "[A-Za-z0-9._%+\\-]+@[A-Za-z0-9][A-Za-z0-9.\\-]*\\.[A-Za-z]{2,}");
    private static final String SYMBOLS = "!@#$%^&*()-_=+";

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

        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasSymbol = false;
        for (int index = 0; index < password.length(); index++) {
            char character = password.charAt(index);
            if ((character >= 'A' && character <= 'Z')
                    || (character >= 'a' && character <= 'z')) {
                hasLetter = true;
            } else if (character >= '0' && character <= '9') {
                hasDigit = true;
            } else if (SYMBOLS.indexOf(character) >= 0) {
                hasSymbol = true;
            } else {
                throw new IllegalArgumentException("Please enter a valid password");
            }
        }
        if (!hasLetter || !hasDigit || !hasSymbol) {
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
