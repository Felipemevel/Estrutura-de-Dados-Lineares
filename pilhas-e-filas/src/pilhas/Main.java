package pilhas;

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

        System.out.println("\nLINKED STACK ABAIXO!!!!! ==================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================================== LINKED STACK ABAIXO!!!!!\n");

        System.out.println("--- Testando Pilha Ligada Simples ---");
        PilhaLigadaSimples pilhaLigada = new PilhaLigadaSimples();

        System.out.println("A pilha ligada está vazia? " + pilhaLigada.isEmpty());

        System.out.println("\nInserindo elementos base...");
        pilhaLigada.push("MIPS");
        pilhaLigada.push("Assembly");
        pilhaLigada.push("Python");

        System.out.println("Tamanho da pilha ligada: " + pilhaLigada.size());
        System.out.println("Topo atual: " + pilhaLigada.top());

        System.out.println("Removendo: " + pilhaLigada.pop());
        System.out.println("Novo topo: " + pilhaLigada.top());
        System.out.println("Tamanho após o pop: " + pilhaLigada.size());

        System.out.println("\n--- Testando Inserção Dinâmica sem Limites ---");
        for (int i = 1; i <= 5; i++) {
            int valor = i * 100;
            pilhaLigada.push(valor);
            System.out.println("Inseriu: " + valor);
        }

        System.out.println("Novo tamanho após inserções em massa: " + pilhaLigada.size());
        System.out.println("Novo topo: " + pilhaLigada.top());

        System.out.println("\n--- Esvaziando a Pilha Ligada (Teste do Garbage Collector) ---");
        while (!pilhaLigada.isEmpty()) {
            System.out.println("Removendo o nó do topo contendo: " + pilhaLigada.pop());
        }

        System.out.println("Tamanho final: " + pilhaLigada.size());
        System.out.println("Testando segurança em remoção de pilha vazia:");
        pilhaLigada.pop();
    }
}