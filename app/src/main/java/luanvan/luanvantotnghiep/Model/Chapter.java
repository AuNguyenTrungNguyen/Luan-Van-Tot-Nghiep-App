package luanvan.luanvantotnghiep.Model;

public class Chapter {
    private int mIdChapter;
    private String mNameChapter;
    private int mIdBlock;
    private String mContentChapter;

    public Chapter() {
    }

    public Chapter(int mIdChapter, String mNameChapter, int mIdBlock, String mContentChapter) {
        this.mIdChapter = mIdChapter;
        this.mNameChapter = mNameChapter;
        this.mIdBlock = mIdBlock;
        this.mContentChapter = mContentChapter;
    }

    public int getIdChapter() {
        return mIdChapter;
    }

    public void setIdChapter(int mIdChapter) {
        this.mIdChapter = mIdChapter;
    }

    public String getNameChapter() {
        return mNameChapter;
    }

    public void setNameChapter(String mNameChapter) {
        this.mNameChapter = mNameChapter;
    }

    public int getIdBlock() {
        return mIdBlock;
    }

    public void setIdBlock(int mIdBlock) {
        this.mIdBlock = mIdBlock;
    }

    public String getContentChapter() {
        return mContentChapter;
    }

    public void setContentChapter(String mContentChapter) {
        this.mContentChapter = mContentChapter;
    }
}
