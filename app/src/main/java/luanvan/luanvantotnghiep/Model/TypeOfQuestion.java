package luanvan.luanvantotnghiep.Model;

public class TypeOfQuestion {
    private int mIdType;
    private String mNameType;
    private String mDescription;

    public TypeOfQuestion() {
    }

    public TypeOfQuestion(int mIdType, String mNameType, String mDescription) {
        this.mIdType = mIdType;
        this.mNameType = mNameType;
        this.mDescription = mDescription;
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

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
