package luanvan.luanvantotnghiep.Model;

public class ReactSeries {
    private int mIdReactSeries;
    private String mIon;
    private String mValence;

    public ReactSeries() {
    }

    public ReactSeries(int mIdReactSeries, String mIon, String mValence) {
        this.mIdReactSeries = mIdReactSeries;
        this.mIon = mIon;
        this.mValence = mValence;
    }

    public int getIdReactSeries() {
        return mIdReactSeries;
    }

    public void setIdReactSeries(int mIdReactSeries) {
        this.mIdReactSeries = mIdReactSeries;
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
