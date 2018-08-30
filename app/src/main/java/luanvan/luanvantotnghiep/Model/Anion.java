package luanvan.luanvantotnghiep.Model;

public class Anion {
    private int mIdAnion;
    private String mNameAnion;
    private String mValenceAnion;

    public Anion() {
    }

    public Anion(int mIdAnion, String mNameAnion, String mValenceAnion) {
        this.mIdAnion = mIdAnion;
        this.mNameAnion = mNameAnion;
        this.mValenceAnion = mValenceAnion;
    }

    public int getIdAnion() {
        return mIdAnion;
    }

    public void setIdAnion(int mIdAnion) {
        this.mIdAnion = mIdAnion;
    }

    public String getNameAnion() {
        return mNameAnion;
    }

    public void setNameAnion(String mNameAnion) {
        this.mNameAnion = mNameAnion;
    }

    public String getValenceAnion() {
        return mValenceAnion;
    }

    public void setValenceAnion(String mValenceAnion) {
        this.mValenceAnion = mValenceAnion;
    }
}