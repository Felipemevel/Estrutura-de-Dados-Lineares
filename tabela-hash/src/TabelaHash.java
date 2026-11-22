public class TabelaHash implements DicionarioInterface{

    private Item[] array;
    private int capacity;
    private int size;
    private int primeNumber;

    public TabelaHash(){
        this.capacity = 13;
        this.primeNumber = 11;
        this.size = 0;
        this.array = new Item[this.capacity];
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
        return this.primeNumber - Math.floorMod(k, this.primeNumber);
    }
    @Override
    public Object findElement(int k){

        int i = h(k);
        int p = 0;

        do {

            Item c = this.array[i];
            if (c == null){
                return "NO_SUCH_KEY";
            } else if(c.getKey() == k){
                return c.getElement();
            }
            i = (i+1) % this.capacity;
            p++;

        } while (p != this.capacity);
        return "NO_SUCH_KEY";
    }


}
