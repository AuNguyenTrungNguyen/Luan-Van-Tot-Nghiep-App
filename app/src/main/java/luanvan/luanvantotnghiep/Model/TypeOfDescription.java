package luanvan.luanvantotnghiep.Model;

public class TypeOfDescription {
    
    private String mIdTypeOfDescription;
    private String mNameTypeOfDescription;

    public TypeOfDescription() {
    }


    public TypeOfDescription(String mIdTypeOfDescription, String mNameTypeOfDescription) {
        this.mIdTypeOfDescription = mIdTypeOfDescription;
        this.mNameTypeOfDescription = mNameTypeOfDescription;
    }

    public String getIdTypeOfDescription() {
        return mIdTypeOfDescription;
    }

    public void setIdTypeOfDescription(String mIdTypeOfDescription) {
        this.mIdTypeOfDescription = mIdTypeOfDescription;
    }

    public String getNameTypeOfDescription() {
        return mNameTypeOfDescription;
    }

    public void setNameTypeOfDescription(String mNameTypeOfDescription) {
        this.mNameTypeOfDescription = mNameTypeOfDescription;
    }
}
