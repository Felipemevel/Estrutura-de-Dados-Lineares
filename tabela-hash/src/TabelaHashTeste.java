public class TabelaHashTeste {

    private static void mostrar(TabelaHash tabela) {
        int[] chaves = tabela.keys();
        Object[] elementos = tabela.elements();

        System.out.println("size = " + tabela.size() + " | isEmpty = " + tabela.isEmpty());
        for (int i = 0; i < chaves.length; i++) {
            System.out.println("  [" + chaves[i] + "] -> " + elementos[i]);
        }
    }

    public static void main(String[] args) {

        // ---------- Tabela vazia ----------
        TabelaHash tabela = new TabelaHash();
        System.out.println("=== Tabela vazia ===");
        mostrar(tabela);
        System.out.println(tabela.findElement(5));
        System.out.println(tabela.removeElement(5));
        System.out.println("\n\n\n");

        // ---------- Inserção com colisões (capacidade 13: 5, 18, 31 -> mesma posição 5) ----------
        System.out.println("=== Inserção com colisões ===");
        tabela.insertItem(5, "Felipe");
        tabela.insertItem(18, "Natália");
        tabela.insertItem(31, "Coleguinha");
        tabela.insertItem(7, "Gustavo");
        tabela.insertItem(20, "Cairo");
        mostrar(tabela);
        System.out.println("\n\n\n");

        // ---------- Busca ----------
        System.out.println("=== Busca ===");
        System.out.println("findElement(5)  = " + tabela.findElement(5));
        System.out.println("findElement(18) = " + tabela.findElement(18));
        System.out.println("findElement(31) = " + tabela.findElement(31));
        System.out.println("findElement(20) = " + tabela.findElement(20));
        System.out.println("findElement(99) = " + tabela.findElement(99));
        System.out.println("\n\n\n");

        // ---------- Remoção (deixa AVAILABLE na posição 5 e a busca precisa passar por ela) ----------
        System.out.println("=== Remoção ===");
        System.out.println("removeElement(5)  = " + tabela.removeElement(5));
        System.out.println("removeElement(5)  = " + tabela.removeElement(5));
        System.out.println("findElement(5)    = " + tabela.findElement(5));
        System.out.println("findElement(18)   = " + tabela.findElement(18));
        System.out.println("findElement(31)   = " + tabela.findElement(31));
        mostrar(tabela);
        System.out.println("\n\n\n");

        // ---------- Reinserção (deve reaproveitar a posição AVAILABLE) ----------
        System.out.println("=== Reinserção após remoção ===");
        tabela.insertItem(44, "Jhonata");
        mostrar(tabela);
        System.out.println("findElement(44) = " + tabela.findElement(44));
        System.out.println("\n\n\n");

        // ---------- Rehash (fator de carga >= 0.5 com capacidade 13 -> capacidade 26) ----------
        System.out.println("=== Rehash ===");
        tabela.insertItem(2, "Guilherme");
        tabela.insertItem(9, "Anna");
        mostrar(tabela);
        System.out.println("findElement(18) = " + tabela.findElement(18));
        System.out.println("findElement(31) = " + tabela.findElement(31));
        System.out.println("findElement(44) = " + tabela.findElement(44));
        System.out.println("\n\n\n");

        // ---------- Chave negativa ----------
        System.out.println("=== Chave negativa ===");
        tabela.insertItem(-7, "Negativo");
        System.out.println("findElement(-7) = " + tabela.findElement(-7));
        System.out.println("removeElement(-7) = " + tabela.removeElement(-7));
        System.out.println("\n\n\n");

        // ---------- Capacidade mínima (3) e vários rehashes seguidos ----------
        System.out.println("=== Capacidade pequena ===");
        TabelaHash pequena = new TabelaHash(1);
        for (int i = 1; i <= 10; i++) {
            pequena.insertItem(i * 3, "Item" + i);
        }
        mostrar(pequena);
        for (int i = 1; i <= 10; i++) {
            System.out.println("findElement(" + (i * 3) + ") = " + pequena.findElement(i * 3));
        }
        System.out.println("\n\n\n");

        // ---------- Esvaziando a tabela ----------
        System.out.println("=== Esvaziando ===");
        for (int i = 1; i <= 10; i++) {
            pequena.removeElement(i * 3);
        }
        mostrar(pequena);
    }
}