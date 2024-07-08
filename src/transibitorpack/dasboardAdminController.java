package transibitorpack;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class dasboardAdminController implements Initializable{
    SwitchScene switchScene = new SwitchScene();
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private Button btnCS;

    @FXML
    private Button btnFinance;

    @FXML
    private Button btnKonten;

    @FXML
    private void costumerServiceHandleButton(ActionEvent event){
        try {
        root = FXMLLoader.load(getClass().getResource("CostumerService.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);  
        stage.setTitle("Admin Customer Service - Transibitor");
        stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Unable to load admin Customer Service.");
        }
    }

    @FXML
    private void kontenHandleButton(ActionEvent event){
        try {
            root = FXMLLoader.load(getClass().getResource("FXMLKonten.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);  
            stage.setTitle("Admin konten - Transibitor");
            stage.show();
    
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(AlertType.ERROR, "Error", "Unable to load admin Konten.");
            }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);  
            stage.setTitle("Login - Transibitor");
            stage.show();
    
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(AlertType.ERROR, "Error", "Unable to load login page.");
            }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
