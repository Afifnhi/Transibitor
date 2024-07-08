package transibitorpack;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.net.URL;
import java.util.*;

public class QuizModel {
    private List<QuizItem> quizItems;
    private int currentItemIndex;

    public QuizModel() {
        quizItems = new ArrayList<>();
        currentItemIndex = 0;
        loadQuestionsFromXml();
    }

    private void loadQuestionsFromXml() {
        try {
            String xmlFilePath = "/Transibitor/quiz_data.xml";
            URL xmlUrl = getClass().getResource(xmlFilePath);
            if (xmlUrl == null) {
                System.err.println("XML file not found: " + xmlFilePath);
                return;
            }
            System.out.println("XML file path: " + xmlUrl.getPath());
    
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlUrl.openStream());
            System.out.println("Loaded XML file.");
    
            NodeList itemList = doc.getElementsByTagName("item");
            for (int i = 0; i < itemList.getLength(); i++) {
                Node itemNode = itemList.item(i);
                if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element itemElement = (Element) itemNode;
                    String videoUrl = itemElement.getElementsByTagName("video").item(0).getTextContent();
                    String correctAnswer = itemElement.getElementsByTagName("correct_answer").item(0).getTextContent();
                    System.out.println("Loaded item: videoUrl = " + videoUrl + ", correctAnswer = " + correctAnswer);
    
                    List<String> options = new ArrayList<>();
                    NodeList optionNodes = itemElement.getElementsByTagName("option");
                    for (int j = 0; j < optionNodes.getLength(); j++) {
                        options.add(optionNodes.item(j).getTextContent());
                        System.out.println("Option " + j + ": " + optionNodes.item(j).getTextContent());
                    }
    
                    quizItems.add(new QuizItem(videoUrl, correctAnswer, options));
                }
            }
            Collections.shuffle(quizItems);
            System.out.println("Shuffled quiz items.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public QuizItem getCurrentItem() {
        if (quizItems.isEmpty()) {
            System.out.println("No quiz items loaded.");
            return null;
        }
        System.out.println("Current item index: " + currentItemIndex);
        return quizItems.get(currentItemIndex);
    }

    public boolean moveToNextItem() {
        if (currentItemIndex < quizItems.size() - 1) {
            currentItemIndex++;
            System.out.println("Moved to next item. Current item index: " + currentItemIndex);
            return true;
        }
        System.out.println("Already at the last item.");
        return false;
    }

    public boolean moveToPreviousItem() {
        if (currentItemIndex > 0) {
            currentItemIndex--;
            System.out.println("Moved to previous item. Current item index: " + currentItemIndex);
            return true;
        }
        System.out.println("Already at the first item.");
        return false;
    }

    public boolean checkAnswer(String selectedAnswer) {
        boolean isCorrect = getCurrentItem().getCorrectAnswer().equalsIgnoreCase(selectedAnswer);
        System.out.println("Checked answer. Selected: " + selectedAnswer + ", Correct: " + isCorrect);
        return isCorrect;
    }

    public String getCurrentVideoUrl() {
        String videoUrl = getCurrentItem().getVideoUrl();
        System.out.println("Current video URL: " + videoUrl);
        return videoUrl;
    }

    public List<String> getCurrentOptions() {
        List<String> options = getCurrentItem().getOptions();
        System.out.println("Current options: " + options);
        return options;
    }

    public static class QuizItem {
        private String videoUrl;
        private String correctAnswer;
        private List<String> options;

        public QuizItem(String videoUrl, String correctAnswer, List<String> options) {
            this.videoUrl = videoUrl;
            this.correctAnswer = correctAnswer;
            this.options = options;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }

        public List<String> getOptions() {
            return options;
        }
    }
}
