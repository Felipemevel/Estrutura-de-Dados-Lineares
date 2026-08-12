package pilhas;

public class PilhaArray implements PilhaInterface{
    private int capacity;
    private static final int capacity_ = 1000;
    private Object[] array;
    private int top = -1;

    public PilhaArray(){
        this(capacity_);
    }
    public PilhaArray(int cap){
        capacity = cap;
        array = new Object[capacity];
    }
    @Override
    public boolean isEmpty(){
        return top < 0;
    }
    @Override
    public int size(){
        return top+1;
    }
    @Override
    public void push(Object element){
        if (size() == capacity){
            System.out.println(">>> A pilha já está cheia!");
            return;
        }
        top++;
        array[top] = element;
    }
    @Override
    public Object pop(){
        if (isEmpty()){
            System.out.println(">>> A pilha já está vazia...");
            return null;
        }
        Object element = array[top];
        array[top] = null;
        top--;
        return element;
    }
    @Override
    public Object top(){
        if (isEmpty()){
            System.out.println(">>> Não há itens na pilha");
            return null;
        }
        return array[top];
    }
}
