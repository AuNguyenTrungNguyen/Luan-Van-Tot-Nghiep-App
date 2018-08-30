package luanvan.luanvantotnghiep.Model;

public class Type {
    private int mIdType;
    private String mNameType;

    public Type() {
    }

    public Type(int mIdType, String mNameType) {
        this.mIdType = mIdType;
        this.mNameType = mNameType;
    }

    public int getIdType() {
        return mIdType;
    }

    public void setIdType(int mIdType) {
        this.mIdType = mIdType;
    }

    public String getNameType() {
        return mNameType;
    }

    public void setNameType(String mNameType) {
        this.mNameType = mNameType;
    }
}
