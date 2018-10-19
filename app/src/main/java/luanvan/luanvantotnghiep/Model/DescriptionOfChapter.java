package luanvan.luanvantotnghiep.Model;

public class DescriptionOfChapter {
    private String mIdChapter;
    private String mIdDescription;

    public DescriptionOfChapter() {
    }

    public DescriptionOfChapter(String mIdChapter, String mIdDescription) {
        this.mIdChapter = mIdChapter;
        this.mIdDescription = mIdDescription;
    }

    public String getIdChapter() {
        return mIdChapter;
    }

    public void setIdChapter(String mIdChapter) {
        this.mIdChapter = mIdChapter;
    }

    public String getIdDescription() {
        return mIdDescription;
    }

    public void setIdDescription(String mIdDescription) {
        this.mIdDescription = mIdDescription;
    }
}
