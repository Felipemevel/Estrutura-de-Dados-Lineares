import node.Node;

public class ArvoreHeapNode {

    private Node root;
    private Node lastInserction;
    private int size;

    private void upHeap(Node n){
        if (n == this.root) {
            return;
        }

        if (n.getFather().getKey() < n.getKey()){
            int tempSwap = n.getKey();

            n.setKey(n.getFather().getKey());
            n.getFather().setKey(tempSwap);

            upHeap(n.getFather());
        }
    }
    private void downHeap(Node n){
        if (!(n.hasChild())){
            return;
        }

        if (n.getRightChild().getKey() < n.getKey() &&
        n.getLeftChild().getKey() < n.getKey()){
            int tempSwap = n.getKey();

            if (n.getRightChild().getKey() < n.getLeftChild().getKey()){

                n.setKey(n.getRightChild().getKey());
                n.getRightChild().setKey(tempSwap);

                downHeap(n.getRightChild());
            } else {

                n.setKey(n.getLeftChild().getKey());
                n.getLeftChild().setKey(tempSwap);

                downHeap(n.getLeftChild());
            }
        }
        if (n.getRightChild().getKey() < n.getKey()){
            int tempSwap = n.getKey();

            n.setKey(n.getRightChild().getKey());
            n.getRightChild().setKey(tempSwap);

            downHeap(n.getRightChild());
        }
        if (n.getLeftChild().getKey() < n.getKey()){
            int tempSwap = n.getKey();

            n.setKey(n.getLeftChild().getKey());
            n.getLeftChild().setKey(tempSwap);

            downHeap(n.getLeftChild());
        }
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

    public void insert(int k, Object o){
        Node newNode = new Node(k, o);
        Node current = this.lastInserction.getFather();

        if (!(current.hasRightChild())){
            current.setRightChild(newNode);
            newNode.setFather(current);

            this.lastInserction = newNode;
            this.size++;

            return;
        }

        current = this.root;

        while (!(current.hasLeftChild())){
            current = current.getLeftChild();
        }

        current.setLeftChild(newNode);
        this.size++;
        this.lastInserction = newNode;

        if (this.lastInserction.getKey() < this.lastInserction.getFather().getKey()){
            upHeap(this.lastInserction);
        }
    }
}
