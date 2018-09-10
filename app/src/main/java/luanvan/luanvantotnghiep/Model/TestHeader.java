package luanvan.luanvantotnghiep.Model;

public class TestHeader {
    private String name;
    private boolean isRelative = false;

    public TestHeader() {
    }

    public TestHeader(String name, boolean isRelative) {
        this.name = name;
        this.isRelative = isRelative;
    }

    public TestHeader(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRelative() {
        return isRelative;
    }

    public void setRelative(boolean relative) {
        isRelative = relative;
    }
}
