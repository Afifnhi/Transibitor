package transibitorpack;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class customerserviceController implements Initializable{

    dataCustomerService data = new dataCustomerService();
    SwitchScene switchScene = new SwitchScene();

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private Button btnHome;

    @FXML
    private Button btnInbox;
    
    @FXML
    private TableColumn<dataFeedback, String> KritikSaran;

    @FXML
    private TableColumn<dataFeedback, String> point;

    @FXML
    private TableView<dataFeedback> tabelView;

    private dataCustomerService cs = new dataCustomerService();

    @FXML
    private void btnDelete(ActionEvent event) {
        ObservableList<dataFeedback> selectedItems = tabelView.getSelectionModel().getSelectedItems();
        for (dataFeedback feedback : selectedItems) {
            dataCustomerService.removeFromXml(feedback);
        }
        tabelView.getItems().removeAll(selectedItems);
    }

    @FXML
    private void btnHome(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("FXMLDasboardAdmin.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);  
            stage.setTitle("Dashboard Admin - Transibitor");
            stage.show();
    
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(AlertType.ERROR, "Error", "Unable to load login page.");
            }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Bind the columns to the properties of dataFeedback
        KritikSaran.setCellValueFactory(new PropertyValueFactory<>("KritikSaran"));
        point.setCellValueFactory(new PropertyValueFactory<>("point"));
    
        // Set the items for the TableView
        tabelView.setItems(cs.observ());
        }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
