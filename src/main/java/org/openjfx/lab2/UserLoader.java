package org.openjfx.lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Reads the users input file (same format as Lab 1: "username password" per line).
 */
public class UserLoader {

    /** Reads the file and returns only the valid users; invalid lines are skipped. */
    public static ArrayList<User> loadValidUsers(File file) throws FileNotFoundException {
        ArrayList<User> users = new ArrayList<>();
        try (Scanner input = new Scanner(file, "UTF-8")) {
            while (input.hasNextLine()) {
                String[] credentials = input.nextLine().trim().split("\\s+");
                if (credentials.length != 2) {
                    continue; // malformed line
                }
                try {
                    // The User constructor (from Lab 1) throws if the details are invalid
                    users.add(new User(credentials[0], credentials[1]));
                } catch (IllegalArgumentException exception) {
                    // invalid user - not added to the list
                }
            }
        }
        return users;
    }
}
