package Pilhas;

public class PilhaLigadaSimples implements PilhaInterface{
    private static class Node{
        Object element;
        Node next;

        public Node(Object element){
            this.element = element;
            this.next = null;
        }
    }
    private Node head;
    private int size;

    public PilhaLigadaSimples(){
        this.head = null;
        this.size = 0;
    }
    public PilhaLigadaSimples(Object element){
        this.head = new Node(element);
        this.size = 1;
    }

    @Override
    public boolean isEmpty(){
        return this.head == null;
    }
    @Override
    public int size(){
        return this.size;
    }
    @Override
    public void push(Object element){
        Node newNode = new Node(element);
        newNode.next = this.head;
        this.head = newNode;
        this.size++;
    }
    @Override
    public Object pop(){
        if(this.head == null){
            System.out.println(">>> A pilha já está vazia...");
            return null;
        }

        Object poppedNode = this.head.element;
        this.head = this.head.next;
        this.size--;

        return poppedNode;
    }
    @Override
    public Object top(){
        return this.head.element;
    }
}
