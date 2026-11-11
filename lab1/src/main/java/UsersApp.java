import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/** Reads users from standard input or a file supplied as the only argument. */
public final class UsersApp {
    public static void main(String[] args) {
        if (args.length > 1) {
            System.err.println("Usage: java UsersApp [users.txt]");
            System.exit(1);
            return;
        }

        ArrayList<User> users = new ArrayList<>();
        try (Scanner input = args.length == 1
                ? new Scanner(new File(args[0]), "UTF-8")
                : new Scanner(System.in, "UTF-8")) {
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] credentials = line.trim().split("\\s+");
                try {
                    if (credentials.length != 2) {
                        String message = credentials[0].isEmpty()
                                ? "Please enter a valid Email as username"
                                : "Please enter a valid password";
                        throw new IllegalArgumentException(message);
                    }
                    users.add(new User(credentials[0], credentials[1]));
                } catch (IllegalArgumentException exception) {
                    System.err.println(line + " " + exception.getMessage());
                }
            }
            if (input.ioException() != null) {
                System.err.println("Unable to read input: " + input.ioException().getMessage());
                System.exit(1);
                return;
            }
        } catch (FileNotFoundException exception) {
            System.err.println("Unable to read input: " + exception.getMessage());
            System.exit(1);
            return;
        }

        users.sort(Comparator.comparing(User::getName));
        for (User user : users) {
            System.out.println(user);
        }
    }
}
