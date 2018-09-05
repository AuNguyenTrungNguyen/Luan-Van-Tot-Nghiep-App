package luanvan.luanvantotnghiep.Model;

public class ClassObject {
    private String mTitle;
    private String mFileName;

    public ClassObject() {
    }

    public ClassObject(String mTitle, String mFileName) {
        this.mTitle = mTitle;
        this.mFileName = mFileName;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setFileName(String mFileName) {
        this.mFileName = mFileName;
    }
}
