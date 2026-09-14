package lista;

public interface ListaInterface {
    void insertFirst(Object o);
    void insertLast(Object o);
    Object replaceElement(Object n, Object o);
    void swapElements(Object n,Object o);
    void insertBefore(Object n, Object o);
    void insertAfter(Object n, Object o);
    Object remove(Object n);
    public int size();
    public boolean isEmpty();
}
