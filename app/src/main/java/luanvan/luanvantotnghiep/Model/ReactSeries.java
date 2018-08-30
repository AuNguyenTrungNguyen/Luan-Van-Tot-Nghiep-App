package luanvan.luanvantotnghiep.Model;

public class ReactSeries {
    private String mIon;
    private String mValence;

    public ReactSeries() {
    }

    public ReactSeries(String mIon, String mValence) {
        this.mIon = mIon;
        this.mValence = mValence;
    }

    public String getIon() {
        return mIon;
    }

    public void setIon(String mIon) {
        this.mIon = mIon;
    }

    public String getValence() {
        return mValence;
    }

    public void setValence(String mValence) {
        this.mValence = mValence;
    }
}
