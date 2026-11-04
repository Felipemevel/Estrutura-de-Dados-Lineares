package testes;

import implementacao.ArvoreHeapNode;

public class ArvoreHeapNodeTeste {

    public static void main(String[] args) {

        ArvoreHeapNode heapNode = new ArvoreHeapNode();

        heapNode.size();
        heapNode.insert(5, "Felipe");
        heapNode.insert(7, "Natália");
        heapNode.insert(13, "Coleguinha");
        heapNode.showHeap();

        System.out.println("\n\n\n");

        heapNode.insert(1, "Gustavo");
        heapNode.showHeap();

        System.out.println("\n\n\n");

        heapNode.insert(4, "Cairo");
        heapNode.insert(12, "Jhonata");
        heapNode.showHeap();

        System.out.println("\n\n\n");

        heapNode.insert(2, "Guilherme");
        heapNode.showHeap();

        System.out.println("\n\n\n");

        heapNode.removeMin();
        heapNode.showHeap();
    }
}