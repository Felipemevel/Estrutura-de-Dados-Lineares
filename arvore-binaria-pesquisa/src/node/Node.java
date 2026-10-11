package node;

public class Node {

    private Object element;
    private Node father;
    private Node leftChild;
    private Node rightChild;

    public Node(){
        this.element = null;
        this.father = null;
        this.leftChild = null;
        this.rightChild = null;
    }

    public Node(Object o){
        this.element = o;
        this.father = null;
        this.leftChild = null;
        this.rightChild = null;
    }

    public boolean hasLeftChild(){
        return this.leftChild != null;
    }
    public boolean hasRightChild(){
        return this.rightChild != null;
    }
    public boolean isLeaf(){
        return !hasLeftChild() && !hasRightChild();
    }

    public Object getElement() {
        return element;
    }

    public void setElement(Object element) {
        this.element = element;
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

    public Node getFather() {
        return father;
    }

    public void setFather(Node father) {
        this.father = father;
    }
}
