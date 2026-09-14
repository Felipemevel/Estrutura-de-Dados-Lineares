package lista;

import lista.exceptions.IndiceInvalidoException;
import lista.exceptions.ListaVaziaException;

public class ListaArray implements ListaInterface {

    private Object[] array;
    private int capacity;
    private int size;

    public ListaArray() {
        this.capacity = 5;
        this.array = new Object[capacity];
        this.size = 0;
    }

    public ListaArray(int capacity) {
        this.capacity = capacity;
        this.array = new Object[this.capacity];
        this.size = 0;
    }

    public void resizeVector(int newCapacity) {
        Object[] newArray = new Object[newCapacity];

        for (int i = 0; i < this.size; i++) {
            newArray[i] = this.array[i];
        }

        this.capacity = newCapacity;
        this.array = newArray;
    }

    public void checkSize() {
        if (this.size == this.capacity) {
            resizeVector(capacity * 2);
        } else if (this.size > 0 && this.size <= (this.capacity / 3)) {
            int newCapacityTest = this.capacity / 2;
            if (newCapacityTest >= 5) {
                resizeVector(newCapacityTest);
            }
        }
    }

    private int indexOf(Object o) {
        for (int i = 0; i < this.size; i++) {
            if (this.array[i] != null && this.array[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void insertFirst(Object o) {
        checkSize();
        for (int i = this.size; i > 0; i--) {
            this.array[i] = this.array[i - 1];
        }
        this.array[0] = o;
        this.size++;
    }

    @Override
    public void insertLast(Object o) {
        checkSize();
        this.array[this.size] = o;
        this.size++;
    }

    @Override
    public Object replaceElement(Object n, Object o) {
        int index = indexOf(n);
        if (index == -1) {
            throw new IndiceInvalidoException(">>> Elemento não encontrado.");
        }
        Object temp = this.array[index];
        this.array[index] = o;
        return temp;
    }

    @Override
    public void swapElements(Object n, Object o) {
        int indexN = indexOf(n);
        int indexO = indexOf(o);
        if (indexN == -1 || indexO == -1) {
            throw new IndiceInvalidoException(">>> Elemento não encontrado.");
        }
        Object temp = this.array[indexN];
        this.array[indexN] = this.array[indexO];
        this.array[indexO] = temp;
    }

    @Override
    public void insertBefore(Object n, Object o) {
        int index = indexOf(n);
        if (index == -1) {
            throw new IndiceInvalidoException(">>> Elemento não encontrado.");
        }
        checkSize();
        for (int i = this.size; i > index; i--) {
            this.array[i] = this.array[i - 1];
        }
        this.array[index] = o;
        this.size++;
    }

    @Override
    public void insertAfter(Object n, Object o) {
        int index = indexOf(n);
        if (index == -1) {
            throw new IndiceInvalidoException(">>> Elemento não encontrado.");
        }
        checkSize();
        for (int i = this.size; i > index + 1; i--) {
            this.array[i] = this.array[i - 1];
        }
        this.array[index + 1] = o;
        this.size++;
    }

    @Override
    public Object remove(Object n) {
        if (isEmpty()) {
            throw new ListaVaziaException(">>> A lista está vazia.");
        }
        int index = indexOf(n);
        if (index == -1) {
            throw new IndiceInvalidoException(">>> Elemento não encontrado.");
        }
        Object temp = this.array[index];
        for (int i = index; i < this.size - 1; i++) {
            this.array[i] = this.array[i + 1];
        }
        this.array[this.size - 1] = null;
        this.size--;
        checkSize();
        return temp;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }
}