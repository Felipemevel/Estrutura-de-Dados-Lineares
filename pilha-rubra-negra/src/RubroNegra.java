public class RubroNegra {

    private int capacity;
    private int headNegra;
    private int headVermelha;
    private Object[] array;

    public RubroNegra(){
        this.capacity = 10;
        this.array = new Object[capacity];
        this.headNegra = capacity;
        this.headVermelha = -1;
    }
    public RubroNegra(int capacity){
        this.capacity = capacity;
        this.array = new Object[capacity];
        this.headNegra = capacity;
        this.headVermelha = -1;
    }

    public int sizeNegra(){
        return this.capacity - this.headNegra;
    }
    public int sizeVermelha(){
        return this.headVermelha + 1;
    }
    public int sizeTotal(){
        return sizeNegra() + sizeVermelha();
    }
    public void validacaoTamanho(){
        if (sizeTotal() == capacity){
            alterarTamanho(array.length * 2);
        } else if(sizeTotal() > 0 && sizeTotal() <= this.array.length / 3){
            alterarTamanho(array.length / 2);
        }
    }
    public void alterarTamanho(int newCapacity){
        Object[] newArray = new Object[newCapacity];
        int newHeadNegra = newCapacity;

        for (int i = 0; i <= headVermelha; i++){
            newArray[i] = this.array[i];
        }
        for (int i = array.length - 1; i >= this.headNegra; i--){
            newArray[--newHeadNegra] = this.array[i];
        }

        this.capacity = newCapacity;
        this.headNegra = newHeadNegra;
        this.array = newArray;
    }

    public void pushNegra(Object object){
        validacaoTamanho();

        this.array[headNegra-1] = object;
        this.headNegra--;
    }
    public void pushVermelha(Object object){
        validacaoTamanho();

        this.array[headVermelha+1] = object;
        this.headVermelha++;
    }
    public Object popNegra(Object object){
        if (headNegra == capacity){
            throw new PilhaVazia("A pilha negra já está vazia!");
        }
        Object temp = array[headNegra];
        array[headNegra] = null;
        headNegra++;
        return temp;
    }
    public Object popVermelha(Object object){
        if (headVermelha == -1){
            throw new PilhaVazia("A pilha vermelha já está vazia!");
        }
        Object temp = array[headVermelha];
        array[headVermelha] = null;
        headVermelha--;
        return temp;
    }
    public Object topNegra(){
        return array[headNegra];
    }
    public Object topVermelha(){
        return array[headVermelha];
    }
}
