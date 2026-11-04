package implementacao;

import arvoreHeapInterface.ArvoreHeapInterface;
import node.Node;

public class ArvoreHeapNode implements ArvoreHeapInterface {

    private Node root;
    private Node lastInserction;
    private int size;

    private void upHeap(Node n){
        if (n == this.root) {
            return;
        }

        if (n.getFather().getKey() > n.getKey()){
            Object tempElement = n.getElement();
            int tempKey = n.getKey();

            n.setKey(n.getFather().getKey());
            n.setElement(n.getFather().getElement());

            n.getFather().setKey(tempKey);
            n.getFather().setElement(tempElement);

            upHeap(n.getFather());
        }
    }
    private void downHeap(Node n){
        if (!(n.hasLeftChild()) && !(n.hasRightChild())){
            return;
        }

        Node smallerChild;

        if (n.hasLeftChild() && n.hasRightChild()){

            if (n.getLeftChild().getKey() < n.getRightChild().getKey()){
                smallerChild = n.getLeftChild();
            } else {
                smallerChild = n.getRightChild();
            }
        } else {
            smallerChild = n.getLeftChild();
        }

        if (smallerChild.getKey() < n.getKey()){
            int tempKey = n.getKey();
            Object tempElement = n.getElement();

            n.setKey(smallerChild.getKey());
            n.setElement(smallerChild.getElement());

            smallerChild.setKey(tempKey);
            smallerChild.setElement(tempElement);

            downHeap(smallerChild);
        }
    }
    private Node previousNode(Node n){
        if (n == this.root){
            return null;
        }

        Node current = n;
        if (current == current.getFather().getRightChild()){
            current = current.getFather().getLeftChild();

            while (current.hasRightChild()){
                current = current.getRightChild();
            }
            return current;
        }
        current = current.getFather();

        while (current != this.root &&
                current == current.getFather().getLeftChild()){
            current = current.getFather();
        }

        if (current != this.root){
            current = current.getFather().getLeftChild();

            while (current.hasRightChild()){
                current = current.getRightChild();
            }
        }

        return current;
    }

    public ArvoreHeapNode(){
        this.root = null;
        this.lastInserction = null;
        this.size = 0;
    }
    public ArvoreHeapNode(int k, Object e){
        this.root = new Node(k, e);
        this.lastInserction = this.root;
        this.size = 1;
    }
    @Override
    public void insert(int k, Object o){
        if (this.root == null){
            this.root = new Node(k, o);
            this.lastInserction = this.root;

            this.size++;
            return;
        }

        Node newNode = new Node(k, o);
        Node current = this.lastInserction;

        while (true){
            if (current == this.root){
                break;
            }
            if (current == current.getFather().getRightChild()){
                current = current.getFather();
            } else if (current == current.getFather().getLeftChild()){
                if (current.getFather().getRightChild() == null){

                    current.getFather().setRightChild(newNode);
                    this.lastInserction = current.getFather().getRightChild();
                    this.lastInserction.setFather(current.getFather());

                    this.size++;

                    upHeap(this.lastInserction);
                    return;
                }
                current = current.getFather().getRightChild();
                break;
            }
        }

        while (current.hasLeftChild()){
            current = current.getLeftChild();
        }

        current.setLeftChild(newNode);
        this.lastInserction = current.getLeftChild();
        this.lastInserction.setFather(current);

        this.size++;

        upHeap(this.lastInserction);
    }
    @Override
    public Object removeMin(){
        if (this.root == null){
            throw new IllegalArgumentException(">>> A heap já está vaiza.");
        }

        Object toRemove = this.root.getElement();

        if (this.lastInserction == this.root){
            this.root = null;
            this.lastInserction = null;
            this.size--;

            return toRemove;
        }

        this.root.setKey(this.lastInserction.getKey());
        this.root.setElement(this.lastInserction.getElement());

        Node oldLast = this.lastInserction;
        Node previous = previousNode(oldLast);
        Node current = oldLast.getFather();

        if (oldLast == current.getRightChild()){
            current.setRightChild(null);
        } else {
            current.setLeftChild(null);
        }

        this.lastInserction = previous;

        downHeap(this.root);
        this.size--;

        return toRemove;
    }
    @Override
    public Object min(){
        if (this.root == null){
            throw new IllegalArgumentException(">>> A heap está vazia.");
        }
        return this.root.getElement();
    }
    @Override
    public int size(){
        return this.size;
    }
    @Override
    public boolean isEmpty(){
        return this.root == null;
    }

    public void showHeap(Node n) {
        if (n == null) {
            return;
        }

        System.out.println(
                "Chave: " + n.getKey() +
                        " | Elemento: " + n.getElement()
        );

        showHeap(n.getLeftChild());
        showHeap(n.getRightChild());
    }
    public void showHeap() {
        showHeap(this.root);
    }
}
