import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import java.sql.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private AnchorPane login_container;

    @FXML
    private AnchorPane forget_password_container;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button btn_login;

    @FXML
    private void showForgetPasswordPane() {
        login_container.setVisible(false);  // Hide the login container
        forget_password_container.setVisible(true);  // Show the forget password container
    }

    @FXML
    private void goBack(MouseEvent event) {
        forget_password_container.setVisible(false);
        login_container.setVisible(true);  // Show the login container again
    }

    @FXML
    private void handleLoginButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill in both fields", Alert.AlertType.ERROR);
            return;
        }

        User loggedInUser = validateLogin(username, password);
        if (loggedInUser != null) {
            showAlert("Success", "Login successful!", Alert.AlertType.INFORMATION);
            logLoginAttempt(username);  // Log the successful login attempt
            switchToHomePage(loggedInUser);  // Pass User object
        } else {
            showAlert("Failed", "Invalid username or password", Alert.AlertType.ERROR);
        }
    }

    private void switchToHomePage(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent root = loader.load();

            HomePageController homePageController = loader.getController();
            homePageController.setUser(user);  // Pass the User object

            Stage stage = (Stage) login_container.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the home page", Alert.AlertType.ERROR);
        }
    }

    private User validateLogin(String username, String password) {
        User user = null;
        try (Connection conn = DatabaseConnection.connect()) {
            String query = "SELECT id, firstname, lastname FROM rentcar.admins WHERE id = ? AND pass = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    String id = rs.getString("id");
                    String firstname = rs.getString("firstname");
                    String lastname = rs.getString("lastname");
                    user = new User(id,password, firstname, lastname);  // Create User object
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database connection error", Alert.AlertType.ERROR);
        }
        return user;
    }

    private void logLoginAttempt(String username) {
        String query = "INSERT INTO rentcar.access_track (admin_id, access, access_time) VALUES (?, ?, CURRENT_TIMESTAMP)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, "log_in");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to log login attempt", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
