package vector.testes;

import vector.VectorLigadoDuplo;
import vector.exceptions.IndiceInvalidoException;
import vector.exceptions.VectorVazioException;

import java.util.ArrayList;
import java.util.List;

public class Main {

    static List<String> falhas = new ArrayList<>();
    static int totalTestes = 0;

    public static void main(String[] args) {
        teste("Construtor Vazio", Main::testeConstrutorVazio);
        teste("Construtor com Elemento", Main::testeConstrutorComElemento);
        teste("Inserção (insertAtRank)", Main::testeInsert);
        teste("Acesso (elemAtRank)", Main::testeElemAtRank);
        teste("Substituição (replaceAtRank)", Main::testeReplace);
        teste("Remoção (removeAtRank)", Main::testeRemove);
        teste("Exceções", Main::testeExcecoes);

        resumoFinal();
    }

    static void testeConstrutorVazio() throws Exception {
        VectorLigadoDuplo v = new VectorLigadoDuplo();
        checkEquals("Size inicial", 0, v.size());
        checkEquals("Vetor isEmpty", true, v.isEmpty());
    }

    static void testeConstrutorComElemento() throws Exception {
        VectorLigadoDuplo v = new VectorLigadoDuplo("A");
        checkEquals("Size inicial", 1, v.size());
        checkEquals("Vetor isEmpty", false, v.isEmpty());
        checkEquals("Elemento no rank 0", "A", v.elemAtRank(0));
    }

    static void testeInsert() throws Exception {
        VectorLigadoDuplo v = new VectorLigadoDuplo();
        v.insertAtRank(0, "C");
        v.insertAtRank(0, "A");
        v.insertAtRank(1, "B");

        checkEquals("Size após inserções", 3, v.size());
        checkEquals("Rank 0", "A", v.elemAtRank(0));
        checkEquals("Rank 1", "B", v.elemAtRank(1));
        checkEquals("Rank 2", "C", v.elemAtRank(2));
    }

    static void testeElemAtRank() throws Exception {
        VectorLigadoDuplo v = new VectorLigadoDuplo();
        v.insertAtRank(0, "A");
        v.insertAtRank(1, "B");
        v.insertAtRank(2, "C");
        v.insertAtRank(3, "D");

        checkEquals("Rank 0 (O(1))", "A", v.elemAtRank(0));
        checkEquals("Rank 3 (O(1))", "D", v.elemAtRank(3));
        checkEquals("Rank 1 (Esquerda)", "B", v.elemAtRank(1));
        checkEquals("Rank 2 (Direita)", "C", v.elemAtRank(2));
    }

    static void testeReplace() throws Exception {
        VectorLigadoDuplo v = new VectorLigadoDuplo();
        v.insertAtRank(0, "A");
        v.insertAtRank(1, "B");
        v.insertAtRank(2, "C");

        Object removidoFim = v.replaceAtRank(2, "Z");
        checkEquals("Objeto substituido no fim", "C", removidoFim);
        checkEquals("Novo objeto no rank 2", "Z", v.elemAtRank(2));

        Object removidoMeio = v.replaceAtRank(1, "Y");
        checkEquals("Objeto substituido no meio", "B", removidoMeio);
        checkEquals("Novo objeto no rank 1", "Y", v.elemAtRank(1));
    }

    static void testeRemove() throws Exception {
        VectorLigadoDuplo v = new VectorLigadoDuplo();
        v.insertAtRank(0, "A");
        v.insertAtRank(1, "B");
        v.insertAtRank(2, "C");

        Object removido = v.removeAtRank(1);
        checkEquals("Objeto removido", "B", removido);
        checkEquals("Size diminuiu", 2, v.size());
        checkEquals("Rank 1 agora é C", "C", v.elemAtRank(1));
    }

    static void testeExcecoes() throws Exception {
        VectorLigadoDuplo v = new VectorLigadoDuplo();

        checkExcecao("elemAtRank em vetor vazio", VectorVazioException.class, () -> v.elemAtRank(0));
        checkExcecao("removeAtRank em vetor vazio", VectorVazioException.class, () -> v.removeAtRank(0));

        v.insertAtRank(0, "A");

        checkExcecao("insertAtRank negativo", IndiceInvalidoException.class, () -> v.insertAtRank(-1, "X"));
        checkExcecao("insertAtRank além do size", IndiceInvalidoException.class, () -> v.insertAtRank(2, "X"));
        checkExcecao("removeAtRank igual ao size", IndiceInvalidoException.class, () -> v.removeAtRank(1));
    }

    interface TesteFn {
        void run() throws Exception;
    }

    static void teste(String nomeMetodoTestado, TesteFn corpo) {
        totalTestes++;
        System.out.println("\n======================================================================");
        System.out.println("TESTE " + totalTestes + " -> " + nomeMetodoTestado);
        System.out.println("======================================================================");
        try {
            corpo.run();
        } catch (Throwable t) {
            System.out.println(">>> ERRO: Exceção inesperada: " + t);
            falhas.add(nomeMetodoTestado + " -> excecao inesperada: " + t);
        }
    }

    static void checkEquals(String descricao, Object esperado, Object obtido) {
        boolean igual = (esperado == null && obtido == null) || (esperado != null && esperado.equals(obtido));
        if (igual) {
            System.out.println("[PASSOU] " + descricao);
        } else {
            System.out.println("[FALHOU] " + descricao + " -> esperado=[" + esperado + "]  obtido=[" + obtido + "]");
            falhas.add(descricao + ": esperado=[" + esperado + "], obtido=[" + obtido + "]");
        }
    }

    static void checkExcecao(String descricao, Class<? extends Throwable> esperada, TesteFn acao) {
        try {
            acao.run();
            System.out.println("[FALHOU] " + descricao + " -> esperava " + esperada.getSimpleName() + ", mas não lançou nada");
            falhas.add(descricao + ": esperava " + esperada.getSimpleName() + ", não lancou nada");
        } catch (Throwable t) {
            if (esperada.isInstance(t)) {
                System.out.println("[PASSOU] " + descricao + " -> lançou " + t.getClass().getSimpleName());
            } else {
                System.out.println("[FALHOU] " + descricao + " -> esperava " + esperada.getSimpleName() + ", mas lançou " + t);
                falhas.add(descricao + ": esperava " + esperada.getSimpleName() + ", mas lancou " + t);
            }
        }
    }

    static void resumoFinal() {
        System.out.println("\n======================================================================");
        System.out.println("RESUMO FINAL - " + totalTestes + " testes executados, " + falhas.size() + " falha(s)");
        System.out.println("======================================================================");
        if (falhas.isEmpty()) {
            System.out.println("Todos os testes passaram.");
            return;
        }
        for (int i = 0; i < falhas.size(); i++) {
            System.out.println((i + 1) + ") " + falhas.get(i));
        }
    }
}