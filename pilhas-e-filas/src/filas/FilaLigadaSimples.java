package filas;

public class FilaLigadaSimples implements FilaInterface{
    public static class Node{
        Object element;
        Node next;

        public Node(Object element){
            this.element = element;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public FilaLigadaSimples(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    public FilaLigadaSimples(Object element){
        this.head = new Node(element);
        this.tail = this.head;
        this.size = 1;
    }

    @Override
    public Object dequeue(){
        if (this.head == null){
            System.out.println(">>> A fila já está vazia...");
            return null;
        }
        Node temp = this.head;
        this.head = this.head.next;


        if (this.size == 1){
            this.tail = this.head;
        }

        size--;
        return temp.element;
    }
    @Override
    public void queue(Object element){

        Node newNode = new Node(element);
        if (this.tail == null){
            this.head = newNode;
            this.tail = this.head;
            this.size++;
            return;
        }
        this.tail.next = newNode;
        this.tail = newNode;

        this.size++;
    }
    @Override
    public int size(){
        return this.size;
    }
    @Override
    public boolean isEmpty(){
        return this.head == null;
    }
    @Override
    public Object front(){
        return this.head.element;
    }
}
