package filas;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Testando FilaArray (Operações Básicas) ---");
        FilaArray filaStr = new FilaArray(5);

        System.out.println("Fila está vazia? " + filaStr.isEmpty());

        filaStr.queue("Primeiro");
        filaStr.queue("Segundo");
        filaStr.queue("Terceiro");

        System.out.println("Tamanho atual: " + filaStr.size());
        System.out.println("Elemento na frente: " + filaStr.front());
        System.out.println("Fila está vazia? " + filaStr.isEmpty());

        System.out.println("\n--- Testando Remoção (Dequeue) ---");
        System.out.println("Removendo: " + filaStr.dequeue());
        System.out.println("Novo elemento na frente: " + filaStr.front());
        System.out.println("Tamanho após remoção: " + filaStr.size());

        System.out.println("\n--- Testando Comportamento Circular e Limites ---");
        FilaArray filaCircular = new FilaArray(3);

        filaCircular.queue(10);
        filaCircular.queue(20);
        filaCircular.queue(30);
        System.out.println("Tentando adicionar em fila cheia (limite de 3):");
        filaCircular.queue(40); // Deve estourar limite

        System.out.println("\nRemovendo dois elementos para liberar espaço no início do array...");
        System.out.println("Removido: " + filaCircular.dequeue());
        System.out.println("Removido: " + filaCircular.dequeue());

        System.out.println("\nAdicionando novos elementos (o ponteiro 'rear' deve dar a volta)...");
        filaCircular.queue(40);
        filaCircular.queue(50);

        System.out.println("Tamanho atual: " + filaCircular.size());
        System.out.println("Elemento na frente: " + filaCircular.front());

        System.out.println("\n--- Esvaziando a Fila ---");
        while (filaCircular.size() > 0) {
            System.out.println("Removendo: " + filaCircular.dequeue());
        }

        System.out.println("\nTentando remover de fila vazia:");
        filaCircular.dequeue(); // Deve acusar vazia

        System.out.println("--- Testando Fila Ligada Simples ---");

        // Iniciando com o construtor vazio
        FilaLigadaSimples filaLigada = new FilaLigadaSimples();

        System.out.println("A fila ligada está vazia? " + filaLigada.isEmpty());

        System.out.println("\nInserindo elementos base...");
        // O código vai quebrar nesta primeira inserção!
        filaLigada.queue("MIPS");
        filaLigada.queue("Assembly");
        filaLigada.queue("Python");

        System.out.println("Tamanho da fila ligada: " + filaLigada.size());
        System.out.println("Frente atual: " + filaLigada.front());

        System.out.println("\n--- Testando Remoção (Dequeue) ---");
        System.out.println("Removendo: " + filaLigada.dequeue());
        System.out.println("Nova frente: " + filaLigada.front());
        System.out.println("Tamanho após o dequeue: " + filaLigada.size());

        System.out.println("\n--- Esvaziando a Fila Ligada ---");
        while (!filaLigada.isEmpty()) {
            System.out.println("Removendo o nó contendo: " + filaLigada.dequeue());
        }

        System.out.println("Tamanho final: " + filaLigada.size());
    }
}