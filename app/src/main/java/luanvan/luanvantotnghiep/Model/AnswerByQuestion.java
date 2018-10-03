package luanvan.luanvantotnghiep.Model;

public class AnswerByQuestion {
    private int mIdQuestion;
    private int mIdAnswer;
    private int mCorrect;

    public AnswerByQuestion() {
    }

    public AnswerByQuestion(int mIdQuestion, int mIdAnswer, int mCorrect) {
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

    public int getCorrect() {
        return mCorrect;
    }

    public void setCorrect(int mCorrect) {
        this.mCorrect = mCorrect;
    }
}
