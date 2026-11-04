package implementacao;

import arvoreHeapInterface.ArvoreHeapInterface;
import item.Item;

public class ArvoreHeapArray implements ArvoreHeapInterface {

    private Item[] array;
    private int capacity;
    private int size;

    private void checkSize(){
        if (this.size +1 >= this.capacity){
            resize();
        }
    }
    private void resize(){
        int newCapacity = this.capacity*2;
        Item[] newArray = new Item[newCapacity];

        for (int i = 1; i <= this.size; i++){
            newArray[i] = array[i];
        }

        this.array = newArray;
        this.capacity = newCapacity;
    }
    private void upheap(int idx){
        if (idx == 1){
            return;
        }

        int father = idx/2;
        if (this.array[idx].getKey() < this.array[father].getKey()){
            Item itemSwap = this.array[idx];

            this.array[idx] = this.array[father];
            this.array[father] = itemSwap;

            upheap(father);
        }
    }
    private void downheap(int idx){

        int left = idx * 2;
        int right = (idx*2)+1;

        if (left > this.size){
            return;
        }

        int smallerIdx = left;

        if (right <= this.size &&
                this.array[right].getKey() < this.array[left].getKey()) {
            smallerIdx = right;
        }

        if (this.array[smallerIdx].getKey() < this.array[idx].getKey()){
            Item itemSwap = this.array[idx];

            this.array[idx] = this.array[smallerIdx];
            this.array[smallerIdx] = itemSwap;

            downheap(smallerIdx);
        }
    }

    public ArvoreHeapArray(){
        this.capacity = 4;
        this.size = 0;
        this.array = new Item[this.capacity];
    }
    @Override
    public void insert(int k, Object e){
        checkSize();

        Item newItem = new Item(k, e);

        this.size++;
        this.array[this.size] = newItem;

        upheap(this.size);
    }
    @Override
    public Object removeMin(){
        if (this.size == 0){
            throw new IllegalArgumentException(">>> A heap já está vazia.");
        }

        Object toRemove = this.array[1].getElement();

        this.array[1] = this.array[this.size];
        this.array[this.size] = null;
        this.size--;

        downheap(1);

        return toRemove;
    }
    @Override
    public boolean isEmpty(){
        return this.size == 0;
    }
    @Override
    public int size(){
        return this.size;
    }
    @Override
    public Object min(){
        if (this.size == 0){
            throw new IllegalArgumentException(">>> A heap está vazia.");
        }
        return this.array[1].getElement();
    }
    public void showHeap() {
        for (int i = 1; i <= this.size; i++) {
            Item item = this.array[i];

            System.out.println(
                    "Índice: " + i +
                            " | Chave: " + item.getKey() +
                            " | Elemento: " + item.getElement()
            );
        }
    }
}
