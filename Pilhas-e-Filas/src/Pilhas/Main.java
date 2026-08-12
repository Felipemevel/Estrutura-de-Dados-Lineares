package Pilhas;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Testando Pilha de Strings ---");
        PilhaArray pilhaStr = new PilhaArray(3);

        System.out.println("Está vazia? " + pilhaStr.isEmpty());
        pilhaStr.push("Java");
        pilhaStr.push("Spring Boot");
        pilhaStr.push("Linux");

        System.out.println("Tamanho: " + pilhaStr.size());
        System.out.println("Topo da pilha: " + pilhaStr.top());

        System.out.println("Removendo: " + pilhaStr.pop());
        System.out.println("Novo topo: " + pilhaStr.top());
        System.out.println("Tamanho após pop: " + pilhaStr.size());

        System.out.println("\n--- Testando Pilha de Integers ---");
        PilhaArray pilhaInt = new PilhaArray(2);

        pilhaInt.push(10);
        pilhaInt.push(20);
        pilhaInt.push(30);

        System.out.println("Topo da pilha int: " + pilhaInt.top());
        System.out.println("Removendo: " + pilhaInt.pop());
        System.out.println("Removendo: " + pilhaInt.pop());
        System.out.println("Removendo com pilha vazia: " + pilhaInt.pop());
        System.out.println("Está vazia? " + pilhaInt.isEmpty());
    }
}