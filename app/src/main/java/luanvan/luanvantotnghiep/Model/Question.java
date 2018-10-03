package luanvan.luanvantotnghiep.Model;



public class Question{
    private int mIdQuestion;
    private String mContentQuestion; //Nội dung
    private int mIdLevel; //từng chặng ( 1-2-3-4 giống chương nhưng có thể mở rộng ra)

    private int mIdBlock; //Khối - lớp
    private int mIdType; //Loại câu hỏi

    private int mExtent; //Mức độ dễ - trung bình - khó **mặt định là 0

    private int mAnswer = -1;// Save answer user select (User selected)
    private int mIdCorrect = -1; //review (Check user true or false)

    public Question() {
    }

//    public Question(int mIdQuestion, int mIdLevel, int mIdBlock, int mIdType, String mContentQuestion, int mExtent) {
//        this.mIdQuestion = mIdQuestion;
//        this.mIdLevel = mIdLevel;
//        this.mIdBlock = mIdBlock;
//        this.mIdType = mIdType;
//        this.mContentQuestion = mContentQuestion;
//        this.mExtent = mExtent;
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

    public int getIdLevel() {
        return mIdLevel;
    }

    public void setIdLevel(int mIdLevel) {
        this.mIdLevel = mIdLevel;
    }

    public int getIdBlock() {
        return mIdBlock;
    }

    public void setIdBlock(int mIdBlock) {
        this.mIdBlock = mIdBlock;
    }

    public int getIdType() {
        return mIdType;
    }

    public void setIdType(int mIdType) {
        this.mIdType = mIdType;
    }

    public String getContentQuestion() {
        return mContentQuestion;
    }

    public void setContentQuestion(String mContentQuestion) {
        this.mContentQuestion = mContentQuestion;
    }

    public int getExtent() {
        return mExtent;
    }

    public void setExtent(int mExtent) {
        this.mExtent = mExtent;
    }

    public int getAnswer() {
        return mAnswer;
    }

    public void setAnswer(int mAnswer) {
        this.mAnswer = mAnswer;
    }

    public int getIdCorrect() {
        return mIdCorrect;
    }

    public void setIdCorrect(int mIdCorrect) {
        this.mIdCorrect = mIdCorrect;
    }
}
