package transibitorpack;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class SignUpController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private void handleSignUp() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(AlertType.ERROR, "Sign Up Error", "Please fill in all fields.");
        } else if (!password.equals(confirmPassword)) {
            showAlert(AlertType.ERROR, "Sign Up Error", "Passwords do not match.");
        } else if (!isValidEmail(email)) {
            showAlert(AlertType.ERROR, "Sign Up Error", "Please enter a valid email address.");
        } else if (password.length() < 8) {
            showAlert(AlertType.ERROR, "Sign Up Error", "Password must be at least 8 characters long.");
        } else if (DataUserManager.userExists(username)) {
            showAlert(AlertType.ERROR, "Sign Up Error", "Username already exists.");
        } else {
            String hashedPassword = DataUserManager.hashPassword(password);
            DataUser newUser = new DataUser(username, email, hashedPassword);
            List<DataUser> users = DataUserManager.readUsersFromFile();
            users.add(newUser);
            DataUserManager.writeUsersToFile(users);
            
            // Show success pop-up and redirect to login
            showSuccessAndRedirect();
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    private void showSuccessAndRedirect() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Registration Successful");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully registered! Please log in to continue.");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            redirectToSignIn();
        }
    }

    private void redirectToSignIn() {
        try {
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/Transibitor/FXMLLogin.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Unable to load login page.");
        }
    }

    @FXML
    private void handleBackToLogin() {
        redirectToSignIn();
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}