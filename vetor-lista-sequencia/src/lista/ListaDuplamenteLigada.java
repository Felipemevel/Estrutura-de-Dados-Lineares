package lista;

import lista.exceptions.IndiceInvalidoException;
import lista.exceptions.ListaVaziaException;
import node.Node;

public class ListaDuplamenteLigada implements ListaInterface {

    private Node sentinel;

    public ListaDuplamenteLigada(){
        this.sentinel = new Node(0);

        this.sentinel.setPrev(this.sentinel);
        this.sentinel.setNext(this.sentinel);
    }
    public ListaDuplamenteLigada(Object o){
        Node newNode = new Node(o);
        this.sentinel = new Node(1);

        newNode.setPrev(this.sentinel);
        newNode.setNext(this.sentinel);

        this.sentinel.setNext(newNode);
        this.sentinel.setPrev(newNode);
    }
    private Node nodeCheck(Node node){
        if (node == null || node == sentinel){
            throw new IndiceInvalidoException(">>> A posição passada é inválida.");
        }
        return node;
    }
    private Node findNode(Object o){
        Node current = sentinel.getNext();
        while (current != sentinel){
            if (current.getElement() != null && current.getElement().equals(o)){
                return current;
            }
            current = current.getNext();
        }
        return null;
    }
    public boolean isFirst(Node node){
        nodeCheck(node);
        return sentinel.getNext() == node;
    }
    public boolean isLast(Node node){
        nodeCheck(node);
        return sentinel.getPrev() == node;
    }
    public Object first(){
        if (isEmpty()){
            throw new ListaVaziaException(">>> A lista está vazia.");
        }
        return sentinel.getNext();
    }
    public Object last(){
        if (isEmpty()){
            throw new ListaVaziaException(">>> A lista está vazia.");
        }
        return sentinel.getPrev();
    }
    public Object before(Node node){
        nodeCheck(node);
        if (node.getPrev() == sentinel){
            throw new IndiceInvalidoException(">>> Índice inválido.");
        }
        return node.getPrev();
    }
    public Object after(Node node){
        nodeCheck(node);
        if (node.getNext() == sentinel){
            throw new IndiceInvalidoException(">>> Índice inválido.");
        }
        return node.getNext();
    }
    @Override
    public void insertFirst(Object o){
        Node newNode = new Node(o);
        Node first = sentinel.getNext();

        newNode.setPrev(sentinel);
        newNode.setNext(first);
        sentinel.setNext(newNode);
        first.setPrev(newNode);

        this.sentinel.setElement((Integer)sentinel.getElement()+1);
    }
    @Override
    public void insertLast(Object o){
        Node newNode = new Node(o);
        Node last = sentinel.getPrev();

        newNode.setPrev(last);
        newNode.setNext(sentinel);
        last.setNext(newNode);
        sentinel.setPrev(newNode);

        this.sentinel.setElement((Integer)sentinel.getElement()+1);
    }
    @Override
    public Object replaceElement(Object n, Object o){
        Node node = findNode(n);
        if (node == null){
            throw new IndiceInvalidoException(">>> Elemento não encontrado.");
        }
        Object temp = node.getElement();
        node.setElement(o);
        return temp;
    }
    @Override
    public void swapElements(Object n, Object o){
        Node nodeN = findNode(n);
        Node nodeO = findNode(o);
        if (nodeN == null || nodeO == null){
            throw new IndiceInvalidoException(">>> Elemento não encontrado.");
        }
        Object temp = nodeN.getElement();
        nodeN.setElement(nodeO.getElement());
        nodeO.setElement(temp);
    }
    @Override
    public void insertBefore(Object n, Object o){
        Node node = findNode(n);
        if (node == null){
            throw new IndiceInvalidoException(">>> Elemento não encontrado.");
        }

        Node newNode = new Node(o);
        Node prev = node.getPrev();

        newNode.setPrev(prev);
        newNode.setNext(node);
        prev.setNext(newNode);
        node.setPrev(newNode);

        this.sentinel.setElement((Integer)sentinel.getElement()+1);
    }
    @Override
    public void insertAfter(Object n, Object o){
        Node node = findNode(n);
        if (node == null){
            throw new IndiceInvalidoException(">>> Elemento não encontrado.");
        }

        Node newNode = new Node(o);
        Node next = node.getNext();

        newNode.setPrev(node);
        newNode.setNext(next);
        node.setNext(newNode);
        next.setPrev(newNode);

        this.sentinel.setElement((Integer)sentinel.getElement()+1);
    }
    @Override
    public Object remove(Object n){
        if (isEmpty()){
            throw new ListaVaziaException(">>> A lista está vazia.");
        }
        Node node = findNode(n);
        if (node == null){
            throw new IndiceInvalidoException(">>> Elemento não encontrado.");
        }

        Node prev = node.getPrev();
        Node next = node.getNext();

        prev.setNext(next);
        next.setPrev(prev);

        this.sentinel.setElement((Integer)sentinel.getElement()-1);
        return node.getElement();
    }
    @Override
    public int size(){
        return (Integer) sentinel.getElement();
    }
    @Override
    public boolean isEmpty(){
        return size() == 0;
    }
}