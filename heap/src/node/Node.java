package node;

public class Node {

    private Node father;
    private Node rightChild;
    private Node leftChild;
    private Object element;
    private int key;

    public Node(int k, Object e){
        this.key = k;
        this.element = e;
    }

    public boolean hasChildes(){
        return this.leftChild != null && this.rightChild != null;
    }
    public boolean hasLeftChild(){
        return this.leftChild != null;
    }
    public boolean hasRightChild(){
        return this.rightChild != null;
    }

    public Node getFather() {
        return father;
    }

    public void setFather(Node father) {
        this.father = father;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
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
