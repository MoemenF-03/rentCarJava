import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class HomePageController implements Initializable {
    // ____ ADMIN & DASHBOARD ATT ____ //
    String adminid;
    @FXML
    private Button logout_btn;
    @FXML
    private Button Dashboard_btn;
    @FXML
    private Button rent_btn_p;
    @FXML
    private Button cars_btn;
    @FXML
    private Button clients_btn;
    @FXML
    private AnchorPane dashboardPane;
    @FXML
    private AnchorPane rentPane;
    @FXML
    private AnchorPane carsPane;
    @FXML
    private AnchorPane clientsPane;

    private User loggedInUser;
    @FXML
    private TextField admin_profile_name;
    @FXML
    private TextField admin_profile_last;
    @FXML
    private TextField admin_profile_pass;
    @FXML
    private Button update_admin_infos;
    @FXML
    private Text Admin_Name_Welcome;
    @FXML
    private Button delete_acc;
    @FXML
    private Text av_cars_number;
    @FXML
    private Text tot_earn;
    @FXML
    private Text admin_id_infos;
    //END  ____ ADMIN & DASHBOARD ATT ____ //
    
    // ---------------- BEGIN CARS ATT //
    @FXML
    private TextField cars_brand;
    @FXML
    private TextArea cars_desc;
    @FXML
    private TextField cars_id;
    @FXML
    private ComboBox<String> cars_status;
    @FXML
    private Button cars_insert_btn;
    @FXML
    private Button cars_clear_btn;
    @FXML
    private Button cars_update_btn;
    @FXML
    private Button cars_delete_btn;
    @FXML
    private TextField cars_model;
    @FXML
    private TextField cars_price;
    @FXML
    private TableView<Car> cars_table;
    @FXML
    private TableColumn<Car, String> cars_col_id;
    @FXML
    private TableColumn<Car, String> cars_col_brand;
    @FXML
    private TableColumn<Car, String> cars_col_model;
    @FXML
    private TableColumn<Car, String> cars_col_status;
    @FXML
    private TableColumn<Car, Double> cars_col_price;
    // ---------------- END CARS ATT //

   // ---------------- BEGIN CLIENTS ATT //
    @FXML
    private TextField Clients_id;
    @FXML
    private TextArea Clients_adress;
    @FXML
    private TextField Clients_phone;
    @FXML
    private Button Clients_btn_insert;
    @FXML
    private Button Clients_btn_clear;
    @FXML
    private Button Clients_btn_update;
    @FXML
    private Button Clients_btn_delete;
    @FXML
    private TextField Clients_firstname;
    
    @FXML
    private TextField Clients_lastname;
    @FXML
    private TableView<Client> clients_table; 
    @FXML
    private TableColumn<Client, String> clients_col_id;        
    @FXML
    private TableColumn<Client, String> clients_col_firstname; 
    @FXML
    private TableColumn<Client, String> clients_col_lastname;  
    @FXML
    private TableColumn<Client, Integer> clients_col_phone;    
    @FXML
    private TableColumn<Client, String> clients_col_adress;    
    @FXML
    private Text nb_clients;
    // ---------------- END CLIENTS ATT //
    
    // ---------------- BEGIN RENT ATT //
    @FXML
    private TextField rent_carid;
    @FXML
    private TextField rent_clientid;
    @FXML
    private DatePicker date_rent;
    @FXML
    private DatePicker date_return;
    @FXML
    private Label tot_price_text;
    @FXML
    private Button rent_btn;
    @FXML
    private Button get_tot_price_btn;
   @FXML
    private TableView<Car> available_table;
    @FXML
    private TableColumn<Car, String> available_col_id;
    @FXML
    private TableColumn<Car, String> available_col_brand;
    @FXML
    private TableColumn<Car, String> available_col_model;
    @FXML
    private TableColumn<Car, String> available_col_status;
    @FXML
    private TableColumn<Car, Double> available_col_price;
    // ---------------- END RENT ATT//
    


    public void setUser(User user) {
        this.loggedInUser = user;
        initializeUserData();
    }
    
    
    // ---------------- BEGIN INIT METHODS //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showDashboard();
        update_admin_infos.setOnAction(e -> handleUpdateAdminInfos());
        logout_btn.setOnAction(e -> handleLogout());
        Dashboard_btn.setOnAction(e -> showDashboard());
        rent_btn_p.setOnAction(e -> showRentPane());
        cars_btn.setOnAction(e -> showCarsPane());
        clients_btn.setOnAction(e -> showClientsPane());
        delete_acc.setOnAction(e -> handleDeleteAcc());
        cars_status.setItems(FXCollections.observableArrayList("available", "rented"));
        cars_insert_btn.setOnAction(e -> handleInsertCar());
        cars_clear_btn.setOnAction(e -> handleClearCar());
        cars_delete_btn.setOnAction(e -> handleDeleteCar());
        cars_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
cars_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
cars_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
cars_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
cars_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
cars_update_btn.setOnAction(e -> handleUpdateCar());
        fetchCars();
        fetchClientCount();
        fetchClients();
        Clients_btn_insert.setOnAction(e -> handleInsertClient());
        clients_col_id.setCellValueFactory(new PropertyValueFactory<>("clientId"));
    clients_col_firstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    clients_col_lastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    clients_col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
    clients_col_adress.setCellValueFactory(new PropertyValueFactory<>("address"));
    Clients_btn_clear.setOnAction(e -> handleClearClient());
    Clients_btn_delete.setOnAction(e -> handleDeleteClient());
    Clients_btn_update.setOnAction(e -> handleUpdateClient());
    get_tot_price_btn.setOnAction(e -> handleTotalPrice());
    rent_btn.setOnAction(e -> handleRent());
    available_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
    available_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
    available_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
    available_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
    available_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
    fetchAvailableCars();
    }
    private void initializeUserData() {
        if (loggedInUser != null) {
            Admin_Name_Welcome.setText(loggedInUser.getFirst_Name());
            admin_profile_name.setText(loggedInUser.getFirst_Name());
            admin_profile_last.setText(loggedInUser.getLast_Name());
            admin_profile_pass.setText(loggedInUser.getPassword());
            admin_id_infos.setText(loggedInUser.getId());
            adminid=loggedInUser.getId();
            
        }
    }
    // ---------------- END INIT METHODS //
    
    
    
// ---------------- BEGIN ADMIN_DASHBOARD PANE //
public void handleUpdateAdminInfos() {
        // Retrieve updated information from the TextFields
        String updatedFirstName = admin_profile_name.getText();
        String updatedLastName = admin_profile_last.getText();
        String updatedPassword = admin_profile_pass.getText();

        // Update the loggedInUser object with the new information
        loggedInUser.setFirst_Name(updatedFirstName);
        loggedInUser.setLast_Name(updatedLastName);
        loggedInUser.setPassword(updatedPassword);

        // Optionally, save the updated user information to the database
        if (updateAdminInDatabase()) {
            System.out.println("User information updated in database: " + updatedFirstName + " " + updatedLastName);
            Admin_Name_Welcome.setText(updatedFirstName); // Optional: Update welcome message
        }
    }
private boolean updateAdminInDatabase() {
        String updateSQL = "UPDATE rentcar.admins SET firstname = ?, lastname = ?, pass = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect(); 
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

            // Set the parameters in the PreparedStatement
            pstmt.setString(1, loggedInUser.getFirst_Name());
            pstmt.setString(2, loggedInUser.getLast_Name());
            pstmt.setString(3, loggedInUser.getPassword());
            pstmt.setString(4, loggedInUser.getId());  // Assuming getId() returns an int or a suitable type

            // Execute the update query
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Return false if there was an error
        }
    }
private void updateAvailableCarsCount() {
    String countSQL = "SELECT COUNT(*) FROM rentcar.cars WHERE available = TRUE";
    int availableCars = 0;

    try (Connection conn = DatabaseConnection.connect();
         PreparedStatement pstmt = conn.prepareStatement(countSQL)) {

        // Execute the query and get the result
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                availableCars = rs.getInt(1); // Retrieve the count from the first column
            }
        }

        // Update the UI with the available cars count
        av_cars_number.setText(String.valueOf(availableCars));

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public void updateTot_earn() {
    String sumSQL = "SELECT sum(total_earn) FROM rentcar.rent";
    double total_earnings = 0;

    try (Connection conn = DatabaseConnection.connect(); 
         PreparedStatement pstmt = conn.prepareStatement(sumSQL)) {

        // Execute the query
        ResultSet rs = pstmt.executeQuery();

        // Retrieve the sum from the result set
        if (rs.next()) {
            total_earnings = rs.getDouble(1); // Get the first column of the result (sum)
        }

        // Optionally, update the UI with the total earnings (if you have a TextField or label)
        tot_earn.setText(total_earnings+" TND");

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public void handleDeleteAcc() {
    // Confirm account deletion (Optional)
    boolean confirmDelete = showConfirmationDialog();
    if (!confirmDelete) {
        return; // Exit if the deletion is not confirmed
    }

    // Delete the user account from the database
    if (deleteAccountFromDatabase()) {
        System.out.println("Account deleted successfully.");

        // Redirect to login screen after successful deletion
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent loginRoot = loader.load();
            Stage currentStage = (Stage) delete_acc.getScene().getWindow();
            Scene loginScene = new Scene(loginRoot);
            String css = this.getClass().getResource("/style_login.css").toExternalForm();
            loginScene.getStylesheets().add(css);
            currentStage.setScene(loginScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } else {
        System.out.println("Account deletion failed.");
    }
}
private boolean deleteAccountFromDatabase() {
    String deleteSQL = "DELETE FROM rentcar.admins WHERE id = ?";

    try (Connection conn = DatabaseConnection.connect();
         PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {

        // Set the user's id for the deletion query
        pstmt.setString(1, loggedInUser.getId()); // Assuming getId() returns a String or appropriate type

        // Execute the DELETE query
        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0; // Return true if the deletion was successful

    } catch (SQLException e) {
        e.printStackTrace();
        return false; // Return false if there was an error
    }
}
    //  ----------------  END ADMIN_DASHBOARD PANE //

// ---------------- BEGIN NAV PANE //
    private void hideAllPanes() {
        dashboardPane.setVisible(false);
        rentPane.setVisible(false);
        carsPane.setVisible(false);
        clientsPane.setVisible(false);
        fetchCars();
        fetchClients();
        fetchAvailableCars();
    }
    private void showDashboard() {
        hideAllPanes();
        updateAvailableCarsCount();
        updateTot_earn();
        dashboardPane.setVisible(true);
    }
    private void showRentPane() {
        hideAllPanes();
        fetchAvailableCars();
        rentPane.setVisible(true);
    }
    private void showCarsPane() {
        hideAllPanes();
        carsPane.setVisible(true);
    }
    private void showClientsPane() {
        hideAllPanes();
        clientsPane.setVisible(true);
    }
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent loginRoot = loader.load();
            Stage currentStage = (Stage) logout_btn.getScene().getWindow();
            Scene loginScene = new Scene(loginRoot);
            String css = this.getClass().getResource("/style_login.css").toExternalForm();
            loginScene.getStylesheets().add(css);
            currentStage.setScene(loginScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Optionally show an alert for error
        }
    }
   //  ----------------  END NAVE PANE //

private boolean showConfirmationDialog() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Delete Account");
    alert.setHeaderText("Are you sure you want to delete your account?");
    alert.setContentText("This action cannot be undone.");

    Optional<ButtonType> result = alert.showAndWait();
    return result.isPresent() && result.get() == ButtonType.OK;
}
private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
private void showAlert(String title, String message, Alert.AlertType alertType) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

// ---------------- BEGIN Cars Pane //
public void handleInsertCar() {
    // Check if all fields are filled
    if (cars_model.getText().isEmpty() || 
        cars_price.getText().isEmpty() || 
        cars_brand.getText().isEmpty() || 
        cars_desc.getText().isEmpty() || 
        cars_id.getText().isEmpty() || 
        cars_status.getSelectionModel().isEmpty()) {
        
        // Display an alert if any field is empty
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText("Please fill in all fields and select a status.");
        alert.showAndWait();
        return;
    }
    
    try {
        // Retrieve field values
        String id = cars_id.getText();
        String model = cars_model.getText();
        String brand = cars_brand.getText();
        String description = cars_desc.getText();
        double price = Double.parseDouble(cars_price.getText());
        String status = cars_status.getSelectionModel().getSelectedItem().toString();

        // Use the DatabaseConnection class
        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection conn = dbConnection.connect(); 

        String insertSQL = "INSERT INTO rentcar.cars  VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(insertSQL);
        pstmt.setString(1, id);
        pstmt.setString(2, brand);
        pstmt.setString(3, model);
        pstmt.setDouble(4, price);
        if(status.equals("available")){
            pstmt.setBoolean(5, true);
        }else{
            pstmt.setBoolean(5, false);
        }
        
        pstmt.setString(6, description);
        
        int rowsInserted = pstmt.executeUpdate();
        
        if (rowsInserted > 0) {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Car inserted successfully!");
            successAlert.showAndWait();
        }
        
        pstmt.close();
        conn.close();
    } catch (NumberFormatException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a valid number for price.");
        alert.showAndWait();
    } catch (SQLException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database Error");
        alert.setHeaderText(null);
        alert.setContentText("Failed to insert car into the database. Please check your connection and query.");
        alert.showAndWait();
        e.printStackTrace();
    }finally{
        fetchCars();
    }
}
public void handleDeleteCar() {
    String carId = cars_id.getText().trim();

    if (carId.isEmpty()) {
        // Notify the user that the Car ID field is empty
        showAlert(Alert.AlertType.WARNING, "Input Error", "Car ID field cannot be empty.");
        return;
    }

    String queryCheck = "SELECT * FROM rentcar.cars WHERE id = ?";
    String queryDelete = "DELETE FROM rentcar.cars WHERE id = ?";

    try (Connection conn = DatabaseConnection.connect();
         PreparedStatement checkStmt = conn.prepareStatement(queryCheck)) {
        
        checkStmt.setString(1, carId);
        ResultSet rs = checkStmt.executeQuery();

        if (!rs.next()) {
            // Notify the user that the car does not exist
            showAlert(Alert.AlertType.INFORMATION, "Not Found", "Car with ID " + carId + " does not exist.");
        } else {
            // Confirmation dialog
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Delete Confirmation");
            confirmAlert.setHeaderText("Delete Car");
            confirmAlert.setContentText("Are you sure you want to delete the car with ID " + carId + "?");
            Optional<ButtonType> result = confirmAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try (PreparedStatement deleteStmt = conn.prepareStatement(queryDelete)) {
                    deleteStmt.setString(1, carId);
                    int rowsAffected = deleteStmt.executeUpdate();
                    if (rowsAffected > 0) {
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Car deleted successfully.");
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete car.");
                    }
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while accessing the database.");
    }finally{
        fetchCars();
    }
}
public void fetchCars() {
    ObservableList<Car> carList = FXCollections.observableArrayList();
    String query = "SELECT id, brand, model, available, price FROM rentcar.cars";

    try (Connection conn = DatabaseConnection.connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        while (rs.next()) {
            String id = rs.getString("id");
            String brand = rs.getString("brand");
            String model = rs.getString("model");
            String status = rs.getBoolean("available") ? "Available" : "Rented";
            double price = rs.getDouble("price");
            
            carList.add(new Car(id, brand, model, status, price));
        }

        cars_table.setItems(carList);
    } catch (SQLException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while fetching car data.");
    }
}
public void handleClearCar(){
    cars_id.clear();
    cars_model.clear();
    cars_brand.clear();
    cars_price.clear();
    cars_desc.clear();
    cars_status.getSelectionModel().clearSelection();
}
public void handleUpdateCar() {
    String id = cars_id.getText().trim();

    if (id.isEmpty()) {
        showAlert("Error", "Car ID is required to update a car.", Alert.AlertType.ERROR);
        return;
    }

    try (Connection conn = DatabaseConnection.connect()) {
        String checkQuery = "SELECT * FROM rentcar.cars WHERE id = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
        checkStmt.setString(1, id);
        ResultSet rs = checkStmt.executeQuery();

        if (!rs.next()) {
            showAlert("Error", "No car found with ID: " + id, Alert.AlertType.ERROR);
            return;
        }

        String brand = cars_brand.getText().trim();
        String model = cars_model.getText().trim();
        String price = cars_price.getText().trim();
        String carDesc = cars_desc.getText().trim();
        Boolean available = cars_status.getValue() != null && cars_status.getValue().toString().equalsIgnoreCase("available");

        if (brand.isEmpty() && model.isEmpty() && price.isEmpty() && carDesc.isEmpty() && cars_status.getValue() == null) {
            showAlert("Information", "No changes detected. Nothing to update.", Alert.AlertType.INFORMATION);
            return;
        }

        StringBuilder updateQuery = new StringBuilder("UPDATE rentcar.cars SET ");
        boolean first = true;

        if (!brand.isEmpty()) {
            updateQuery.append("brand = ?");
            first = false;
        }
        if (!model.isEmpty()) {
            updateQuery.append(first ? "" : ", ").append("model = ?");
            first = false;
        }
        if (!price.isEmpty()) {
            updateQuery.append(first ? "" : ", ").append("price = ?");
            first = false;
        }
        if (!carDesc.isEmpty()) {
            updateQuery.append(first ? "" : ", ").append("car_desc = ?");
            first = false;
        }
        if (cars_status.getValue() != null) {
            updateQuery.append(first ? "" : ", ").append("available = ?");
        }
        updateQuery.append(" WHERE id = ?");
        
        PreparedStatement updateStmt = conn.prepareStatement(updateQuery.toString());
        int paramIndex = 1;

        if (!brand.isEmpty()) {
            updateStmt.setString(paramIndex++, brand);
        }
        if (!model.isEmpty()) {
            updateStmt.setString(paramIndex++, model);
        }
        if (!price.isEmpty()) {
            updateStmt.setDouble(paramIndex++, Double.parseDouble(price));
        }
        if (!carDesc.isEmpty()) {
            updateStmt.setString(paramIndex++, carDesc);
        }
        if (cars_status.getValue() != null) {
            updateStmt.setBoolean(paramIndex++, available);
        }
        updateStmt.setString(paramIndex, id);

        int rowsAffected = updateStmt.executeUpdate();
        if (rowsAffected > 0) {
            showAlert("Success", "Car updated successfully!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to update car.", Alert.AlertType.ERROR);
        }
    } catch (Exception ex) {
        showAlert("Error", "An error occurred: " + ex.getMessage(), Alert.AlertType.ERROR);
    }finally{
        fetchCars();
    }
}
// ---------------- END Cars Pane //

// ---------------- BEGIN Clients Pane//
public void handleInsertClient() {
    String clientId = Clients_id.getText().trim();
    String firstName = Clients_firstname.getText().trim();
    String lastName = Clients_lastname.getText().trim();
    String phone = Clients_phone.getText().trim();
    String address = Clients_adress.getText().trim();

    // Check if all fields are filled
    if (clientId.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || address.isEmpty()) {
        showAlert("Error", "All fields must be filled.", Alert.AlertType.ERROR);
        return;
    }

    // Validate phone number (must be exactly 8 digits)
    if (!phone.matches("\\d{8}")) {
        showAlert("Error", "Phone number must be exactly 8 digits.", Alert.AlertType.ERROR);
        return;
    }

    try (Connection conn = DatabaseConnection.connect()) {
        String insertQuery = "INSERT INTO rentcar.clients (client_id, firstname, lastname, phone, adress) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
        insertStmt.setString(1, clientId);
        insertStmt.setString(2, firstName);
        insertStmt.setString(3, lastName);
        insertStmt.setInt(4, Integer.parseInt(phone));
        insertStmt.setString(5, address);

        int rowsAffected = insertStmt.executeUpdate();
        if (rowsAffected > 0) {
            showAlert("Success", "Client inserted successfully!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to insert client.", Alert.AlertType.ERROR);
        }
    } catch (Exception ex) {
        showAlert("Error", "An error occurred: " + ex.getMessage(), Alert.AlertType.ERROR);
    }finally{
        fetchClients();
        
    }
}
public void fetchClients() {
    try (Connection conn = DatabaseConnection.connect()) {
        String fetchQuery = "SELECT * FROM rentcar.clients";
        PreparedStatement fetchStmt = conn.prepareStatement(fetchQuery);
        ResultSet rs = fetchStmt.executeQuery();

        // Clear the table before adding new data
        clients_table.getItems().clear();

        // Iterate through the result set and add data to the table
        while (rs.next()) {
            String clientId = rs.getString("client_id");
            String firstName = rs.getString("firstname");
            String lastName = rs.getString("lastname");
            int phone = rs.getInt("phone");
            String address = rs.getString("adress");

            // Create a client object for each row and add it to the table
            Client client = new Client(clientId, firstName, lastName, phone, address);
            clients_table.getItems().add(client);
        }
    } catch (SQLException ex) {
        showAlert("Error", "An error occurred while fetching clients: " + ex.getMessage(), Alert.AlertType.ERROR);
    }finally{
        fetchClientCount();
    }
}
public void fetchClientCount() {
    String query = "SELECT COUNT(*) AS nb_clients FROM rentcar.clients";
    try (Connection conn = DatabaseConnection.connect()) {
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            int clientCount = rs.getInt("nb_clients");  // Get the count from the query result
            nb_clients.setText(String.valueOf(clientCount));  // Display the count in the Text element
        }
    } catch (SQLException e) {
        e.printStackTrace();
        nb_clients.setText("Error fetching client count.");
    }
}
public void handleClearClient() {
    // Clear the text fields
    Clients_id.clear();
    Clients_firstname.clear();
    Clients_lastname.clear();
    Clients_phone.clear();
    
    // Clear the text area
    Clients_adress.clear();
    
    // Optionally, reset the ComboBox if you have one
    // If you have a ComboBox, you can set it back to null or reset it to a default value
    // For example: clients_status.setValue(null);
}
public void handleDeleteClient() {
    String clientId = Clients_id.getText().trim();

    // Check if the client ID field is empty
    if (clientId.isEmpty()) {
        showAlert("Error", "Client ID is required to delete a client.", Alert.AlertType.ERROR);
        return;
    }

    try (Connection conn = DatabaseConnection.connect()) {
        // Check if the client exists in the database
        String checkQuery = "SELECT * FROM rentcar.clients WHERE client_id = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
        checkStmt.setString(1, clientId);
        ResultSet rs = checkStmt.executeQuery();

        if (!rs.next()) {
            // No client found with the given ID
            showAlert("Error", "No client found with ID: " + clientId, Alert.AlertType.ERROR);
            return;
        }

        // Show a confirmation alert before deletion
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText("Are you sure you want to delete this client?");
        confirmationAlert.setContentText("Client ID: " + clientId);

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Perform deletion if confirmed
            String deleteQuery = "DELETE FROM rentcar.clients WHERE client_id = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
            deleteStmt.setString(1, clientId);
            int rowsAffected = deleteStmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Success", "Client deleted successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to delete client.", Alert.AlertType.ERROR);
            }
        }
    } catch (Exception ex) {
        showAlert("Error", "An error occurred: " + ex.getMessage(), Alert.AlertType.ERROR);
    }finally{
        fetchClients();
    }
}
public void handleUpdateClient() {
    String clientId = Clients_id.getText().trim();

    // Check if the client ID is filled
    if (clientId.isEmpty()) {
        showAlert("Error", "Client ID is required to update a client.", Alert.AlertType.ERROR);
        return;
    }

    try (Connection conn = DatabaseConnection.connect()) {
        // Check if the client exists in the database
        String checkQuery = "SELECT * FROM rentcar.clients WHERE client_id = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
        checkStmt.setString(1, clientId);
        ResultSet rs = checkStmt.executeQuery();

        if (!rs.next()) {
            // No client found with the given ID
            showAlert("Error", "No client found with ID: " + clientId, Alert.AlertType.ERROR);
            return;
        }

        // Get the values from the form fields
        String firstName = Clients_firstname.getText().trim();
        String lastName = Clients_lastname.getText().trim();
        String phone = Clients_phone.getText().trim();
        String address = Clients_adress.getText().trim();

        // Build the update query
        StringBuilder updateQuery = new StringBuilder("UPDATE rentcar.clients SET ");
        boolean first = true;

        if (!firstName.isEmpty()) {
            updateQuery.append("firstname = ?");
            first = false;
        }
        if (!lastName.isEmpty()) {
            updateQuery.append(first ? "" : ", ").append("lastname = ?");
            first = false;
        }
        if (!phone.isEmpty()) {
            updateQuery.append(first ? "" : ", ").append("phone = ?");
            first = false;
        }
        if (!address.isEmpty()) {
            updateQuery.append(first ? "" : ", ").append("adress = ?");
        }
        updateQuery.append(" WHERE client_id = ?");
        
        // If no field is filled, inform the user that nothing was changed
        if (firstName.isEmpty() && lastName.isEmpty() && phone.isEmpty() && address.isEmpty()) {
            showAlert("Info", "No changes were made because all fields are empty.", Alert.AlertType.INFORMATION);
            return;
        }

        // Prepare the update statement
        PreparedStatement updateStmt = conn.prepareStatement(updateQuery.toString());
        int paramIndex = 1;

        // Set the new values for the fields if they are not empty
        if (!firstName.isEmpty()) {
            updateStmt.setString(paramIndex++, firstName);
        }
        if (!lastName.isEmpty()) {
            updateStmt.setString(paramIndex++, lastName);
        }
        if (!phone.isEmpty()) {
            updateStmt.setInt(paramIndex++, Integer.parseInt(phone));  // Assuming phone is stored as an int
        }
        if (!address.isEmpty()) {
            updateStmt.setString(paramIndex++, address);
        }
        updateStmt.setString(paramIndex, clientId);

        // Execute the update
        int rowsAffected = updateStmt.executeUpdate();
        if (rowsAffected > 0) {
            showAlert("Success", "Client updated successfully!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to update client.", Alert.AlertType.ERROR);
        }
    } catch (Exception ex) {
        showAlert("Error", "An error occurred: " + ex.getMessage(), Alert.AlertType.ERROR);
    }finally{
        fetchClients();
    }
}
// ---------------- END Clients Pane//


// ---------------- BEGIN Rent Pane//
public void handleTotalPrice() {
    String carId = rent_carid.getText().trim();  // Assuming rent_carid is a TextField

    // Check if the car ID is filled
    if (carId.isEmpty()) {
        showAlert("Error", "Car ID is required to calculate the total price.", Alert.AlertType.ERROR);
        return;
    }

    try (Connection conn = DatabaseConnection.connect()) {
        // Check if the car exists in the database
        String checkQuery = "SELECT * FROM rentcar.cars WHERE id = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
        checkStmt.setString(1, carId);
        ResultSet rs = checkStmt.executeQuery();

        if (!rs.next()) {
            // No car found with the given ID
            showAlert("Error", "No car found with ID: " + carId, Alert.AlertType.ERROR);
            return;
        }

        // Check if the car is available
        boolean available = rs.getBoolean("available");
        if (!available) {
            showAlert("Error", "The car with ID " + carId + " is not available.", Alert.AlertType.ERROR);
            return;
        }

        // Get the rental and return dates
        LocalDate rentDate = date_rent.getValue();  // Assuming date_rent is a DatePicker
        LocalDate returnDate = date_return.getValue();  // Assuming date_return is a DatePicker

        // Check if both dates are selected and date_return > date_rent
        if (rentDate == null || returnDate == null) {
            showAlert("Error", "Please select both rental and return dates.", Alert.AlertType.ERROR);
            return;
        }

        if (returnDate.isBefore(rentDate)) {
            showAlert("Error", "Return date must be after rental date.", Alert.AlertType.ERROR);
            return;
        }

        // Calculate the number of days between the rental and return dates
        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(rentDate, returnDate);

        // Extract the price of the car
        double price = rs.getDouble("price");

        // Calculate the total price
        double totalPrice = price * daysBetween;

        // Display the total price in the format "Total: x.xx TND"
        tot_price_text.setText(String.format("Total: %.2f TND", totalPrice));

    } catch (Exception ex) {
        showAlert("Error", "An error occurred: " + ex.getMessage(), Alert.AlertType.ERROR);
    }
}
public void handleRent() {
    String carId = rent_carid.getText().trim();
    String clientId = rent_clientid.getText().trim();
    LocalDate rentDate = date_rent.getValue();
    LocalDate returnDate = date_return.getValue();
    int randomNumber = ThreadLocalRandom.current().nextInt(1, 100000);
String idRent=clientId+carId+randomNumber;
    // Check if all fields are filled
    if (carId.isEmpty() || clientId.isEmpty() || rentDate == null || returnDate == null) {
        showAlert("Error", "All fields must be filled.", Alert.AlertType.ERROR);
        return;
    }

    // Check if return date is after rent date
    if (!returnDate.isAfter(rentDate)) {
        showAlert("Error", "Return date must be after rent date.", Alert.AlertType.ERROR);
        return;
    }

    try (Connection conn = DatabaseConnection.connect()) {
        // Verify if the car exists and is available
        String carCheckQuery = "SELECT * FROM rentcar.cars WHERE id = ? AND available = TRUE";
        PreparedStatement carCheckStmt = conn.prepareStatement(carCheckQuery);
        carCheckStmt.setString(1, carId);
        ResultSet carRs = carCheckStmt.executeQuery();

        if (!carRs.next()) {
            showAlert("Error", "Car not found or not available.", Alert.AlertType.ERROR);
            return;
        }

        // Verify if the client exists
        String clientCheckQuery = "SELECT * FROM rentcar.clients WHERE client_id = ?";
        PreparedStatement clientCheckStmt = conn.prepareStatement(clientCheckQuery);
        clientCheckStmt.setString(1, clientId);
        ResultSet clientRs = clientCheckStmt.executeQuery();

        if (!clientRs.next()) {
            showAlert("Error", "Client not found.", Alert.AlertType.ERROR);
            return;
        }

        // Calculate total price
        double totalPrice = calculateTotalPrice(carId, rentDate, returnDate);

        // Insert into rent table
        String insertRentQuery = "INSERT INTO rentcar.rent (id_rent,id_car, id_client,id_admin, total_earn, date_rent, date_return) VALUES (?,?,?, ?, ?, ?, ?)";
        PreparedStatement insertStmt = conn.prepareStatement(insertRentQuery);
        insertStmt.setString(1, idRent);
        insertStmt.setString(2, carId);
        insertStmt.setString(3, clientId);
        insertStmt.setString(4, this.adminid);
        insertStmt.setDouble(5, totalPrice);
        insertStmt.setDate(6, Date.valueOf(rentDate)); // Convert LocalDate to SQL Date
        insertStmt.setDate(7, Date.valueOf(returnDate)); // Convert LocalDate to SQL Date

        int rowsInserted = insertStmt.executeUpdate();
        if (rowsInserted > 0) {
            showAlert("Success", "Rent successfully created.", Alert.AlertType.INFORMATION);
            
        String carUpdateQuery = "UPDATE rentcar.cars SET available = false WHERE id = ?";
        PreparedStatement carUpdateStmt = conn.prepareStatement(carUpdateQuery);
        carUpdateStmt.setString(1, carId);
        carUpdateStmt.executeUpdate();
            
        } else {
            showAlert("Error", "Failed to create rent.", Alert.AlertType.ERROR);
        }
    } catch (SQLException e) {
        showAlert("Error", "Database error: " + e.getMessage(), Alert.AlertType.ERROR);
    }finally{
        fetchAvailableCars();
    }
}
private double calculateTotalPrice(String carId, LocalDate rentDate, LocalDate returnDate) {
    double pricePerDay = 0;

    try (Connection conn = DatabaseConnection.connect()) {
        String priceQuery = "SELECT price FROM rentcar.cars WHERE id = ?";
        PreparedStatement priceStmt = conn.prepareStatement(priceQuery);
        priceStmt.setString(1, carId);
        ResultSet rs = priceStmt.executeQuery();

        if (rs.next()) {
            pricePerDay = rs.getDouble("price");
        }

        // Calculate number of days between rentDate and returnDate
        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(rentDate, returnDate);

        // Total price is price per day multiplied by number of days
        return pricePerDay * daysBetween;
    } catch (SQLException e) {
        showAlert("Error", "Failed to fetch car price: " + e.getMessage(), Alert.AlertType.ERROR);
        return 0;
    }
}
public void fetchAvailableCars() {
    ObservableList<Car> availableCarList = FXCollections.observableArrayList();
    String query = "SELECT id, brand, model, available, price FROM rentcar.cars WHERE available = true";

    try (Connection conn = DatabaseConnection.connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        while (rs.next()) {
            String id = rs.getString("id");
            String brand = rs.getString("brand");
            String model = rs.getString("model");
            String status = rs.getBoolean("available") ? "Available" : "Rented"; // This should always be "Available" in this case
            double price = rs.getDouble("price");
            
            // Add the available car to the list
            availableCarList.add(new Car(id, brand, model, status, price));
        }
        
        // Set the items of the table to display the available cars
        available_table.setItems(availableCarList);

    } catch (SQLException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while fetching available car data.");
    }
}
// ---------------- END Rent Pane//
}
