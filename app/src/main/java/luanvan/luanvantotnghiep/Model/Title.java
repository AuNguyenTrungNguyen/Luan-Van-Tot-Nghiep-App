package luanvan.luanvantotnghiep.Model;

public class Title {
    private String mIdTitle;
    private String mIdHeading;
    private String mNameTitle;
    private String mSortOrder = "0";

    public Title() {
    }


    public Title(String mIdTitle, String mIdHeading, String mNameTitle, String mSortOrder) {
        this.mIdTitle = mIdTitle;
        this.mIdHeading = mIdHeading;
        this.mNameTitle = mNameTitle;
        this.mSortOrder = mSortOrder;
    }

    public String getIdTitle() {
        return mIdTitle;
    }

    public void setIdTitle(String mIdTitle) {
        this.mIdTitle = mIdTitle;
    }

    public String getNameTitle() {
        return mNameTitle;
    }

    public void setNameTitle(String mNameTitle) {
        this.mNameTitle = mNameTitle;
    }

    public String getIdHeading() {
        return mIdHeading;
    }

    public void setIdHeading(String mIdHeading) {
        this.mIdHeading = mIdHeading;
    }

    public String getSortOrder() {
        return mSortOrder;
    }

    public void setSortOrder(String mSortOrder) {
        this.mSortOrder = mSortOrder;
    }
}
