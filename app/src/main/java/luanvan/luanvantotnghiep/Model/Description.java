package luanvan.luanvantotnghiep.Model;

public class Description {
    private String mIdDescription;
    private String mIdTypeOfDescription;
    private String mNameDescription;
    private String mSortOrder = "0";


    public Description() {
    }

    public Description(String mIdDescription, String mIdTypeOfDescription, String mNameDescription, String mSortOrder) {
        this.mIdDescription = mIdDescription;
        this.mIdTypeOfDescription = mIdTypeOfDescription;
        this.mNameDescription = mNameDescription;
        this.mSortOrder = mSortOrder;
    }

    public String getIdDescription() {
        return mIdDescription;
    }

    public void setIdDescription(String mIdDescription) {
        this.mIdDescription = mIdDescription;
    }

    public String getNameDescription() {
        return mNameDescription;
    }

    public void setNameDescription(String mNameDescription) {
        this.mNameDescription = mNameDescription;
    }

    public String getIdTypeOfDescription() {
        return mIdTypeOfDescription;
    }

    public void setIdTypeOfDescription(String mIdTypeOfDescription) {
        this.mIdTypeOfDescription = mIdTypeOfDescription;
    }

    public String getSortOrder() {
        return mSortOrder;
    }

    public void setSortOrder(String mSortOrder) {
        this.mSortOrder = mSortOrder;
    }
}
