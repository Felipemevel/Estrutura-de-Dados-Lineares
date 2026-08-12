package filas;

public class FilaArray implements FilaInterface{
    private int capacity;
    private final static int capacity_ = 1000;
    private Object[] array;
    private int front = 0;
    private int rear = -1;
    private int size;

    public FilaArray(){
        this(capacity_);
    }
    public FilaArray(int cap){
        this.capacity = cap;
        array = new Object[capacity];
        this.size = 0;
    }
    @Override
    public Object dequeue(){
        if (size == 0){
            System.out.println(">>> Fila já está vazia...");
            return null;
        }
        Object temp = array[front];
        array[front] = null;
        front = (front + 1) % capacity;
        this.size--;
        return temp;
    }
    @Override
    public void queue(Object element){
        if (this.size == capacity){
            System.out.println(">>> Fila já está cheia!");
            return;
        }
        rear = (rear + 1) % capacity;
        array[rear] = element;
        this.size++;
    }
    @Override
    public int size(){
        return this.size;
    }
    @Override
    public boolean isEmpty(){
        return array[front] == null;
    }
    @Override
    public Object front(){
        return array[front];
    }
}
