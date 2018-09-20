package luanvan.luanvantotnghiep.Model;

    /*
        Phản ừng với

        IdChemistry_1 = mIdChemistry = mIdRightReaction :mã chất search
        IdChemistry_1 = mIdChemistry :mã chất sẽ tác dụng với chất search
        mIdChemicalReaction : mã puhh, khóa ngoại
    */

public class ReactWith {
    private int mIdChemistry_1;
    private int mIdChemistry_2;
    private int mIdChemicalReaction;

    public ReactWith() {
    }

    public ReactWith(int mIdChemistry_1, int mIdChemistry_2, int mIdChemicalReaction) {
        this.mIdChemistry_1 = mIdChemistry_1;
        this.mIdChemistry_2 = mIdChemistry_2;
        this.mIdChemicalReaction = mIdChemicalReaction;
    }

    public int getIdChemistry_1() {
        return mIdChemistry_1;
    }

    public void setIdChemistry_1(int mIdChemistry_1) {
        this.mIdChemistry_1 = mIdChemistry_1;
    }

    public int getIdChemistry_2() {
        return mIdChemistry_2;
    }

    public void setIdChemistry_2(int mIdChemistry_2) {
        this.mIdChemistry_2 = mIdChemistry_2;
    }

    public int getIdChemicalReaction() {
        return mIdChemicalReaction;
    }

    public void setIdChemicalReaction(int mIdChemicalReaction) {
        this.mIdChemicalReaction = mIdChemicalReaction;
    }
}
