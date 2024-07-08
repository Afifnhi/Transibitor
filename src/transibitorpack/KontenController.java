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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class KontenController implements Initializable {
    private DataKonten data = new DataKonten();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableColumn<DataKonten, String> AlamatVideo;

    @FXML
    private TableColumn<DataKonten, String> Kata;

    @FXML
    private TextField alamatVideo;

    @FXML
    private TextField kata;

    @FXML
    private TableView<DataKonten> tabelView;

    private DataKonten selectedDataKonten;


    @FXML
    private void addData() {
        String video = this.alamatVideo.getText();
        String kata = this.kata.getText();

        if (!video.isEmpty() && !kata.isEmpty()) {
            data.addData(kata, video);
            loadData();
        } else {
            System.err.println("Both fields must be filled.");
        }
    }

    @FXML
    private void updateData() {
        if (selectedDataKonten != null) {
            String video = this.alamatVideo.getText();
            String kata = this.kata.getText();

            if (!video.isEmpty() && !kata.isEmpty()) {
                selectedDataKonten.setUrl(video);
                selectedDataKonten.setWord(kata);
                data.updateData(selectedDataKonten);
                loadData();
                clearFields();
            } else {
                System.err.println("Both fields must be filled.");
            }
        } else {
            System.err.println("No data selected to update.");
        }
    }

    @FXML
    private void btnDelete(ActionEvent event) {
        ObservableList<DataKonten> selectedItems = tabelView.getSelectionModel().getSelectedItems();
        for (DataKonten konten : selectedItems) {
            data.removeFromXml(konten);
        }
        tabelView.getItems().removeAll(selectedItems);
    }

    @FXML
    private void onTableItemSelect(MouseEvent event) {
        selectedDataKonten = tabelView.getSelectionModel().getSelectedItem();
        if (selectedDataKonten != null) {
            alamatVideo.setText(selectedDataKonten.getUrl());
            kata.setText(selectedDataKonten.getWord());
        }
    }

    private void loadData() {
        ObservableList<DataKonten> dataList = data.getData();
        tabelView.setItems(dataList);
    }

    private void clearFields() {
        alamatVideo.clear();
        kata.clear();
        selectedDataKonten = null;
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
        AlamatVideo.setCellValueFactory(new PropertyValueFactory<>("url"));
        Kata.setCellValueFactory(new PropertyValueFactory<>("word"));

        tabelView.setOnMouseClicked(this::onTableItemSelect);
        loadData();
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}