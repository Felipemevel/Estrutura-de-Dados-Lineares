package vector;

import vector.exceptions.IndiceInvalidoException;
import vector.exceptions.VectorCheioException;
import vector.exceptions.VectorVazioException;

public class VectorArray implements VectorInterface {

    private Object[] array;
    private int size;
    private int capacity;

    public VectorArray(){
        this.capacity = 5;
        this.array = new Object[capacity];
        this.size = 0;
    }
    public VectorArray(int capacity){
        this.capacity = capacity;
        this.array = new Object[capacity];
        this.size = 0;
    }
    public void resizeVector(int newCapacity){
         Object[] newArray = new Object[newCapacity];

         for(int i = 0; i < this.size; i++){
             newArray[i] = this.array[i];
         }

         this.capacity = newCapacity;
         this.array = newArray;
    }
    public void checkSize(){
        if (this.size == this.capacity) {
            resizeVector(capacity * 2);
        } else if (this.size > 0 && this.size <= (this.capacity / 3)){
            int newCapacityTest = this.capacity / 2;
            if (newCapacityTest >= 5){
                resizeVector(newCapacityTest);
            }
        }
    }
    @Override
    public Object elemAtRank(int r){
        if (r < 0 || r >= this.size) {
            throw new IndiceInvalidoException(">>> Índice inválido.");
        }
        return array[r];
    }
    @Override
    public Object replaceAtRank(int r, Object o){
        if (r < 0 || r >= this.size) {
            throw new IndiceInvalidoException(">>> Índice inválido.");
        }
        Object toRemove = this.array[r];
        this.array[r] = o;

        return toRemove;
    }
    @Override
    public void insertAtRank(int r, Object o){
        checkSize();
        if (r < 0 || r > this.size) {
            throw new IndiceInvalidoException(">>> Índice inválido.");
        }
        for(int i = this.size; i > r; i--){
            this.array[i] = this.array[i-1];
        }
        this.array[r] = o;
        size++;
    }
    @Override
    public Object removeAtRank(int r){
        if (size == 0){
            throw new VectorVazioException(">>> O vetor já está vazio.");
        } else if (r > size){
            throw new IndiceInvalidoException(">>> Índice inválido.");
        }

        Object toRemove = this.array[r];
        for (int i = r; i < this.size -1; i++){
            this.array[i] = this.array[i+1];
        }

        this.array[this.size - 1] = null;
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
