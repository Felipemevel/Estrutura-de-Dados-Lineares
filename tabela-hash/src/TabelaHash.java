public class TabelaHash implements DicionarioInterface{

    private Item[] array;
    private int capacity;
    private int size;
    private static final Item AVAILABLE = new Item(-1, null);

    public TabelaHash(int capacity){
        if (capacity < 3){
            capacity = 3;
        }
        this.capacity = capacity;
        this.size = 0;
        this.array = new Item[this.capacity];
    }
    public TabelaHash(){
        this.capacity = 13;
        this.size = 0;
        this.array = new Item[this.capacity];
    }
    private void loadFactor(){
        double alpha = (double) this.size / this.capacity;
        if (alpha >= 0.5){
            this.array = rehash();
        }
    }
    private boolean isPrime(int n){
        if (n < 2){
            return false;
        }
        for (int i = 2; i*i <= n; i++){
            if (n % i == 0){
                return false;
            }
        }
        return true;
    }
    private int findHighestPrime(){
        int prime = this.capacity-1;
        while (!isPrime(prime)){
            prime--;
        }
        return prime;
    }
    private int h1(int k){
        return k;
    }
    private int h2(int x){
        return Math.floorMod(x, this.capacity);
    }
    private int h(int k){
        return h2(h1(k));
    }
    private int d(int k){
        int q = 3;
        if (this.capacity <= 3){
            q = findHighestPrime();
        }
        return q - Math.floorMod(k, q);
    }
    private Item[] rehash(){

        this.capacity = this.capacity*2;
        Item[] newArray = new Item[this.capacity];

        if (isPrime(this.capacity)){
            for (int i = 0; i < (this.capacity/2); i++){
                if (this.array[i] == null
                        || this.array[i] == AVAILABLE){
                    continue;
                }
                int idx = h(this.array[i].getKey());
                int step = d(this.array[i].getKey());
                while (newArray[idx] != null){
                    idx = (idx + step) % this.capacity;
                }
                newArray[idx] = this.array[i];
            }
            return newArray;
        } else {
            for (int i = 0; i < (this.capacity/2); i++){
                if (this.array[i] == null
                        || this.array[i] == AVAILABLE){
                    continue;
                }
                int idx = h(this.array[i].getKey());
                while (newArray[idx] != null){
                   idx = (idx + 1) % this.capacity;
                }
                newArray[idx] = this.array[i];
            }
            return newArray;
        }
    }
    @Override
    public Object findElement(int k){

        int i = h(k);
        int p = 0;

        if (isPrime(this.capacity)){
            do {

                Item c = this.array[i];
                if (c == null){
                    return "NO_SUCH_KEY";
                } else if(c != AVAILABLE
                        && c.getKey() == k){
                    return c.getElement();
                }
                i = (i+ d(k)) % this.capacity;
                p++;

            } while (p != this.capacity);
        } else {
            do {

                Item c = this.array[i];
                if (c == null){
                    return "NO_SUCH_KEY";
                } else if(c != AVAILABLE
                        && c.getKey() == k){
                    return c.getElement();
                }
                i = (i+1) % this.capacity;
                p++;

            } while (p != this.capacity);
        }
        return "NO_SUCH_KEY";
    }
    @Override
    public void insertItem(int k, Object e){

        Item newItem = new Item(k, e);

        if (isPrime(this.capacity)){
            int idx = h(k);
            int step = d(k);
            while(this.array[idx] != null
                    && this.array[idx] != AVAILABLE){
                idx = (idx + step) % this.capacity;
            }
            this.array[idx] = newItem;
        } else {
            int idx = h(k);
            while (this.array[idx] != null
                    && this.array[idx] != AVAILABLE){
                idx = (idx + 1) % this.capacity;
            }
            this.array[idx] = newItem;
        }
        this.size++;
        loadFactor();
    }
    @Override
    public Object removeElement(int k){

        if (isPrime(this.capacity)){

            int idx = h(k);
            int step = d(k);
            int attempts = 0;

            while (this.array[idx] != null
                    && this.array[idx].getKey() != k
                    && attempts < this.capacity){

                idx = (idx + step) % this.capacity;
                attempts++;
            }
            if (this.array[idx] == null
                    || this.array[idx].getKey() != k){
                return "NO_SUCH_KEY";
            }

            Object toRemove = this.array[idx].getElement();
            this.array[idx] = AVAILABLE;
            this.size--;

            return toRemove;
        } else {

            int idx = h(k);
            int attempts = 0;

            while(this.array[idx] != null
                    && this.array[idx].getKey() != k
                    && attempts < this.capacity){
                idx = (idx + 1) % this.capacity;
                attempts++;
            }
            if (this.array[idx] == null
                    || this.array[idx].getKey() != k){
                return "NO_SUCH_KEY";
            }

            Object toRemove = this.array[idx].getElement();
            this.array[idx] = AVAILABLE;
            this.size--;

            return toRemove;
        }
    }
    @Override
    public int size(){
        return this.size;
    }
    @Override
    public boolean isEmpty(){
        return this.size == 0;
    }
    @Override
    public int[] keys(){
        int[] keys = new int[this.size];
        int keysIdx = 0;
        for (int i = 0; i < this.capacity; i++){
            if (this.array[i] != null
                    && this.array[i] != AVAILABLE){
                keys[keysIdx] = this.array[i].getKey();
                keysIdx++;
            }
        }
        return keys;
    }
    @Override
    public Object[] elements(){

        Object[] elements = new Object[this.size];
        int elementsIdx = 0;

        for (int i = 0; i < this.capacity; i++){
            if (this.array[i] != null
                    && this.array[i] != AVAILABLE){
                elements[elementsIdx] = this.array[i].getElement();
                elementsIdx++;
            }
        }
        return elements;
    }
}
