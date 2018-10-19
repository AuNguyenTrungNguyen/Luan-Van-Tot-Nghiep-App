package luanvan.luanvantotnghiep.Model;

public class DescriptionOfTitle {
    private String mIdTitle;
    private String mIdDescription;

    public DescriptionOfTitle() {
    }

    public DescriptionOfTitle(String mIdTitle, String mIdDescription) {
        this.mIdTitle = mIdTitle;
        this.mIdDescription = mIdDescription;
    }

    public String getIdTitle() {
        return mIdTitle;
    }

    public void setIdTitle(String mIdTitle) {
        this.mIdTitle = mIdTitle;
    }

    public String getIdDescription() {
        return mIdDescription;
    }

    public void setIdDescription(String mIdDescription) {
        this.mIdDescription = mIdDescription;
    }
}
