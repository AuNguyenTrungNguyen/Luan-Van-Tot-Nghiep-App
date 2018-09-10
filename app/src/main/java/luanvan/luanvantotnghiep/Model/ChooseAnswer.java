package luanvan.luanvantotnghiep.Model;

public class ChooseAnswer {
    private int mNumberQuestion;
    private int mNumberChooseAnswer = -1; //UI
    private int mIdChooseAnswer = 0; //Handle

    public int getIdChooseAnswer() {
        return mIdChooseAnswer;
    }

    public void setIdChooseAnswer(int mIdChooseAnswer) {
        this.mIdChooseAnswer = mIdChooseAnswer;
    }

    public ChooseAnswer() {
    }

    public ChooseAnswer(int mNumberQuestion) {
        this.mNumberQuestion = mNumberQuestion;
    }

//    public ChooseAnswer(int mNumberQuestion, int mNumberChooseAnswer) {
//        this.mNumberQuestion = mNumberQuestion;
//        this.mNumberChooseAnswer = mNumberChooseAnswer;
//    }

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
