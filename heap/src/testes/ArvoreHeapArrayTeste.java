package testes;

import implementacao.ArvoreHeapArray;

public class ArvoreHeapArrayTeste {

    public static void main(String[] args) {

        ArvoreHeapArray heapArray = new ArvoreHeapArray();

        heapArray.size();
        heapArray.insert(5, "Felipe");
        heapArray.insert(7, "Natália");
        heapArray.insert(13, "Coleguinha");
        heapArray.showHeap();
        System.out.println("\n\n\n");
        heapArray.insert(1, "Gustavo");
        heapArray.showHeap();
        System.out.println("\n\n\n");
        heapArray.insert(4, "Cairo");
        heapArray.insert(12, "Jhonata");
        heapArray.showHeap();
        System.out.println("\n\n\n");
        heapArray.insert(2, "Guilherme");
        heapArray.showHeap();
        System.out.println("\n\n\n");
        heapArray.removeMin();
        heapArray.showHeap();
    }
}
