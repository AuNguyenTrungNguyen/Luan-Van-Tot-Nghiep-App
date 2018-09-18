package luanvan.luanvantotnghiep.Model;

public class Compound {
    private int mIdCompound;
    private String mOtherNames;

    public Compound() {
    }

    public Compound(int mIdCompound, String mOtherNames) {
        this.mIdCompound = mIdCompound;
        this.mOtherNames = mOtherNames;
    }

    public int getIdCompound() {
        return mIdCompound;
    }

    public void setIdCompound(int mIdCompound) {
        this.mIdCompound = mIdCompound;
    }

    public String getOtherNames() {
        return mOtherNames;
    }

    public void setOtherNames(String mOtherNames) {
        this.mOtherNames = mOtherNames;
    }
}
