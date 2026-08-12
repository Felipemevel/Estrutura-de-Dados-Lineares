package pilhas;

public interface PilhaInterface {
    boolean isEmpty();
    int size();
    void push(Object element);
    Object pop();
    Object top();
}
