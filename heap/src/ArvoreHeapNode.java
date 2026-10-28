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
        if (!(n.hasChildes())){
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
        Node current = this.lastInserction;

        while(true){
            if (current == this.root){
                break;
            }
            current = current.getFather();
            if (current == current.getFather().getLeftChild()){
                current = current.getFather().getRightChild();
                break;
            }
        }
        while (true){
            if(!(current.hasChildes())){
                current.
            }
        }
    }

    public Object removeMin(){
        Object toRemove = this.root.getElement();

        if (this.lastInserction == this.root){
            this.root = null;
            return toRemove;
        }

        this.root.setKey(this.lastInserction.getKey());

        Node current = this.lastInserction.getFather();

        if (current.hasChildes()){
            lastInserction = current.getLeftChild();
            current.setRightChild(null);

            downHeap(this.root);

            return toRemove;
        }
        lastInserction = current;
        lastInserction.setLeftChild(null);

        downHeap(this.root);

        return toRemove;
    }

    public int size(){
        return this.size;
    }
}
