package luanvan.luanvantotnghiep.Model;

public class MethodEquilibrium {
    private String mMethod;
    private String mContentMethod;

    public MethodEquilibrium() {
    }

    public MethodEquilibrium(String mMethod, String mContentMethod) {
        this.mMethod = mMethod;
        this.mContentMethod = mContentMethod;
    }

    public String getMethod() {
        return mMethod;
    }

    public void setMethod(String mMethod) {
        this.mMethod = mMethod;
    }

    public String getContentMethod() {
        return mContentMethod;
    }

    public void setContentMethod(String mContentMethod) {
        this.mContentMethod = mContentMethod;
    }
}
