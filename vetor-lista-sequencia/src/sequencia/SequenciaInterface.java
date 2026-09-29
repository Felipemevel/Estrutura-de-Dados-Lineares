package sequencia;

public interface SequenciaInterface {

    Position atRank(int r);
    int rankOf(Position p);

    int size();
    boolean isEmpty();

    void insertAtRank(int r, Object o);
    Object removeAtRank(int r);
    Object replaceAtRank(int r, Object o);
    Object elemAtRank(int r);

    Object before(Position p);
    Object after(Position p);
    Object replaceElement(Position p, Object o);
    void swapElement(Position p, Position q);
    void insertBefore (Position p, Object o);
    void insertAfter(Position p, Object o);
    void insertFirst(Object o);
    void insertLast(Object o);
    Object remove(Position p);
    Object last();
    Object first();
}
