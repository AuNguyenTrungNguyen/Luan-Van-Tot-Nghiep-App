package luanvan.luanvantotnghiep.Model;

public class Group {
    private int mIdGroup;
    private String mNameGroup;

    public Group() {
    }

    public Group(int mIdGroup, String mNameGroup) {
        this.mIdGroup = mIdGroup;
        this.mNameGroup = mNameGroup;
    }

    public int getIdGroup() {
        return mIdGroup;
    }

    public void setIdGroup(int mIdGroup) {
        this.mIdGroup = mIdGroup;
    }

    public String getNameGroup() {
        return mNameGroup;
    }

    public void setNameGroup(String mNameGroup) {
        this.mNameGroup = mNameGroup;
    }
}
