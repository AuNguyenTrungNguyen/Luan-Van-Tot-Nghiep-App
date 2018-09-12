package luanvan.luanvantotnghiep.Model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import luanvan.luanvantotnghiep.BR;

public class Question extends BaseObservable{
    private int mIdQuestion;
    private int mIdChapter; //Chương
    private int mIdBlock; //Khối
    private int mIdKind; //Loại
    private String mContentQuestion; //Nội dung
    private String mLevel; //Mức độ
    private int mAnswer = -1;
    private boolean isCheckQuestion;


    public int getmIdQuestion() {
        return mIdQuestion;
    }

    public void setmIdQuestion(int mIdQuestion) {
        this.mIdQuestion = mIdQuestion;
    }

    public int getmIdChapter() {
        return mIdChapter;
    }

    public void setmIdChapter(int mIdChapter) {
        this.mIdChapter = mIdChapter;
    }

    public int getmIdBlock() {
        return mIdBlock;
    }

    public void setmIdBlock(int mIdBlock) {
        this.mIdBlock = mIdBlock;
    }

    public int getmIdKind() {
        return mIdKind;
    }

    public void setmIdKind(int mIdKind) {
        this.mIdKind = mIdKind;
    }

    public String getmContentQuestion() {
        return mContentQuestion;
    }

    public void setmContentQuestion(String mContentQuestion) {
        this.mContentQuestion = mContentQuestion;
    }

    public String getmLevel() {
        return mLevel;
    }

    public void setmLevel(String mLevel) {
        this.mLevel = mLevel;
    }

    public int getmAnswer() {
        return mAnswer;
    }

    public void setmAnswer(int mAnswer) {
        this.mAnswer = mAnswer;
    }
    @Bindable
    public boolean isCheckQuestion() {
        return isCheckQuestion;
    }

    public void setCheckQuestion(boolean checkQuestion) {
        isCheckQuestion = checkQuestion;
        notifyPropertyChanged(BR.checkQuestion);
    }

    public Question() {
    }

//    public Question(int mIdQuestion, int mIdChapter, int mIdBlock, int mIdKind, String mContentQuestion, String mLevel) {
//        this.mIdQuestion = mIdQuestion;
//        this.mIdChapter = mIdChapter;
//        this.mIdBlock = mIdBlock;
//        this.mIdKind = mIdKind;
//        this.mContentQuestion = mContentQuestion;
//        this.mLevel = mLevel;
//    }

    public Question(int mIdQuestion, String mContentQuestion) {
        this.mIdQuestion = mIdQuestion;
        this.mContentQuestion = mContentQuestion;
    }

    public int getIdQuestion() {
        return mIdQuestion;
    }

    public void setIdQuestion(int mIdQuestion) {
        this.mIdQuestion = mIdQuestion;
    }

    public int getIdChapter() {
        return mIdChapter;
    }

    public void setIdChapter(int mIdChapter) {
        this.mIdChapter = mIdChapter;
    }

    public int getIdBlock() {
        return mIdBlock;
    }

    public void setIdBlock(int mIdBlock) {
        this.mIdBlock = mIdBlock;
    }

    public int getIdKind() {
        return mIdKind;
    }

    public void setIdKind(int mIdKind) {
        this.mIdKind = mIdKind;
    }

    public String getContentQuestion() {
        return mContentQuestion;
    }

    public void setContentQuestion(String mContentQuestion) {
        this.mContentQuestion = mContentQuestion;
    }

    public String getLevel() {
        return mLevel;
    }

    public void setLevel(String mLevel) {
        this.mLevel = mLevel;
    }
    @Bindable
    public int getAnswer() {
        return mAnswer;
    }

    public void setAnswer(int mAnswer) {
        this.mAnswer = mAnswer;
        notifyPropertyChanged( BR.answer);
    }
}
