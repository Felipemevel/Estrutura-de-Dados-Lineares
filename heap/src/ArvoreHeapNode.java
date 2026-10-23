import node.Node;

public class ArvoreHeapNode {

    private Node root;
    private Node lastInserction;
    private int size;

    private void upHeap(Node n){
        if (n == this.root || n.getFather() == null) {
            return;
        }

        if (n.getFather().getKey() < n.getKey()){
            int tempSwap = n.getKey();

            n.setKey(n.getFather().getKey());
            n.getFather().setKey(tempSwap);

            upHeap(n.getFather());
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
