package luanvan.luanvantotnghiep.Model;

import java.io.Serializable;

public class Chemistry implements Serializable {
    private int mIdChemistry;
    private int mIdType;
    private String mSymbolChemistry;
    private String mNameChemistry;
    private String mStatusChemistry;
    private String mColorChemistry;
    private double mWeightChemistry;

    public Chemistry() {
    }

    public Chemistry(String mSymbolChemistry, String mNameChemistry) {
        this.mSymbolChemistry = mSymbolChemistry;
        this.mNameChemistry = mNameChemistry;
    }

    public Chemistry(int mIdChemistry,
                     int mIdType,
                     String mSymbolChemistry,
                     String mNameChemistry,
                     String mStatusChemistry,
                     String mColorChemistry,
                     double mWeightChemistry) {
        this.mIdChemistry = mIdChemistry;
        this.mIdType = mIdType;
        this.mSymbolChemistry = mSymbolChemistry;
        this.mNameChemistry = mNameChemistry;
        this.mStatusChemistry = mStatusChemistry;
        this.mColorChemistry = mColorChemistry;
        this.mWeightChemistry = mWeightChemistry;
    }

    public int getIdChemistry() {
        return mIdChemistry;
    }

    public void setIdChemistry(int mIdChemistry) {
        this.mIdChemistry = mIdChemistry;
    }

    public int getIdType() {
        return mIdType;
    }

    public void setIdType(int mIdType) {
        this.mIdType = mIdType;
    }

    public String getSymbolChemistry() {
        return mSymbolChemistry;
    }

    public void setSymbolChemistry(String mSymbolChemistry) {
        this.mSymbolChemistry = mSymbolChemistry;
    }

    public String getNameChemistry() {
        return mNameChemistry;
    }

    public void setNameChemistry(String mNameChemistry) {
        this.mNameChemistry = mNameChemistry;
    }

    public String getStatusChemistry() {
        return mStatusChemistry;
    }

    public void setStatusChemistry(String mStatusChemistry) {
        this.mStatusChemistry = mStatusChemistry;
    }

    public String getColorChemistry() {
        return mColorChemistry;
    }

    public void setColorChemistry(String mColorChemistry) {
        this.mColorChemistry = mColorChemistry;
    }

    public double getWeightChemistry() {
        return mWeightChemistry;
    }

    public void setWeightChemistry(double mWeightChemistry) {
        this.mWeightChemistry = mWeightChemistry;
    }

}
