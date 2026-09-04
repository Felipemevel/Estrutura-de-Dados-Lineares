package vector.testes;

import vector.VectorArray;
import vector.exceptions.VectorCheioException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TesteVectorArray {

    static List<String> falhas = new ArrayList<>();
    static int totalTestes = 0;

    public static void main(String[] args) {

        teste("Construtor Vector() (capacidade padrao)", TesteVectorArray::testeConstrutorPadrao);
        teste("Construtor Vector(int capacity)", TesteVectorArray::testeConstrutorComCapacidade);

        teste("size() / isEmpty() em vetor vazio", TesteVectorArray::testeVetorVazio);

        teste("insertAtRank(int, Object) - insercao simples e efeito no array", TesteVectorArray::testeInsertAtRankSimples);
        teste("insertAtRank(int, Object) - insercao com deslocamento", TesteVectorArray::testeInsertAtRankDeslocamento);

        teste("replaceAtRank(int, Object) - substituicao e retorno do antigo", TesteVectorArray::testeReplaceAtRank);

        teste("elemAtRank(int) - retorno do elemento correto", TesteVectorArray::testeElemAtRank);

        teste("removeAtRank(int) - remocao e deslocamento", TesteVectorArray::testeRemoveAtRank);

        teste("removeAtRank(int) em vetor vazio -> deve lancar excecao", TesteVectorArray::testeRemoveVazia);

        teste("Crescimento dinamico via insertAtRank ate exceder capacidade", TesteVectorArray::testeCrescimento);

        teste("Encolhimento dinamico via removeAtRank", TesteVectorArray::testeEncolhimento);

        resumoFinal();
    }

    static void testeConstrutorPadrao() throws Exception {
        VectorArray v = new VectorArray();
        printEstado(v);
        checkEquals("Vector() -> capacity interno", 5, getInt(v, "capacity"));
        checkEquals("Vector() -> array.length", 5, getArray(v).length);
        checkEquals("Vector() -> size inicial", 0, getInt(v, "size"));
    }

    static void testeConstrutorComCapacidade() throws Exception {
        VectorArray v = new VectorArray(15);
        printEstado(v);
        checkEquals("Vector(15) -> capacity interno", 15, getInt(v, "capacity"));
        checkEquals("Vector(15) -> array.length", 15, getArray(v).length);
        checkEquals("Vector(15) -> size inicial", 0, getInt(v, "size"));
    }

    static void testeVetorVazio() throws Exception {
        VectorArray v = new VectorArray(6);
        printEstado(v);
        checkEquals("size() com vetor vazio", 0, v.size());
        checkEquals("isEmpty() com vetor vazio", true, v.isEmpty());
    }

    static void testeInsertAtRankSimples() throws Exception {
        VectorArray v = new VectorArray(10);
        v.insertAtRank(0, "A");
        printEstado(v);
        checkEquals("insertAtRank(0, 'A') -> array[0]", "A", getArray(v)[0]);
        checkEquals("insertAtRank(0, 'A') -> size", 1, v.size());
    }

    static void testeInsertAtRankDeslocamento() throws Exception {
        VectorArray v = new VectorArray(10);
        v.insertAtRank(0, "B");
        v.insertAtRank(0, "A");
        printEstado(v);
        checkEquals("insertAtRank(0, 'A') deslocou 'B' -> array[0]", "A", getArray(v)[0]);
        checkEquals("insertAtRank(0, 'A') deslocou 'B' -> array[1]", "B", getArray(v)[1]);
        checkEquals("size apos 2 insercoes", 2, v.size());
    }

    static void testeReplaceAtRank() throws Exception {
        VectorArray v = new VectorArray(10);
        v.insertAtRank(0, "A");
        Object antigo = v.replaceAtRank(0, "B");
        printEstado(v);
        checkEquals("replaceAtRank(0, 'B') -> retornou o antigo", "A", antigo);
        checkEquals("replaceAtRank(0, 'B') -> array[0] atualizado", "B", getArray(v)[0]);
    }

    static void testeElemAtRank() throws Exception {
        VectorArray v = new VectorArray(10);
        v.insertAtRank(0, "X");
        v.insertAtRank(1, "Y");
        printEstado(v);
        checkEquals("elemAtRank(1)", "Y", v.elemAtRank(1));
    }

    static void testeRemoveAtRank() throws Exception {
        VectorArray v = new VectorArray(10);
        v.insertAtRank(0, "X");
        v.insertAtRank(1, "Y");
        v.insertAtRank(2, "Z");
        printEstado(v);
        Object removido = v.removeAtRank(1);
        printEstado(v);
        checkEquals("removeAtRank(1) -> retornou", "Y", removido);
        checkEquals("removeAtRank(1) -> deslocou 'Z' para indice 1", "Z", getArray(v)[1]);
        checkEquals("size apos remocao", 2, v.size());
    }

    static void testeRemoveVazia() throws Exception {
        VectorArray v = new VectorArray(10);
        printEstado(v);
        checkExcecao("removeAtRank(0) em vetor vazio", VectorCheioException.class, () -> v.removeAtRank(0));
    }

    static void testeCrescimento() throws Exception {
        VectorArray v = new VectorArray(3);
        v.insertAtRank(0, "1");
        v.insertAtRank(1, "2");
        v.insertAtRank(2, "3");
        printEstado(v);
        System.out.println("Vetor de capacidade 3 esta cheio. Testando o gatilho de crescimento com a 4a insercao:");
        v.insertAtRank(3, "4");
        printEstado(v);
        checkEquals("capacity() apos crescimento (3 * 2)", 6, getInt(v, "capacity"));
        checkEquals("size() apos crescimento", 4, v.size());
    }

    static void testeEncolhimento() throws Exception {
        VectorArray v = new VectorArray(12);
        v.insertAtRank(0, "A");
        v.insertAtRank(1, "B");
        v.insertAtRank(2, "C");
        v.insertAtRank(3, "D");
        printEstado(v);
        System.out.println("Removendo elementos para acionar o encolhimento (size <= capacity/3):");
        v.removeAtRank(3);
        v.removeAtRank(2);
        v.removeAtRank(1);
        printEstado(v);
        checkEquals("capacity() apos encolher (12 / 2)", 6, getInt(v, "capacity"));
        checkEquals("size() apos os pops", 1, v.size());
    }

    interface TesteFn {
        void run() throws Exception;
    }

    static void teste(String nomeMetodoTestado, TesteFn corpo) {
        totalTestes++;
        System.out.println();
        System.out.println("======================================================================");
        System.out.println("TESTE " + totalTestes + " -> " + nomeMetodoTestado);
        System.out.println("======================================================================");
        try {
            corpo.run();
        } catch (Throwable t) {
            System.out.println(">>> ERRO: o teste do metodo [" + nomeMetodoTestado + "] quebrou com uma excecao"
                    + " que NAO era esperada nesse ponto: " + t);
            falhas.add(nomeMetodoTestado + " -> excecao inesperada: " + t);
        }
    }

    static void checkEquals(String descricaoDoQueEstaSendoTestado, Object esperado, Object obtido) {
        boolean igual = (esperado == null && obtido == null)
                || (esperado != null && esperado.equals(obtido));
        if (igual) {
            System.out.println("[PASSOU] " + descricaoDoQueEstaSendoTestado);
        } else {
            String msg = "[FALHOU] " + descricaoDoQueEstaSendoTestado
                    + " -> esperado=[" + esperado + "]  obtido=[" + obtido + "]";
            System.out.println(msg);
            falhas.add(descricaoDoQueEstaSendoTestado + ": esperado=[" + esperado + "], obtido=[" + obtido + "]");
        }
    }

    static void checkExcecao(String descricaoDoQueEstaSendoTestado, Class<? extends Throwable> esperada, TesteFn acao) {
        try {
            acao.run();
            String msg = "[FALHOU] " + descricaoDoQueEstaSendoTestado
                    + " -> esperava que lancasse " + esperada.getSimpleName() + ", mas nao lancou nada";
            System.out.println(msg);
            falhas.add(descricaoDoQueEstaSendoTestado + ": esperava " + esperada.getSimpleName() + ", nao lancou nada");
        } catch (Throwable t) {
            if (esperada.isInstance(t)) {
                System.out.println("[PASSOU] " + descricaoDoQueEstaSendoTestado
                        + " -> lancou " + t.getClass().getSimpleName() + " (\"" + t.getMessage() + "\")");
            } else {
                String msg = "[FALHOU] " + descricaoDoQueEstaSendoTestado
                        + " -> esperava " + esperada.getSimpleName() + ", mas lancou " + t;
                System.out.println(msg);
                falhas.add(descricaoDoQueEstaSendoTestado + ": esperava " + esperada.getSimpleName() + ", mas lancou " + t);
            }
        }
    }

    static void resumoFinal() {
        System.out.println();
        System.out.println("======================================================================");
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

    static void printEstado(VectorArray v) throws Exception {
        Object[] array = getArray(v);
        StringBuilder sb = new StringBuilder();
        for (Object o : array) {
            if (o == null) {
                sb.append("[   ] ");
            } else {
                sb.append(String.format("[ %s ] ", o));
            }
        }
        System.out.println(sb.toString());
        System.out.println("   size=" + getInt(v, "size")
                + "  capacity=" + getInt(v, "capacity")
                + "  array.length=" + array.length);
    }

    static Object[] getArray(VectorArray v) throws Exception {
        Field f = VectorArray.class.getDeclaredField("array");
        f.setAccessible(true);
        return (Object[]) f.get(v);
    }

    static int getInt(VectorArray v, String campo) throws Exception {
        Field f = VectorArray.class.getDeclaredField(campo);
        f.setAccessible(true);
        return f.getInt(v);
    }
}