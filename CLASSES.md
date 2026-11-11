# Lab 2 – class overview (for the PDF)

- **User** – the class from Lab 1 (moved into the `org.openjfx.lab2` package); its constructor throws `IllegalArgumentException` for an invalid email or password.
- **UserLoader** – reads the input file (`username password` per line, like Lab 1) and returns an `ArrayList<User>` with only the valid users.
- **App** – the JavaFX entry point: loads the users, opens `login.fxml`, and exits the program when the window's x is clicked.
- **LoginController** – controller of `login.fxml`; on "login" it searches the list. A match swaps the screen to `welcome.fxml`, otherwise a red error label is shown in the same window (no pop-up).
- **login.fxml / welcome.fxml** – the two screens' layouts (editable in SceneBuilder).
