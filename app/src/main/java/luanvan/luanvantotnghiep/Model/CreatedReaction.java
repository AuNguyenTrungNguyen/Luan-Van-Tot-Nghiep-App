package luanvan.luanvantotnghiep.Model;

    /*
        Được tạo thành

        mIdCreatedRight= mIdChemistry = mIdRightReaction : mã chất search
        mIdChemicalReaction : mã puhh, khóa ngoại
    */

public class CreatedReaction {
    private int mIdCreatedRight;
    private int mIdChemicalReaction;

    public CreatedReaction() {
    }

    public CreatedReaction(int mIdCreatedRight, int mIdChemicalReaction) {
        this.mIdCreatedRight = mIdCreatedRight;
        this.mIdChemicalReaction = mIdChemicalReaction;
    }

    public int getIdCreatedRight() {
        return mIdCreatedRight;
    }

    public void setIdCreatedRight(int mIdCreatedRight) {
        this.mIdCreatedRight = mIdCreatedRight;
    }

    public int getIdChemicalReaction() {
        return mIdChemicalReaction;
    }

    public void setIdChemicalReaction(int mIdChemicalReaction) {
        this.mIdChemicalReaction = mIdChemicalReaction;
    }
}
