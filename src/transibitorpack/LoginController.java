package transibitorpack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.util.List;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.ERROR, "Login Error", "Please enter both username and password.");
        } else {
            List<DataUser> users = DataUserManager.readUsersFromFile();
            boolean validCredentials = false;

            for (DataUser user : users) {
                if (user.getUsername().equals(username) && DataUserManager.hashPassword(password).equals(user.getPassword())) {
                    validCredentials = true;
                    UserSession.login(user);
                    break;
                }
            }

            if (validCredentials) {
                if (username.equals("admin")) {
                    redirectToAdminPage();
                } else {
                    redirectToMainPage();
                }
            } else {
                showAlert(AlertType.ERROR, "Login Error", "Invalid username or password.");
            }
        }
    }

    private void redirectToMainPage() {
        try {
            Parent mainPageRoot = FXMLLoader.load(getClass().getResource("FXMLHomePage.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(mainPageRoot));
            stage.setTitle("Main Page - Transibitor");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Unable to load main page.");
        }
    }


    private void redirectToAdminPage() {
        try {
            Parent adminPageRoot = FXMLLoader.load(getClass().getResource("FXMLDasboardAdmin.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(adminPageRoot));
            stage.setTitle("Admin Dashboard - Transibitor");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Unable to load admin dashboard.");
        }
    }

    @FXML
    private void handleSignUp() {
        try {
            Parent signUpRoot = FXMLLoader.load(getClass().getResource("FXMLSignup.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(signUpRoot));
            stage.setTitle("Sign Up");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Unable to load sign-up page.");
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}