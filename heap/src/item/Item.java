package item;

public class Item {

    private Object element;
    private int key;

    public Item(int k, Object e){
        this.element = e;
        this.key = k;
    }

    public Object getElement() {
        return element;
    }

    public void setElement(Object element) {
        this.element = element;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
