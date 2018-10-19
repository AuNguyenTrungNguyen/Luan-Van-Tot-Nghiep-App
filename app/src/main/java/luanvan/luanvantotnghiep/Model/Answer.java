package luanvan.luanvantotnghiep.Model;

public class Answer {
    private String mIdAnswer;
    private String mContentAnswer;
    private int mShow = -1;

    public int getShow() {
        return mShow;
    }

    public void setShow(int mShow) {
        this.mShow = mShow;
    }

    public Answer() {

    }

    public Answer(String mIdAnswer, String mContentAnswer) {
        this.mIdAnswer = mIdAnswer;
        this.mContentAnswer = mContentAnswer;
    }

    public String getIdAnswer() {
        return mIdAnswer;
    }

    public void setIdAnswer(String mIdAnswer) {
        this.mIdAnswer = mIdAnswer;
    }

    public String getContentAnswer() {
        return mContentAnswer;
    }

    public void setContentAnswer(String mContentAnswer) {
        this.mContentAnswer = mContentAnswer;
    }
}
