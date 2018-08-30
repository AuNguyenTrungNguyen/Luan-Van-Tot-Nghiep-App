package luanvan.luanvantotnghiep.Model;

public class Solute {
    private int mAnion;
    private int mCation;
    private String mSolute;

    public Solute() {
    }

    public Solute(int mAnion, int mCation, String mSolute) {
        this.mAnion = mAnion;
        this.mCation = mCation;
        this.mSolute = mSolute;
    }

    public int getAnion() {
        return mAnion;
    }

    public void setAnion(int mAnion) {
        this.mAnion = mAnion;
    }

    public int getCation() {
        return mCation;
    }

    public void setCation(int mCation) {
        this.mCation = mCation;
    }

    public String getSolute() {
        return mSolute;
    }

    public void setSolute(String mSolute) {
        this.mSolute = mSolute;
    }
}
