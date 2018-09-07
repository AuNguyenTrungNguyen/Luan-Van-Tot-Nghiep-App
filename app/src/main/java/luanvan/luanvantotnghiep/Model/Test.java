package luanvan.luanvantotnghiep.Model;

public class Test {
    private int id;
    private String symbol;
    private String name;
    private int type;
    private boolean light = false;

    public boolean isLight() {
        return light;
    }

    public void setLight(boolean light) {
        this.light = light;
    }

    public Test() {
    }

    public Test(int id, String symbol, String name, int type) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
