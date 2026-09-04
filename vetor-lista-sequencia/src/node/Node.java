package node;

public class Node {

    private Object element;
    private Node next;
    private Node prev;

    public Node (Object o){
        this.element = o;
        this.next = null;
        this.prev = null;
    }

}
