package transibitorpack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePageController implements Initializable {
    @FXML
    private TextField inputTextField;
    @FXML
    private Text inputKataText;
    @FXML
    private Button translateButton;
    @FXML
    private Text translatedText;
    @FXML
    private MediaView videoView;
    @FXML
    private AnchorPane greyBox;
    @FXML
    private Stage stage;
    @FXML
    private MenuButton userMenuButton;
    @FXML
    private MenuItem settingsMenuItem;
    @FXML
    private MenuItem logoutMenuItem;

    private ArrayList<String> inputHistory = new ArrayList<>();
    private DataTranslation dataTranslation = new DataTranslation();
    private SwitchScene switchScene = new SwitchScene();
    private MediaPlayer mediaPlayer;

    @FXML
    private void inputText() {
        String inputText = inputTextField.getText();
        if (!inputText.isEmpty()) {
            inputHistory.add(inputText);
            // if (inputHistory.size() == 5) {
            //     showPopup();
            // }
        }
    }

    @FXML
    private void translateText() {
        inputText();
        String inputText = inputTextField.getText();
        if (!inputText.isEmpty()) {
            String translated = dataTranslation.translate(inputText);
            displayTranslation(translated);
            playVideo(inputText);
        } else {
            translatedText.setVisible(false);
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            greyBox.setVisible(true);
        }
    }

    @FXML
    private void displayTranslation(String translation) {
        translatedText.setText(translation);
        translatedText.setVisible(true);
    }

    @FXML
    private void playVideo(String word) {
        String videoUrl = dataTranslation.getVideoUrl(word);
        System.out.println("Video URL: " + videoUrl); // Debugging
        if (videoUrl != null) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            Media media = new Media(videoUrl);
            mediaPlayer = new MediaPlayer(media);
            videoView.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();
            videoView.setVisible(true);
            greyBox.setVisible(true);
        } else {
            videoView.setVisible(false);
            greyBox.setVisible(true);
        }
    }

    @FXML
    private void homeButton(ActionEvent event) throws IOException{
        switchScene.Switchscene(event, "FXMLQuiz");
    }

    @FXML
    private void quizButton(ActionEvent event) throws IOException{
        switchScene.Switchscene(event, "FXMLQuiz");
    }

    @FXML
    private void logout(ActionEvent event) throws IOException{
        switchScene.Switchscene(event, "FXMLLogin");
    }

    @FXML
    private void showPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFeedback.fxml"));
            Parent root = loader.load();
            Stage popupStage = new Stage();
            popupStage.setScene(new Scene(root));
            popupStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSettings() {
        showAlert("Settings", "Settings page is under construction.");
    }

    @FXML
    private void handleLogout() {
        try {
            Parent loginRoot = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
            Stage stage = (Stage) userMenuButton.getScene().getWindow();
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Login - Transibitor");
            UserSession.logout();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load login page.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            inputKataText.setVisible(newValue.isEmpty());
        });
        translateButton.setOnAction(event -> translateText());

        userMenuButton.setText("Hello, " + UserSession.getCurrentUser().getUsername());

        // stage.setTitle("Translate - Transibitor");
        // stage.show();
        
    }

}