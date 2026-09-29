package sequencia;

import node.NodePosition;
import sequencia.Exceptions.IndiceInvalidoException;
import sequencia.Exceptions.PositionInvalidoException;
import sequencia.Exceptions.SequenciaVaziaException;

public class SequenciaPosition implements SequenciaInterface{

    private NodePosition sentinel;

    public SequenciaPosition(){
        this.sentinel = new NodePosition(0);

        this.sentinel.setNext(this.sentinel);
        this.sentinel.setPrev(this.sentinel);
    }

    private NodePosition checkPosition(Position p){
        if (!(p instanceof NodePosition)){
            throw new PositionInvalidoException(">>> O elemento informado não é válido.");
        }

        NodePosition pos = (NodePosition) p;

        if (pos == this.sentinel || pos.getPrev() == null
                || pos.getNext() == null) {
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
        NodePosition current = this.sentinel.getNext();

        for (int i = 0; i < r; i++){
            current = current.getNext();
        }

        return current;
    }

    public int rankOf(Position p){
        NodePosition pos = checkPosition(p);
        NodePosition current = this.sentinel.getNext();

        for (int i = 0; i < size(); i++){
            if (current == pos){
                return i;
            }
            current = current.getNext();
        }
        throw new PositionInvalidoException(">>> Elemento não encontrado.");
    }
    @Override
    public void insertAtRank(int r, Object o){
        if (r < 0 || r > size()){
            throw new IndiceInvalidoException(">>> Índice inválido.");
        }

        NodePosition current;
        if (r < (size()/2)){
            current = this.sentinel.getNext();
            for (int i = 0; i < r; i++){
                current = current.getNext();
            }
        } else {
            current = this.sentinel.getPrev();
            for (int i = size()-1; i > r; i--){
                current = current.getPrev();
            }
        }

        NodePosition newNode = new NodePosition(o);
        newNode.setNext(current);
        newNode.setPrev(current.getPrev());

        current.getPrev().setNext(newNode);
        current.setPrev(newNode);

        this.sentinel.setElement((Integer)(this.sentinel.getElement()) + 1);
    }
    @Override
    public Object removeAtRank(int r){
        Position p = atRank(r);
        return remove(p);
    }
    @Override
    public Object replaceAtRank(int r, Object o){
        Position pos = atRank(r);
        Object toRemove = pos.getElement();
        replaceElement(pos, o);

        return toRemove;
    }
    @Override
    public Object elemAtRank(int r){
        validateRank(r);
        return atRank(r).getElement();
    }
    @Override
    public Object before(Position p){
        NodePosition pos = checkPosition(p);

        if (pos.getPrev() == this.sentinel){
            throw new IndiceInvalidoException(">>> O sentinela não é válido.");
        }

        return pos.getPrev().getElement();
    }
    @Override
    public Object after(Position p){
        NodePosition pos = checkPosition(p);

        if (pos.getNext() == this.sentinel){
            throw new IndiceInvalidoException(">>> O sentinela não é válido.");
        }

        return pos.getNext().getElement();
    }
    @Override
    public Object replaceElement(Position p, Object o){
        NodePosition pos = checkPosition(p);
        Object toRemove = pos.getElement();
        pos.setElement(o);

        return toRemove;
    }
    @Override
    public void swapElement(Position p, Position q){
        Object pElement = p.getElement();

        replaceElement(p, q.getElement());
        replaceElement(q, pElement);
    }
    @Override
    public void insertBefore(Position p, Object o){
        NodePosition current = checkPosition(p);
        NodePosition newNode = new NodePosition(o);

        newNode.setNext(current);
        newNode.setPrev(current.getPrev());

        current.getPrev().setNext(newNode);
        current.setPrev(newNode);

        this.sentinel.setElement((Integer)(this.sentinel.getElement()) + 1);
    }
    @Override
    public void insertAfter(Position p, Object o){
        NodePosition current = checkPosition(p);
        NodePosition newNode = new NodePosition(o);

        newNode.setPrev(current);
        newNode.setNext(current.getNext());

        current.getNext().setPrev(newNode);
        current.setNext(newNode);

        this.sentinel.setElement((Integer)(this.sentinel.getElement())+1);
    }
    @Override
    public void insertFirst(Object o){
        insertAtRank(0, o);
    }
    @Override
    public void insertLast(Object o){
        insertAtRank(size(), o);
    }
    @Override
    public Object remove(Position p){
        NodePosition pos = checkPosition(p);

        pos.getPrev().setNext(pos.getNext());
        pos.getNext().setPrev(pos.getPrev());

        pos.setNext(null);
        pos.setPrev(null);

        this.sentinel.setElement((Integer)(this.sentinel.getElement()) - 1);

        return pos.getElement();
    }
    @Override
    public Object last(){
        if (isEmpty()) {
            throw new SequenciaVaziaException(">>> Sequência vazia.");
        }
        return this.sentinel.getPrev().getElement();
    }
    public Object first(){
        if (isEmpty()) {
            throw new SequenciaVaziaException(">>> Sequência vazia.");
        }
        return this.sentinel.getNext().getElement();
    }
    @Override
    public int size(){
        return (Integer) this.sentinel.getElement();
    }
    @Override
    public boolean isEmpty(){
        return size()==0;
    }
}
