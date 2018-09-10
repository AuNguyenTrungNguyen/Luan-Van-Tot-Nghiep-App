package luanvan.luanvantotnghiep.Model;

public class AnswerByQuestion {
    private int mIdQuestion;
    private int mIdAnswer;
    private boolean mCorrect;

    public AnswerByQuestion() {
    }

    public AnswerByQuestion(int mIdQuestion, int mIdAnswer, boolean mCorrect) {
        this.mIdQuestion = mIdQuestion;
        this.mIdAnswer = mIdAnswer;
        this.mCorrect = mCorrect;
    }

    public int getIdQuestion() {
        return mIdQuestion;
    }

    public void setIdQuestion(int mIdQuestion) {
        this.mIdQuestion = mIdQuestion;
    }

    public int getIdAnswer() {
        return mIdAnswer;
    }

    public void setIdAnswer(int mIdAnswer) {
        this.mIdAnswer = mIdAnswer;
    }

    public boolean isCorrect() {
        return mCorrect;
    }

    public void setCorrect(boolean mCorrect) {
        this.mCorrect = mCorrect;
    }
}
