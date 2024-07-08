package transibitorpack;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class feedbackController implements Initializable{

    public static dataFeedback data = new dataFeedback();
    
    @FXML
    private TextField KritikSaran;

    @FXML
    Stage stage;

    @FXML
    Button btnKirim;

    @FXML
    Button bint1;

    @FXML
    Button bint2;

    @FXML
    Button bint3;

    @FXML
    Button bint4;

    @FXML
    Button bint5;


    @FXML
    private void kirimHandleAction(ActionEvent event) throws IOException{
        data.setKritikSaran(KritikSaran.getText());
        data.object_XML(data);
    
        stage = (Stage) btnKirim.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void bintang1HandleAction(ActionEvent event){
        data.setPoint(1);

    }

    @FXML
    private void bintang2HandleAction(ActionEvent event){
        data.setPoint(2);
    }

    @FXML
    private void bintang3HandleAction(ActionEvent event){
        data.setPoint(3);
    }

    @FXML
    private void bintang4HandleAction(ActionEvent event){
        data.setPoint(4);
    }

    @FXML
    private void bintang5HandleAction(ActionEvent event){
        data.setPoint(5);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 
}
