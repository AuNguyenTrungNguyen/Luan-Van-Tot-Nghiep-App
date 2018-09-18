package luanvan.luanvantotnghiep.Model;

public class ProducedBy {
    private int mIdRightReaction; //mã chất 1
    private int mIdLeftReaction; //mã chất 2
    
    public ProducedBy() {
    }

    public ProducedBy(int mIdRightReaction, int mIdLeftReaction) {
        this.mIdRightReaction = mIdRightReaction;
        this.mIdLeftReaction = mIdLeftReaction;
    }

    public int getIdRightReaction() {
        return mIdRightReaction;
    }

    public void setIdRightReaction(int mIdRightReaction) {
        this.mIdRightReaction = mIdRightReaction;
    }

    public int getIdLeftReaction() {
        return mIdLeftReaction;
    }

    public void setIdLeftReaction(int mIdLeftReaction) {
        this.mIdLeftReaction = mIdLeftReaction;
    }
}
