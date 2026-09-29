package node;

import sequencia.Position;

public class NodePosition implements Position {

    private Object element;
    private NodePosition next;
    private NodePosition prev;

    public NodePosition (Object o){
        this.element = o;
        this.next = null;
        this.prev = null;
    }
    @Override
    public Object getElement() {
        return element;
    }

    public void setElement(Object element) {
        this.element = element;
    }

    public NodePosition getNext() {
        return next;
    }

    public void setNext(NodePosition next) {
        this.next = next;
    }

    public NodePosition getPrev() {
        return prev;
    }

    public void setPrev(NodePosition prev) {
        this.prev = prev;
    }
}
