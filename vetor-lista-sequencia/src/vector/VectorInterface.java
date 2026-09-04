package vector;

public interface VectorInterface {
    Object elemAtRank(int r);
    Object replaceAtRank(int r, Object o);
    void insertAtRank(int r, Object o);
    Object removeAtRank(int r);
    int size();
    boolean isEmpty();
}
