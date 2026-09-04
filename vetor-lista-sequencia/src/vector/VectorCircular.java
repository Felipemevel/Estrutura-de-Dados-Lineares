package vector;

import vector.exceptions.IndiceInvalidoException;
import vector.exceptions.VectorCheioException;

public class VectorCircular implements VectorInterface{

    private Object[] array;
    private int size;
    private int capacity;
    private int head;

    public VectorCircular(){
        this.capacity = 5;
        this.array = new Object[capacity];
        this.size = 0;
        this.head = 0;
    }
    public VectorCircular(int capacity){
        this.capacity = capacity;
        this.array = new Object[capacity];
        this.size = 0;
        this.head = 0;
    }
    private int getIndex(int r){
        return (this.head + r) % this.capacity;
    }

    public void resizeVector(int newCapacity){
        Object[] newArray = new Object[newCapacity];

        for (int i = 0; i < this.size; i++){
            newArray[i] = this.array[getIndex(i)];
        }

        this.capacity = newCapacity;
        this.array = newArray;
        this.head = 0;
    }
    public void checkSize(){
        if (this.size == this.capacity) {
            resizeVector(capacity * 2);
        } else if (this.size > 0 && this.size <= (this.capacity / 3)){
            int newCapacityTest = this.capacity / 2;
            if(newCapacityTest >= 5){
                resizeVector(newCapacityTest);
            }
        }
    }
    @Override
    public Object elemAtRank(int r){
        if (r < 0 || r >= this.size){
            throw new IndiceInvalidoException(">>> Índice inválido.");
        }
        return array[getIndex(r)];
    }
    @Override
    public Object replaceAtRank(int r, Object o){
        if (r < 0 || r >= this.size){
            throw new IndiceInvalidoException(">>> Índice inválido.");
        }

        Object toRemove = this.array[getIndex(r)];
        this.array[getIndex(r)] = o;

        return toRemove;
    }
    @Override
    public void insertAtRank(int r, Object o){
        checkSize();
        if (r < 0 || r > this.size){
            throw new IndiceInvalidoException(">>> Índice inválido.");
        }

        if (r == this.size){
            this.array[getIndex(this.size)] = o;
        } else if (r == 0){
            this.head = (this.head - 1 + this.capacity) % this.capacity;
            this.array[this.head] = o;
        } else {
            for (int i = this.size; i > r; i--){
                this.array[getIndex(i)] = this.array[getIndex(i-1)];
            }
            this.array[getIndex(r)] = o;
        }
        size++;
    }
    @Override
    public Object removeAtRank(int r){

        if (this.size == 0){
            throw new VectorCheioException(">>> O vetor já está vazio.");
        } else if (r >= this.size || r < 0){
            throw new IndiceInvalidoException(">>> Índice inválido.");
        }

        Object toRemove = this.array[getIndex(r)];
        if (r == 0){
            this.array[this.head] = null;
            this.head = (this.head + 1) % capacity;
        } else if (r == this.size-1){
            this.array[getIndex(this.size - 1)] = null;
        } else {
            for (int i = r; i < this.size - 1; i++) {
                this.array[getIndex(i)] = this.array[getIndex(i + 1)];
            }
            this.array[getIndex(this.size - 1)] = null;
        }

        this.size--;
        checkSize();
        return toRemove;
    }
    @Override
    public int size(){
        return this.size;
    }
    @Override
    public boolean isEmpty(){
        return size == 0;
    }
}
