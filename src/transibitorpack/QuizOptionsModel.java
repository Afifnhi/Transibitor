package transibitorpack;

public class QuizOptionsModel {
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    public QuizOptionsModel(String optionA, String optionB, String optionC, String optionD) {
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

}
