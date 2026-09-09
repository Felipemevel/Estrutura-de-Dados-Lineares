package lista;

import lista.exceptions.ListaCheiaException;

public class ListaArray {

    private Object[] array;
    private int capacity;
    private int size;

    public ListaArray(){
        this.capacity = 5;
        this.array = new Object[capacity];
        this.size = 0;
    }
    public ListaArray(int capacity){
        this.capacity = capacity;
        this.array = new Object[this.capacity];
        this.size = 0;
    }

    public void insertFirst(Object o){
        if (size() == this.capacity-1){
            throw new ListaCheiaException(">>> A lista está cheia.");
        }
        if (size() == 0){
            this.array[0] = o;
            return;
        }
        for (int i = 1; i < size()-1; i++){
            this.array[i] = this.array[i-1];
        }
        this.array[0] = o;
        this.size++;
    }
    public void insertLast(Object o){
        if (size() == this.capacity-1){
            throw new ListaCheiaException(">>> A lista está cheia.");
        }
        this.array[this.size] = o;
        this.size++;
    }
    public int size(){
        return this.size;
    }
    public boolean isEmpty(){
        return this.size == 0;
    }
}
