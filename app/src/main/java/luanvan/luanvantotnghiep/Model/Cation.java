package luanvan.luanvantotnghiep.Model;

public class Cation {
    private int mIdCation;
    private String mNameCation;
    private String mValenceCation;

    public Cation() {
    }

    public Cation(int mIdCation, String mNameCation, String mValenceCation) {
        this.mIdCation = mIdCation;
        this.mNameCation = mNameCation;
        this.mValenceCation = mValenceCation;
    }

    public int getIdCation() {
        return mIdCation;
    }

    public void setIdCation(int mIdCation) {
        this.mIdCation = mIdCation;
    }

    public String getNameCation() {
        return mNameCation;
    }

    public void setNameCation(String mNameCation) {
        this.mNameCation = mNameCation;
    }

    public String getValenceCation() {
        return mValenceCation;
    }

    public void setValenceCation(String mValenceCation) {
        this.mValenceCation = mValenceCation;
    }
}
