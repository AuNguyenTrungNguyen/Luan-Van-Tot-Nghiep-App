package luanvan.luanvantotnghiep.Model;

import java.io.Serializable;

public class Element implements Serializable {
    private int mIdElement;
    private int mIdGroup;
    private String mMolecularFormula; //công thức phân tử h2
    private int mPeriod; //chu kỳ
    private String mClassElement; //phân lớp
    private int mNeutron; //nơ tron
    private String mSimplifiedConfiguration; //rút gọn
    private String mConfiguration; //e
    private String mShell; //vỏ
    private double mElectronegativity; //độ âm điện
    private String mValence; //hóa trị
    private String mEnglishName;
    private double mMeltingPoint;
    private double mBoilingPoint;
    private String mDiscoverer;
    private String mYearDiscovery;
    private String mIsotopes; //đồng vị
    private String mPicture;

    public int getIdElement() {
        return mIdElement;
    }

    public void setIdElement(int mIdElement) {
        this.mIdElement = mIdElement;
    }

    public String getMolecularFormula() {
        return mMolecularFormula;
    }

    public void setMolecularFormula(String mMolecularFormula) {
        this.mMolecularFormula = mMolecularFormula;
    }

    public int getPeriod() {
        return mPeriod;
    }

    public void setPeriod(int mPeriod) {
        this.mPeriod = mPeriod;
    }

    public String getClassElement() {
        return mClassElement;
    }

    public void setClassElement(String mClassElement) {
        this.mClassElement = mClassElement;
    }

    public int getNeutron() {
        return mNeutron;
    }

    public void setNeutron(int mNeutron) {
        this.mNeutron = mNeutron;
    }

    public String getSimplifiedConfiguration() {
        return mSimplifiedConfiguration;
    }

    public void setSimplifiedConfiguration(String mSimplifiedConfiguration) {
        this.mSimplifiedConfiguration = mSimplifiedConfiguration;
    }

    public String getConfiguration() {
        return mConfiguration;
    }

    public void setConfiguration(String mConfiguration) {
        this.mConfiguration = mConfiguration;
    }

    public String getShell() {
        return mShell;
    }

    public void setShell(String mShell) {
        this.mShell = mShell;
    }

    public double getElectronegativity() {
        return mElectronegativity;
    }

    public void setElectronegativity(double mElectronegativity) {
        this.mElectronegativity = mElectronegativity;
    }

    public String getValence() {
        return mValence;
    }

    public void setValence(String mValence) {
        this.mValence = mValence;
    }

    public String getEnglishName() {
        return mEnglishName;
    }

    public void setEnglishName(String mEnglishName) {
        this.mEnglishName = mEnglishName;
    }

    public double getMeltingPoint() {
        return mMeltingPoint;
    }

    public void setMeltingPoint(double mMeltingPoint) {
        this.mMeltingPoint = mMeltingPoint;
    }

    public double getBoilingPoint() {
        return mBoilingPoint;
    }

    public void setBoilingPoint(double mBoilingPoint) {
        this.mBoilingPoint = mBoilingPoint;
    }

    public String getDiscoverer() {
        return mDiscoverer;
    }

    public void setDiscoverer(String mDiscoverer) {
        this.mDiscoverer = mDiscoverer;
    }

    public String getYearDiscovery() {
        return mYearDiscovery;
    }

    public void setYearDiscovery(String mYearDiscovery) {
        this.mYearDiscovery = mYearDiscovery;
    }

    public String getIsotopes() {
        return mIsotopes;
    }

    public void setIsotopes(String mIsotopes) {
        this.mIsotopes = mIsotopes;
    }

    public int getIdGroup() {
        return mIdGroup;
    }

    public void setIdGroup(int mIdGroup) {
        this.mIdGroup = mIdGroup;
    }

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String mPicture) {
        this.mPicture = mPicture;
    }

}
