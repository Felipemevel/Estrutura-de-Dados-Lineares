public interface DicionarioInterface {

    Object findElement(int k);
    void insertItem(int k, Object e);
    Object removeElement(int k);
    int size();
    boolean isEmpty();
    int[] keys();
    Object[] elements();

}
