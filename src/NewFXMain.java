import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NewFXMain extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load FXML file for the login page
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            
            // Create scene and set it
            Scene scene = new Scene(root, 1000, 500);
            
            primaryStage.setTitle("Car Rental Management");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            
            // Apply the CSS style
            String css = this.getClass().getResource("/style_login.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            // Show the stage
            primaryStage.show();
            
            // Attempt to connect to the database when the app starts
            connectToDatabase();
        } catch (IOException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Helper method to test the database connection.
     */
    private void connectToDatabase() {
        try {
            Connection conn = DatabaseConnection.connect();
            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
        }
    }

    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }
}
