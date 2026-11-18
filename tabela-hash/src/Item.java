public class Item {

    private int key;
    private Object element;

    public Item(int k, Object e){
        this.key = k;
        this.element = e;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Object getElement() {
        return element;
    }

    public void setElement(Object element) {
        this.element = element;
    }

}
