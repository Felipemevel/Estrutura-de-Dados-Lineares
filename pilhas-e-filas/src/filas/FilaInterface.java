package filas;

public interface FilaInterface {
    Object dequeue();
    void queue(Object element);
    int size();
    boolean isEmpty();
    Object front();
}
