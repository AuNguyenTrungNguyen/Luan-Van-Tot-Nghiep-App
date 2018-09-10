package luanvan.luanvantotnghiep.Model;

public class TestElement {
    private boolean isShow = true;
    private Element element;

    public TestElement() {
    }

    public TestElement(Element element) {
        this.element = element;
    }

    public TestElement(boolean isShow, Element element) {
        this.isShow = isShow;
        this.element = element;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }
}
