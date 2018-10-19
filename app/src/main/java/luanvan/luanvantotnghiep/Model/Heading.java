package luanvan.luanvantotnghiep.Model;

public class Heading {
    private String mIdHeading;
    private String mIdChapter;
    private String mNameHeading;
    private String mSortOrder = "0";

    public Heading() {
    }

    public Heading(String mIdHeading, String mIdChapter, String mNameHeading, String mSortOrder) {
        this.mIdHeading = mIdHeading;
        this.mIdChapter = mIdChapter;
        this.mNameHeading = mNameHeading;
        this.mSortOrder = mSortOrder;
    }

    public String getIdHeading() {
        return mIdHeading;
    }

    public void setIdHeading(String mIdHeading) {
        this.mIdHeading = mIdHeading;
    }

    public String getNameHeading() {
        return mNameHeading;
    }

    public void setNameHeading(String mNameHeading) {
        this.mNameHeading = mNameHeading;
    }

    public String getIdChapter() {
        return mIdChapter;
    }

    public void setIdChapter(String mIdChapter) {
        this.mIdChapter = mIdChapter;
    }

    public String getSortOrder() {
        return mSortOrder;
    }

    public void setSortOrder(String mSortOrder) {
        this.mSortOrder = mSortOrder;
    }
}
