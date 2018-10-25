package luanvan.luanvantotnghiep.Model;

public class User {
    private String mPhone;
    private String mPassword;
    private String mName;
    private int mBlock;
    private int mType = 0;

    public User() {
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getType() {
        return mType;
    }

    public void setType(int mType) {
        this.mType = mType;
    }

    public int getBlock() {
        return mBlock;
    }

    public void setBlock(int mBlock) {
        this.mBlock = mBlock;
    }
}
