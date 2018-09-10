package luanvan.luanvantotnghiep.Model;

public class ChooseAnswer {
    private int mNumberQuestion;
    private int mNumberChooseAnswer = -1;

    public ChooseAnswer() {
    }

    public ChooseAnswer(int mNumberQuestion, int mNumberChooseAnswer) {
        this.mNumberQuestion = mNumberQuestion;
        this.mNumberChooseAnswer = mNumberChooseAnswer;
    }

    public int getNumberQuestion() {
        return mNumberQuestion;
    }

    public void setNumberQuestion(int mNumberQuestion) {
        this.mNumberQuestion = mNumberQuestion;
    }

    public int getNumberChooseAnswer() {
        return mNumberChooseAnswer;
    }

    public void setNumberChooseAnswer(int mNumberChooseAnswer) {
        this.mNumberChooseAnswer = mNumberChooseAnswer;
    }
}
