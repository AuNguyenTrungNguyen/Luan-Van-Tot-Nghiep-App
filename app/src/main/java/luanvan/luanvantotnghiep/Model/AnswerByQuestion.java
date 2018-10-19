package luanvan.luanvantotnghiep.Model;

public class AnswerByQuestion {
    private String mIdQuestion;
    private String mIdAnswer;
    private int mCorrect;

    public AnswerByQuestion() {
    }

    public AnswerByQuestion(String mIdQuestion, String mIdAnswer, int mCorrect) {
        this.mIdQuestion = mIdQuestion;
        this.mIdAnswer = mIdAnswer;
        this.mCorrect = mCorrect;
    }

    public String getIdQuestion() {
        return mIdQuestion;
    }

    public void setIdQuestion(String mIdQuestion) {
        this.mIdQuestion = mIdQuestion;
    }

    public String getIdAnswer() {
        return mIdAnswer;
    }

    public void setIdAnswer(String mIdAnswer) {
        this.mIdAnswer = mIdAnswer;
    }

    public int getCorrect() {
        return mCorrect;
    }

    public void setCorrect(int mCorrect) {
        this.mCorrect = mCorrect;
    }
}
