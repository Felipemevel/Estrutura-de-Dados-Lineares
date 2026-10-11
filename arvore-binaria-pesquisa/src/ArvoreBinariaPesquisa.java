import node.Node;

import java.util.ArrayList;
import java.util.Iterator;


public class ArvoreBinariaPesquisa {

    private Node root;
    private int size;

    private static int compare(Object a, Object b){
        if (!(a instanceof Comparable)){
            throw new IllegalArgumentException(">>> O objeto não implementa Comparable.");
        }
        return ((Comparable<Object>)a).compareTo(b);
    }

    private void insert(Object o, Node currentNode){
        int res = compare(o, currentNode.getElement());

        if (res < 0){
            if (currentNode.getLeftChild() == null){
                currentNode.setLeftChild(new Node(o));
                this.size++;
            } else {
                insert(o, currentNode.getLeftChild());
            }
        } else if (res > 0){
            if (currentNode.getRightChild() == null){
                currentNode.setRightChild(new Node(o));
                this.size++;
            } else {
                insert(o, currentNode.getRightChild());
            }
        }
    }
    private Node remove(Object o, Node currentNode){
        if (currentNode == null){
            return null;
        }

        int res = compare(o, currentNode.getElement());

        if (res< 0){
            currentNode.setLeftChild(remove(o, currentNode.getLeftChild()));
        } else if (res > 0){
            currentNode.setRightChild(remove(o, currentNode.getRightChild()));
        } else {
            // CASO 01: Nó folha

            if (!currentNode.hasLeftChild()
            && !currentNode.hasRightChild()){
                this.size--;
                return null;
            }
            // Caso 02: Nó com um filhinho somente

            if (!currentNode.hasLeftChild()){
                this.size--;
                return currentNode.getRightChild();
            }
            if (!currentNode.hasRightChild()){
                this.size--;
                return currentNode.getLeftChild();
            }
            // Caso 03: Nó com dois filhinhos

            Node sucessor = lower(currentNode.getRightChild());
            currentNode.setElement(sucessor.getElement());

            currentNode.setRightChild(remove(sucessor.getElement(), currentNode.getRightChild()));

            // Aqui foi a IA que me deu uma ajuda

            return currentNode;
        }

        return currentNode;
    }
    private Node lower(Node currentNode){
        if (currentNode.getLeftChild() == null){
            return currentNode;
        }

        return lower(currentNode.getLeftChild());
    }
    private int height(Node currentNode) {
        if (currentNode == null) {
            return -1;
        }

        int alturaEsquerda = height(currentNode.getLeftChild());
        int alturaDireita = height(currentNode.getRightChild());

        return 1 + Math.max(alturaEsquerda, alturaDireita);
    }
    private int depth(Node currentNode, Node target, int nivel) {
        if (currentNode == null) {
            return -1;
        }

        if (currentNode == target) {
            return nivel;
        }

        int left = depth(
                currentNode.getLeftChild(), target, nivel + 1
        );

        if (left != -1) {
            return left;
        }

        return depth(currentNode.getRightChild(), target, nivel + 1);
    }
    private void iterateOverElements(Node currentNode, ArrayList<Object> elements){
        if (currentNode == null){
            return;
        }
        iterateOverElements(currentNode.getLeftChild(), elements);
        elements.add(currentNode.getElement());
        iterateOverElements(currentNode.getRightChild(), elements);
    }
    private void iterateOverNodes(Node currentNode, ArrayList<Node> nodes){
        if (currentNode == null){
            return;
        }
        iterateOverNodes(currentNode.getLeftChild(), nodes);
        nodes.add(currentNode);
        iterateOverNodes(currentNode.getRightChild(), nodes);
    }
    private void fillMatriz(Node currentNode, Object[][] matriz,
                            int level, int start, int end){
        if (currentNode == null){
            return;
        }

        int mid = (start + end) / 2;
        matriz[level][mid] = currentNode.getElement();

        fillMatriz(
                currentNode.getLeftChild(),
                matriz,
                level + 1,
                start,
                mid -1
        );
        fillMatriz(
                currentNode.getRightChild(),
                matriz,
                level + 1,
                mid + 1,
                end
        );
    }


    public ArvoreBinariaPesquisa(){
        this.root = null;
        this.size = 0;

    }
    public ArvoreBinariaPesquisa(Object o){
        this.root = new Node(o);
        this.size = 1;
    }

    public boolean isEmpty(){
        return this.root == null;
    }

    public Node leftChild(Node v){
        return v.getLeftChild();
    }
    public Node rightChild(Node v){
        return v.getRightChild();
    }
    public Node hasLeft(Node v){
        if (v == null){
            throw new IllegalArgumentException(">>> O nó fornecido é nulo.");
        }
        if (!v.hasLeftChild()){
            throw new IllegalArgumentException(">>> O nó não possui filho esquerdo.");
        }
        return v.getLeftChild();
    }
    public Node hasRight(Node v){
        if (v == null){
            throw new IllegalArgumentException(">>> O nó fornecido é nulo.");
        }
        if (!v.hasRightChild()){
            throw new IllegalArgumentException(">>> O nó não possui filho direito.");
        }
        return v.getRightChild();
    }
    public void insert(Object o){
        if (this.root == null){
            Node newNode = new Node(o);
            this.root = newNode;
            this.size++;
            return;
        }

        insert(o, this.root);
    }
    public void remove(Object o){
        this.root = remove(o, this.root);
    }
    public Node root(){
        return this.root;
    }
    public Node parent(Node v){
        return v.getFather();
    }
    public int height() {
        return height(this.root);
    }
    public int depth(Node v) {
        if (v == null) {
            throw new IllegalArgumentException(">>> O nó fornecido é nulo.");
        }

        return depth(this.root, v, 0);
    }
    public boolean isRoot(Node v){
        return v == this.root;
    }
    public boolean isInternal(Node v){
        return !v.isLeaf();
    }
    public boolean isExternal(Node v){
        return v.isLeaf();
    }
    public Iterator<Object> elements(){
        ArrayList<Object> elements = new ArrayList<>();
        iterateOverElements(this.root, elements);
        return elements.iterator();
    }
    public Iterator<Node> nodes(){
        ArrayList<Node> nodes = new ArrayList<>();
        iterateOverNodes(this.root, nodes);
        return nodes.iterator();
    }
    public Iterator<Node> children(Node v){
        if (v == null){
            throw new IllegalArgumentException(">>> O nó fornecido é nulo.");
        }
        ArrayList<Node> childrens = new ArrayList<>();

        if (v.getLeftChild() != null){
            childrens.add(v.getLeftChild());
        }
        if (v.getRightChild() != null){
            childrens.add(v.getRightChild());
        }

        return childrens.iterator();
    }

    public void replace(Node v, Object o) {
        if (v == null) {
            throw new IllegalArgumentException(
                    ">>> O nó fornecido é nulo."
            );
        }

        if (!(o instanceof Comparable)) {
            throw new IllegalArgumentException(
                    ">>> O objeto não implementa Comparable."
            );
        }

        Node father = v.getFather();

        if (father != null) {
            int res = compare(o, father.getElement());

            if (v == father.getLeftChild() && res >= 0) {
                throw new IllegalArgumentException(">>> O novo elemento deve ser menor que o pai.");
            }

            if (v == father.getRightChild() && res <= 0) {
                throw new IllegalArgumentException(">>> O novo elemento deve ser maior que o pai.");
            }
        }

        if (v.getLeftChild() != null
                && compare(o, v.getLeftChild().getElement()) <= 0) {
            throw new IllegalArgumentException(">>> O novo elemento deve ser maior que o filho esquerdo.");
        }

        if (v.getRightChild() != null
                && compare(o, v.getRightChild().getElement()) >= 0) {
            throw new IllegalArgumentException(">>> O novo elemento deve ser menor que o filho direito.");
        }

        v.setElement(o);

        // IA ajudou nessa
    }
    public void show(){
        if (this.root == null){
            throw new IllegalArgumentException(">>> A árvore esta vazia");
        }

        int rows = height() + 1;
        int cols = (int) Math.pow(2, rows) -1;

        Object[][] matriz = new Object[rows][cols];

        fillMatriz(this.root, matriz, 0, 0, cols -1);

        for (Object[] line : matriz){
            for (Object element : line){
                System.out.print(element == null ? "\t" : element + "\t");
            }
            System.out.println();
        }
    }

}
