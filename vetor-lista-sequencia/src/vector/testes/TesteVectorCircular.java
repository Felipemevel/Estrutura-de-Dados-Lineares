package vector.testes;

import vector.VectorCircular;

import vector.exceptions.IndiceInvalidoException;
import vector.exceptions.VectorCheioException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TesteVectorCircular {

    static List<String> falhas = new ArrayList<>();
    static int totalTestes = 0;

    public static void main(String[] args) {

        teste("Construtor VectorCircular()", TesteVectorCircular::testeConstrutorPadrao);

        teste("Inserção O(1) no início (r=0) - Recuo do Head", TesteVectorCircular::testeInsertInicioCircular);
        teste("Inserção O(1) no final (r=size) - Avanço simples", TesteVectorCircular::testeInsertFinal);
        teste("Inserção O(n) no meio", TesteVectorCircular::testeInsertMeio);

        teste("Remoção O(1) no início (r=0) - Avanço do Head", TesteVectorCircular::testeRemoveInicio);
        teste("Remoção O(1) no final (r=size-1) - Limpeza simples", TesteVectorCircular::testeRemoveFinal);
        teste("Remoção O(n) no meio", TesteVectorCircular::testeRemoveMeio);

        teste("replaceAtRank e elemAtRank usando o mapeamento circular", TesteVectorCircular::testeAcessoMapeado);

        teste("Exceções de limites", TesteVectorCircular::testeExcecoes);

        teste("Gatilho de Crescimento (Dobrar Capacidade) e Linearização", TesteVectorCircular::testeCrescimento);
        teste("Gatilho de Encolhimento", TesteVectorCircular::testeEncolhimento);

        resumoFinal();
    }

    static void testeConstrutorPadrao() throws Exception {
        VectorCircular v = new VectorCircular();
        checkEquals("capacity inicial", 5, getInt(v, "capacity"));
        checkEquals("size inicial", 0, getInt(v, "size"));
        checkEquals("head inicial", 0, getInt(v, "head"));
    }

    static void testeInsertInicioCircular() throws Exception {
        VectorCircular v = new VectorCircular(5);
        v.insertAtRank(0, "A");
        v.insertAtRank(0, "B");
        printEstado(v);

        checkEquals("size após 2 inserções", 2, v.size());
        checkEquals("head recuou para o índice 4", 4, getInt(v, "head"));
        checkEquals("array físico [4] deve ser B", "B", getArray(v)[4]);
        checkEquals("array físico [0] deve ser A", "A", getArray(v)[0]);
    }

    static void testeInsertFinal() throws Exception {
        VectorCircular v = new VectorCircular(5);
        v.insertAtRank(0, "Z");
        v.insertAtRank(1, "Y");
        printEstado(v);

        checkEquals("array físico [1] deve ser Y", "Y", getArray(v)[1]);
        checkEquals("head não deve ser alterado", 0, getInt(v, "head"));
    }

    static void testeInsertMeio() throws Exception {
        VectorCircular v = new VectorCircular(5);
        v.insertAtRank(0, "X");
        v.insertAtRank(1, "Z");
        v.insertAtRank(1, "Y");
        printEstado(v);

        checkEquals("size deve ser 3", 3, v.size());
        checkEquals("elemAtRank(1) deve ser Y", "Y", v.elemAtRank(1));
        checkEquals("elemAtRank(2) deve ser Z", "Z", v.elemAtRank(2));
    }

    static void testeRemoveInicio() throws Exception {
        VectorCircular v = new VectorCircular(5);
        v.insertAtRank(0, "A");
        v.insertAtRank(1, "B");
        Object removido = v.removeAtRank(0);
        printEstado(v);

        checkEquals("Objeto removido", "A", removido);
        checkEquals("Head deve avançar para 1", 1, getInt(v, "head"));
        checkEquals("Antigo head no array físico deve ser null", null, getArray(v)[0]);
        checkEquals("size diminuiu", 1, v.size());
    }

    static void testeRemoveFinal() throws Exception {
        VectorCircular v = new VectorCircular(5);
        v.insertAtRank(0, "A");
        v.insertAtRank(1, "B");
        Object removido = v.removeAtRank(1);
        printEstado(v);

        checkEquals("Objeto removido", "B", removido);
        checkEquals("Último espaço físico deve ser null", null, getArray(v)[1]);
    }

    static void testeRemoveMeio() throws Exception {
        VectorCircular v = new VectorCircular(5);
        v.insertAtRank(0, "A");
        v.insertAtRank(1, "B");
        v.insertAtRank(2, "C");
        Object removido = v.removeAtRank(1);
        printEstado(v);

        checkEquals("Objeto removido", "B", removido);
        checkEquals("elemAtRank(1) agora deve ser C", "C", v.elemAtRank(1));
    }

    static void testeAcessoMapeado() throws Exception {
        VectorCircular v = new VectorCircular(5);
        v.insertAtRank(0, "A");
        v.insertAtRank(0, "B");
        v.insertAtRank(0, "C");

        checkEquals("elemAtRank(0)", "C", v.elemAtRank(0));

        Object substituido = v.replaceAtRank(0, "NovoC");
        checkEquals("Objeto substituido", "C", substituido);
        checkEquals("elemAtRank(0) atualizado", "NovoC", v.elemAtRank(0));
    }

    static void testeExcecoes() throws Exception {
        VectorCircular v = new VectorCircular(5);
        checkExcecao("remove em vetor vazio", VectorCheioException.class, () -> v.removeAtRank(0));
        v.insertAtRank(0, "A");
        checkExcecao("remove r < 0", IndiceInvalidoException.class, () -> v.removeAtRank(-1));
        checkExcecao("remove r >= size", IndiceInvalidoException.class, () -> v.removeAtRank(1));
        checkExcecao("insert r < 0", IndiceInvalidoException.class, () -> v.insertAtRank(-1, "X"));
        checkExcecao("insert r > size", IndiceInvalidoException.class, () -> v.insertAtRank(2, "X"));
    }

    static void testeCrescimento() throws Exception {
        VectorCircular v = new VectorCircular(3);
        v.insertAtRank(0, "1");
        v.insertAtRank(0, "2");
        v.insertAtRank(0, "3");

        System.out.println("Vetor circular cheio (head no final do array):");
        printEstado(v);

        v.insertAtRank(3, "4");
        System.out.println("Vetor após crescimento (deve linearizar e resetar head para 0):");
        printEstado(v);

        checkEquals("Nova capacidade", 6, getInt(v, "capacity"));
        checkEquals("Head resetado para 0", 0, getInt(v, "head"));
        checkEquals("array[0] físico", "3", getArray(v)[0]);
        checkEquals("array[1] físico", "2", getArray(v)[1]);
        checkEquals("array[2] físico", "1", getArray(v)[2]);
        checkEquals("array[3] físico", "4", getArray(v)[3]);
    }

    static void testeEncolhimento() throws Exception {
        VectorCircular v = new VectorCircular(10);
        v.insertAtRank(0, "A");
        v.insertAtRank(1, "B");
        v.insertAtRank(2, "C");
        v.removeAtRank(2);
        v.removeAtRank(1);

        printEstado(v);

        checkEquals("Nova capacidade reduziu", 5, getInt(v, "capacity"));
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

    static void printEstado(VectorCircular v) throws Exception {
        Object[] array = getArray(v);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                sb.append(String.format("[%02d:     ] ", i));
            } else {
                sb.append(String.format("[%02d:  %s  ] ", i, array[i]));
            }
            if ((i + 1) % 5 == 0) sb.append("\n");
        }
        System.out.print(sb.toString());
        if(array.length % 5 != 0) System.out.println();
        System.out.println("   size=" + getInt(v, "size")
                + "  capacity=" + getInt(v, "capacity")
                + "  head=" + getInt(v, "head"));
    }

    static Object[] getArray(VectorCircular v) throws Exception {
        Field f = VectorCircular.class.getDeclaredField("array");
        f.setAccessible(true);
        return (Object[]) f.get(v);
    }

    static int getInt(VectorCircular v, String campo) throws Exception {
        Field f = VectorCircular.class.getDeclaredField(campo);
        f.setAccessible(true);
        return f.getInt(v);
    }
}