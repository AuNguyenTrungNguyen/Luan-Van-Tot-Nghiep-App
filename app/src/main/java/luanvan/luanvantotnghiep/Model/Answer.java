package luanvan.luanvantotnghiep.Model;

public class Answer {
    private int mIdAnswer;
    private String mContentAnswer;

    public Answer() {

    }

    public Answer(int mIdAnswer, String mContentAnswer) {
        this.mIdAnswer = mIdAnswer;
        this.mContentAnswer = mContentAnswer;
    }

    public int getIdAnswer() {
        return mIdAnswer;
    }

    public void setIdAnswer(int mIdAnswer) {
        this.mIdAnswer = mIdAnswer;
    }

    public String getContentAnswer() {
        return mContentAnswer;
    }

    public void setContentAnswer(String mContentAnswer) {
        this.mContentAnswer = mContentAnswer;
    }
}
