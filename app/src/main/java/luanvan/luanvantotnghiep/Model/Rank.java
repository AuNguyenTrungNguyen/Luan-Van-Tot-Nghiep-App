package luanvan.luanvantotnghiep.Model;

public class Rank {
    private String mName;
    private float mScore;
    private int mBlock;
    private int mExtent;

    public Rank() {
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public float getScore() {
        return mScore;
    }

    public void setScore(float mScore) {
        this.mScore = mScore;
    }

    public int getBlock() {
        return mBlock;
    }

    public void setBlock(int mBlock) {
        this.mBlock = mBlock;
    }

    public int getExtent() {
        return mExtent;
    }

    public void setExtent(int mExtent) {
        this.mExtent = mExtent;
    }
}
