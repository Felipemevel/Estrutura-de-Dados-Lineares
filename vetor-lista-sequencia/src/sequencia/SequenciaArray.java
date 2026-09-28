package sequencia;

import sequencia.Exceptions.IndiceInvalidoException;
import sequencia.Exceptions.PositionInvalidoException;
import sequencia.Exceptions.SequenciaVaziaException;

public class SequenciaArray {

    private int size;
    private int capacity;
    private SeqArrayPosition[] array;

    public SequenciaArray(){
        this.capacity = 5;
        this.array = new SeqArrayPosition[capacity];
        this.size = 0;
    }
    public SequenciaArray(int capacity){
        this.capacity = capacity;
        this.array = new SeqArrayPosition[capacity];
        this.size = 0;
    }

    private class SeqArrayPosition implements Position{

        private Object element;
        private int idx;

        public SeqArrayPosition(Object o, int i){
            this.element = o;
            this.idx = i;
        }
        @Override
        public Object getElement(){
            return this.element;
        }
        public void setElement(Object o){
            this.element = o;
        }
        public int getIdx(){
            return this.idx;
        }
        public void setIdx(int i){
            this.idx = i;
        }
    }

    private void checkSize(int opCode){
        switch (opCode){
            case 0:
                if (size() == this.capacity) {
                    resize(array.length * 2);
                }
                break;
            case 1:
                if(size() <= this.array.length / 3){
                    resize(array.length / 2);
                }
        }
    }
    private void resize(int newCapacity){
        SeqArrayPosition[] newArray = new SeqArrayPosition[newCapacity];

        for (int i = 0; i <= size()-1; i++){
            newArray[i] = this.array[i];
        }

        this.capacity = newCapacity;
        this.array = newArray;
    }
    private SeqArrayPosition checkPosition(Position p){
        if (!(p instanceof SeqArrayPosition)){
            throw new PositionInvalidoException(">>> O elemento informado não é válido.");
        }

        SeqArrayPosition pos = (SeqArrayPosition) p;

        if (pos.getIdx() < 0 || pos.getIdx() > size()
                || this.array[pos.getIdx()] != pos) {
            throw new PositionInvalidoException(">>> A posição informada não pertence a esta sequência");
        }

        return pos;
    }
    private void validateRank (int r){
        if(isEmpty()){
            throw new SequenciaVaziaException(">>> Sequência vazia.");
        }
        if (r < 0 || r >= size()){
            throw new IndiceInvalidoException(">>> Índice inválido.");
        }
    }
    public Position atRank(int r){
        validateRank(r);
        return array[r];
    }
    public void insertAtRank(int r, Object o){
        if (r < 0 || r > size()){
            throw new IndiceInvalidoException(">>> Índice inválido.");
        }
        int opCodeInsert = 0;
        checkSize(opCodeInsert);

        for (int i = size(); i > r; i--){
            this.array[i] = this.array[i-1];
            this.array[i].setIdx(i);
        }

        this.array[r] = new SeqArrayPosition(o, r);
        this.size++;
    }
    public Object removeAtRank(int r){
        if (r < 0 || r >= size()){
            throw new IndiceInvalidoException(">>> Índice inválido.");
        }
        int opCodeRemove = 1;

        Object toRemove = this.array[r].getElement();

        for (int i = r; i < size()-1; i++){
            this.array[i] = this.array[i+1];
            this.array[i].setIdx(i);
        }

        size--;
        this.array[size()] = null;
        checkSize(opCodeRemove);

        return toRemove;
    }
    public Object replaceAtRank(int r, Object o){
        if (r < 0 || r >= size()){
            throw new IndiceInvalidoException(">>> Índice inválido.");
        }

        SeqArrayPosition pos = (SeqArrayPosition) atRank(r);
        Object toRemove = pos.getElement();
        pos.setElement(o);

        return toRemove;
    }
    public Object elemAtRank(int r){
        return atRank(r).getElement();
    }
    public Object first(){
        return atRank(0).getElement();
    }
    public Object last(){
        return atRank(size()-1).getElement();
    }
    public Object before(Position p){
        SeqArrayPosition pos = (SeqArrayPosition) checkPosition(p);
        int idx = pos.getIdx();

        if (idx == 0){
            throw new IndiceInvalidoException(">>> Não há elemento anterior ao primeiro elemento.");
        }

        return array[idx-1].getElement();
    }
    public Object after(Position p){
        SeqArrayPosition pos = (SeqArrayPosition) checkPosition(p);
        int idx = pos.getIdx();

        if (idx >= size()-1){
            throw new IndiceInvalidoException(">> Não há elemento posterior ao último elemento.");
        }

        return array[idx+1].getElement();
    }
    public Object replaceElement(Position p, Object o){
        SeqArrayPosition pos = (SeqArrayPosition) checkPosition(p);
        Object toRemove = pos.getElement();
        pos.setElement(o);

        return toRemove;
    }
    public void swapElement(Position p, Position q){
        SeqArrayPosition pos1 = (SeqArrayPosition) checkPosition(p);
        SeqArrayPosition pos2 = (SeqArrayPosition) checkPosition(q);

        Object pos1Element = pos1.getElement();
        Object pos2Element = pos2.getElement();

        pos1.setElement(pos2Element);
        pos2.setElement(pos1Element);
    }
    public void insertBefore (Position p, Object o){
        SeqArrayPosition pos = (SeqArrayPosition) checkPosition(p);
        insertAtRank(pos.getIdx(), o);
    }
    public void insertAfter (Position p, Object o){
        SeqArrayPosition pos = (SeqArrayPosition) checkPosition(p);
        insertAtRank(pos.getIdx()+1, o);
    }
    public void insertFirst(Object o){
        insertAtRank(0, o);
    }
    public void insertLast(Object o){
        insertAtRank(size(), o);
    }
    public void remove(Position p){
        SeqArrayPosition pos = (SeqArrayPosition) checkPosition(p);
        removeAtRank(pos.getIdx());
    }
    public boolean isEmpty(){
        return size() == 0;
    }
    public int size(){
        return this.size;
    }
}
