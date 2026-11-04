package arvoreHeapInterface;

public interface ArvoreHeapInterface {

    void insert(int k, Object e);
    Object removeMin();
    int size();
    boolean isEmpty();
    Object min();
}
