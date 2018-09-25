package luanvan.luanvantotnghiep.Model;

    /*
        Phương trình phản ứng

        mIdChemicalReaction; //mã puhh
        mReactants;       // vế trái
        mProducts;        //vế phải
        mConditions;       //điều kiện
        mPhenomena;        // hiện tượng
        mTowWay;          // hai chiều <default: false>
    */

public class ChemicalReaction {
    private int mIdChemicalReaction;
    private String mReactants;
    private String mProducts;
    private String mConditions = "";
    private String mPhenomena = "";
    private int mTwoWay = 0;
    private String mReactionTypes = "";

    public ChemicalReaction() {
    }

    public ChemicalReaction(int mIdChemicalReaction,
                            String mReactants,
                            String mProducts,
                            String mConditions,
                            String mPhenomena,
                            int mTowWay,
                            String mReactionTypes) {
        this.mIdChemicalReaction = mIdChemicalReaction;
        this.mReactants = mReactants;
        this.mProducts = mProducts;
        this.mConditions = mConditions;
        this.mPhenomena = mPhenomena;
        this.mTwoWay = mTowWay;
        this.mReactionTypes = mReactionTypes;
    }

    public int getIdChemicalReaction() {
        return mIdChemicalReaction;
    }

    public void setIdChemicalReaction(int mIdChemicalReaction) {
        this.mIdChemicalReaction = mIdChemicalReaction;
    }

    public String getReactants() {
        return mReactants;
    }

    public void setReactants(String mReactants) {
        this.mReactants = mReactants;
    }

    public String getProducts() {
        return mProducts;
    }

    public void setProducts(String mProducts) {
        this.mProducts = mProducts;
    }

    public String getConditions() {
        return mConditions;
    }

    public void setConditions(String mConditions) {
        this.mConditions = mConditions;
    }

    public String getPhenomena() {
        return mPhenomena;
    }

    public void setPhenomena(String mPhenomena) {
        this.mPhenomena = mPhenomena;
    }

    public int getTwoWay() {
        return mTwoWay;
    }

    public void setTwoWay(int mTwoWay) {
        this.mTwoWay = mTwoWay;
    }

    public String getReactionTypes() {
        return mReactionTypes;
    }

    public void setReactionTypes(String mReactionTypes) {
        this.mReactionTypes = mReactionTypes;
    }
}
