package luanvan.luanvantotnghiep.CustomTree;

public class Tree {
    private boolean isLeft;
    private String text;
    private int level = 0;

    public Tree() {
    }

    public Tree(boolean isLeft, String text, int level) {
        this.isLeft = isLeft;
        this.text = text;
        this.level = level;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public void setLeft(boolean left) {
        isLeft = left;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
