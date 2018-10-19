package luanvan.luanvantotnghiep.Model;

public class DescriptionOfHeading {
    private String mIdHeading;
    private String mIdDescription;

    public DescriptionOfHeading() {
    }

    public DescriptionOfHeading(String mIdHeading, String mIdDescription) {
        this.mIdHeading = mIdHeading;
        this.mIdDescription = mIdDescription;
    }

    public String getIdHeading() {
        return mIdHeading;
    }

    public void setIdHeading(String mIdHeading) {
        this.mIdHeading = mIdHeading;
    }

    public String getIdDescription() {
        return mIdDescription;
    }

    public void setIdDescription(String mIdDescription) {
        this.mIdDescription = mIdDescription;
    }
}
