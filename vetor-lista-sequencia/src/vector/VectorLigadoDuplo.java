package vector;

import node.Node;
import vector.exceptions.IndiceInvalidoException;
import vector.exceptions.VectorVazioException;

public class VectorLigadoDuplo implements VectorInterface {

    private Node sentinel;

    public VectorLigadoDuplo(){
        this.sentinel = new Node(0);

        this.sentinel.setNext(this.sentinel);
        this.sentinel.setPrev(this.sentinel);
    }
    public VectorLigadoDuplo(Object o){
        Node newNode = new Node(o);
        this.sentinel = new Node(1);

        newNode.setPrev(this.sentinel);
        newNode.setNext(this.sentinel);

        this.sentinel.setPrev(newNode);
        this.sentinel.setNext(newNode);
    }
    @Override
    public Object elemAtRank(int r){
        if ((r >= size() && size() != 0) || r < 0){
            throw new IndiceInvalidoException(">>> Índice inválido");
        } else if (size() == 0){
            throw new VectorVazioException(">>> O vetor está vazio.");
        }
        Node current;
        if (r < (size() / 2)) {
            current = sentinel.getNext();
            for (int i = 0; i < r; i++) {
                current = current.getNext();
            }
        } else {
            current = sentinel.getPrev();
            for (int i = size()-1; i > r; i--){
                current = current.getPrev();
            }
        }
        return current.getElement();
    }
    @Override
    public Object replaceAtRank(int r, Object o){
        if (r >= size() || r < 0){
            throw new IndiceInvalidoException(">>> Índice inválido");
        } else if (size() == 0){
            throw new VectorVazioException(">>> O vetor está vazio.");
        }

        Object toRemove;
        Node current;
        if (r < (size() / 2)){
            current = this.sentinel.getNext();
            for (int i = 0; i < r; i++){
                current = current.getNext();
            }
            toRemove = current.getElement();
            current.setElement(o);
        } else {
            current = this.sentinel.getPrev();
            for (int i = size()-1; i > r; i--){
                current = current.getPrev();
            }
            toRemove = current.getElement();
            current.setElement(o);
        }

        return toRemove;
    }
    @Override
    public void insertAtRank(int r, Object o){
        if (r > size() || r < 0){
            throw new IndiceInvalidoException(">>> Índice inválido");
        }

        Node newNode = new Node(o);
        Node temp;
        Node current;
        if (r < (size() / 2)){
            current = this.sentinel.getNext();
            for (int i = 0; i < r; i++){
                current = current.getNext();
            }
        } else {
            current = this.sentinel;
            for (int i = size(); i > r; i--){
                current = current.getPrev();
            }
        }
        temp = current.getPrev();
        temp.setNext(newNode);
        newNode.setPrev(temp);
        current.setPrev(newNode);
        newNode.setNext(current);


        this.sentinel.setElement((Integer)sentinel.getElement()+1);
    }
    @Override
    public Object removeAtRank(int r){
        if ((r >= size() && size() != 0) || r < 0){
            throw new IndiceInvalidoException(">>> Índice inválido");
        } else if (size() == 0){
            throw new VectorVazioException(">>> O vetor já está vazio.");
        }

        Node temp;
        Node current;
        if (r < (size() / 2)){
            current = sentinel.getNext();
            for (int i = 0; i < r; i++){
                current = current.getNext();
            }
        } else {
            current = sentinel;
            for (int i = size(); i > r; i--){
                current = current.getPrev();
            }
        }
        temp = current.getPrev();
        temp.setNext(current.getNext());
        current.getNext().setPrev(temp);

        this.sentinel.setElement((Integer)sentinel.getElement()-1);
        return current.getElement();
    }
    @Override
    public int size(){
        return (Integer) sentinel.getElement();
    }
    @Override
    public boolean isEmpty(){
        return size()==0;
    }

}
