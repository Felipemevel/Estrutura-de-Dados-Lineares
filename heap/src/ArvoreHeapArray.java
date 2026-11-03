import item.Item;

public class ArvoreHeapArray {

    private Item[] array;
    private int capacity;
    private int size;
    private int idxPtr;

    private void checkSize(){
        if (this.size-1 == this.capacity){
            resize();
        }
    }
    private void resize(){
        int newCapacity = this.capacity*2;
        Item[] newArray = new Item[newCapacity];

        for (int i = 1; i < this.capacity; i++){
            newArray[i] = array[i];
        }

        this.array = newArray;
        this.capacity = newCapacity;
    }

    public ArvoreHeapArray(){
        this.capacity = 4;
        this.size = 0;
        this.array = new Item[this.capacity];
    }

    public void insert(int k, Object e){
        Item newItem = new Item(k, e);

        if(this.array[1] == null){
            this.array[1] = newItem;
            this.size++;
            this.idxPtr = 1;

            return;
        }

        checkSize();

        if (this.array[this.idxPtr * 2] == null){
            this.array[this.idxPtr * 2] = newItem;
            this.size++;

            return;
        } else if (this.array[(this.idxPtr * 2) + 1] == null){
            this.array[(this.idxPtr * 2) + 1] = newItem;
            this.size++;

            return;
        }

    }

    public int size(){
        return this.size;
    }
}
