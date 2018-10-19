package luanvan.luanvantotnghiep.Model;

import java.io.Serializable;

public class Chapter implements Serializable {
    private String mIdChapter;
    private String mNameChapter;
    private int mIdBlock;
    private int mConfirm; //0 - NOT; 1 - OKE

    public Chapter() {
    }

    public Chapter(String mIdChapter, String mNameChapter, int mIdBlock, int mConfirm) {
        this.mIdChapter = mIdChapter;
        this.mNameChapter = mNameChapter;
        this.mIdBlock = mIdBlock;
        this.mConfirm = mConfirm;
    }

    public String getIdChapter() {
        return mIdChapter;
    }

    public void setIdChapter(String mIdChapter) {
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

    public int getConfirm() {
        return mConfirm;
    }

    public void setConfirm(int mConfirm) {
        this.mConfirm = mConfirm;
    }
}
