package transibitorpack;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class FXMLQuizController {

    @FXML
    private MediaView mediaView;
    @FXML
    private Button buttonOptionA;
    @FXML
    private Button buttonOptionB;
    @FXML
    private Button buttonOptionC;
    @FXML
    private Button buttonOptionD;
    @FXML
    private Button buttonPrev;
    @FXML
    private Button buttonNext;
    @FXML
    private Button buttonBack;

    private QuizModel quizModel;
    private MediaPlayer mediaPlayer;
    private SwitchScene switchScene = new SwitchScene();

    public FXMLQuizController() {
        quizModel = new QuizModel();
    }

    @FXML
    private void initialize() {
        buttonPrev.getStyleClass().add("button-prev");
    buttonNext.getStyleClass().add("button-next");
        loadCurrentQuestion();

        buttonNext.setOnAction(event -> {
            if (quizModel.moveToNextItem()) {
                loadCurrentQuestion();
            }
        });

        buttonPrev.setOnAction(event -> {
            if (quizModel.moveToPreviousItem()) {
                loadCurrentQuestion();
            }
        });

        buttonOptionA.setOnAction(event -> handleOptionSelected(buttonOptionA.getText()));
        buttonOptionB.setOnAction(event -> handleOptionSelected(buttonOptionB.getText()));
        buttonOptionC.setOnAction(event -> handleOptionSelected(buttonOptionC.getText()));
        buttonOptionD.setOnAction(event -> handleOptionSelected(buttonOptionD.getText()));

        buttonBack.setOnAction(event -> {
            try {
                switchScene.Switchscene(event, "FXMLHomePage");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadCurrentQuestion() {
        QuizModel.QuizItem currentItem = quizModel.getCurrentItem();
        if (currentItem != null) {
            playVideo(currentItem.getVideoUrl());
            displayOptions(currentItem);
        }
    }

    private void playVideo(String videoUrl) {
        try {
            URL videoUrlPath = getClass().getResource("/transibitorpack/Video/" + videoUrl);
            if (videoUrlPath == null) {
                System.err.println("Video file not found: " + videoUrl);
                return;
            }
            Media media = new Media(videoUrlPath.toExternalForm());
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.setAutoPlay(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayOptions(QuizModel.QuizItem item) {
        List<String> options = item.getOptions();
        buttonOptionA.setText(options.get(0));
        buttonOptionB.setText(options.get(1));
        buttonOptionC.setText(options.get(2));
        buttonOptionD.setText(options.get(3));
    }

    private void handleOptionSelected(String selectedAnswer) {
        boolean isCorrect = quizModel.checkAnswer(selectedAnswer);
        String message = isCorrect ? "Jawaban benar!" : "Jawaban salah.";
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Hasil Jawaban");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

        // Optionally, move to the next question automatically after showing the alert
        if (isCorrect) {
            if (quizModel.moveToNextItem()) {
                loadCurrentQuestion();
            } else {
                // Handle end of quiz or any other logic
            }
        }
    }
}
