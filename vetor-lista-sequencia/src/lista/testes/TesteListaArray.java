package lista.testes;

import lista.ListaArray;
import lista.exceptions.ListaCheiaException;
import lista.exceptions.ListaVaziaException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TesteListaArray {

    static List<String> falhas = new ArrayList<>();
    static int totalTestes = 0;

    public static void main(String[] args) {

        teste("Construtor ListaArray() (capacidade padrao)", TesteListaArray::testeConstrutorPadrao);
        teste("Construtor ListaArray(int capacity)", TesteListaArray::testeConstrutorComCapacidade);

        teste("size() / isEmpty() em lista vazia", TesteListaArray::testeListaVazia);

        teste("insertFirst(Object) - insercao simples", TesteListaArray::testeInsertFirstSimples);
        teste("insertFirst(Object) - insercao com deslocamento", TesteListaArray::testeInsertFirstDeslocamento);

        teste("insertLast(Object) - insercao no final", TesteListaArray::testeInsertLast);

        teste("insertBefore(Object, Object) - insercao antes de elemento", TesteListaArray::testeInsertBefore);

        teste("insertAfter(Object, Object) - insercao apos elemento", TesteListaArray::testeInsertAfter);

        teste("replaceElement(Object, Object) - substituicao e retorno do antigo", TesteListaArray::testeReplaceElement);

        teste("swapElements(Object, Object) - troca de elementos", TesteListaArray::testeSwapElements);

        teste("remove(Object) - remocao e deslocamento", TesteListaArray::testeRemove);

        teste("remove(Object) em lista vazia -> deve lancar excecao", TesteListaArray::testeRemoveListaVazia);

        teste("remove(Object) elemento inexistente -> deve lancar excecao", TesteListaArray::testeRemoveElementoInexistente);

        teste("insertFirst com lista cheia -> deve lancar excecao", TesteListaArray::testeInsertFirstListaCheia);

        teste("insertLast com lista cheia -> deve lancar excecao", TesteListaArray::testeInsertLastListaCheia);

        teste("insertBefore elemento inexistente -> deve lancar excecao", TesteListaArray::testeInsertBeforeInexistente);

        teste("insertAfter elemento inexistente -> deve lancar excecao", TesteListaArray::testeInsertAfterInexistente);

        teste("replaceElement elemento inexistente -> deve lancar excecao", TesteListaArray::testeReplaceElementInexistente);

        teste("swapElements com elementos inexistentes -> deve lancar excecao", TesteListaArray::testeSwapElementsInexistente);

        teste("Crescimento dinamico via insertFirst ate exceder capacidade", TesteListaArray::testeCrescimentoInsertFirst);

        teste("Crescimento dinamico via insertLast ate exceder capacidade", TesteListaArray::testeCrescimentoInsertLast);

        teste("Encolhimento dinamico apos multiplas remocoes", TesteListaArray::testeEncolhimento);

        resumoFinal();
    }

    static void testeConstrutorPadrao() throws Exception {
        ListaArray lista = new ListaArray();
        printEstado(lista);
        checkEquals("ListaArray() -> capacity interno", 5, getInt(lista, "capacity"));
        checkEquals("ListaArray() -> array.length", 5, getArray(lista).length);
        checkEquals("ListaArray() -> size inicial", 0, getInt(lista, "size"));
    }

    static void testeConstrutorComCapacidade() throws Exception {
        ListaArray lista = new ListaArray(15);
        printEstado(lista);
        checkEquals("ListaArray(15) -> capacity interno", 15, getInt(lista, "capacity"));
        checkEquals("ListaArray(15) -> array.length", 15, getArray(lista).length);
        checkEquals("ListaArray(15) -> size inicial", 0, getInt(lista, "size"));
    }

    static void testeListaVazia() throws Exception {
        ListaArray lista = new ListaArray(6);
        printEstado(lista);
        checkEquals("size() com lista vazia", 0, lista.size());
        checkEquals("isEmpty() com lista vazia", true, lista.isEmpty());
    }

    static void testeInsertFirstSimples() throws Exception {
        ListaArray lista = new ListaArray(10);
        lista.insertFirst("A");
        printEstado(lista);
        checkEquals("insertFirst('A') -> array[0]", "A", getArray(lista)[0]);
        checkEquals("insertFirst('A') -> size", 1, lista.size());
    }

    static void testeInsertFirstDeslocamento() throws Exception {
        ListaArray lista = new ListaArray(10);
        lista.insertFirst("B");
        lista.insertFirst("A");
        printEstado(lista);
        checkEquals("insertFirst('A') deslocou 'B' -> array[0]", "A", getArray(lista)[0]);
        checkEquals("insertFirst('A') deslocou 'B' -> array[1]", "B", getArray(lista)[1]);
        checkEquals("size apos 2 insercoes", 2, lista.size());
    }

    static void testeInsertLast() throws Exception {
        ListaArray lista = new ListaArray(10);
        lista.insertLast("X");
        lista.insertLast("Y");
        printEstado(lista);
        checkEquals("insertLast('X') -> array[0]", "X", getArray(lista)[0]);
        checkEquals("insertLast('Y') -> array[1]", "Y", getArray(lista)[1]);
        checkEquals("size apos 2 insercoes", 2, lista.size());
    }

    static void testeInsertBefore() throws Exception {
        ListaArray lista = new ListaArray(10);
        lista.insertLast("A");
        lista.insertLast("C");
        lista.insertBefore("C", "B");
        printEstado(lista);
        checkEquals("insertBefore('C', 'B') -> array[0]", "A", getArray(lista)[0]);
        checkEquals("insertBefore('C', 'B') -> array[1]", "B", getArray(lista)[1]);
        checkEquals("insertBefore('C', 'B') -> array[2]", "C", getArray(lista)[2]);
        checkEquals("size apos insercao", 3, lista.size());
    }

    static void testeInsertAfter() throws Exception {
        ListaArray lista = new ListaArray(10);
        lista.insertLast("A");
        lista.insertLast("B");
        lista.insertAfter("A", "X");
        printEstado(lista);
        checkEquals("insertAfter('A', 'X') -> array[0]", "A", getArray(lista)[0]);
        checkEquals("insertAfter('A', 'X') -> array[1]", "X", getArray(lista)[1]);
        checkEquals("insertAfter('A', 'X') -> array[2]", "B", getArray(lista)[2]);
        checkEquals("size apos insercao", 3, lista.size());
    }

    static void testeReplaceElement() throws Exception {
        ListaArray lista = new ListaArray(10);
        lista.insertLast("A");
        Object antigo = lista.replaceElement("A", "B");
        printEstado(lista);
        checkEquals("replaceElement('A', 'B') -> retornou o antigo", "A", antigo);
        checkEquals("replaceElement('A', 'B') -> array[0] atualizado", "B", getArray(lista)[0]);
    }

    static void testeSwapElements() throws Exception {
        ListaArray lista = new ListaArray(10);
        lista.insertLast("X");
        lista.insertLast("Y");
        lista.swapElements("X", "Y");
        printEstado(lista);
        checkEquals("swapElements('X', 'Y') -> array[0]", "Y", getArray(lista)[0]);
        checkEquals("swapElements('X', 'Y') -> array[1]", "X", getArray(lista)[1]);
    }

    static void testeRemove() throws Exception {
        ListaArray lista = new ListaArray(10);
        lista.insertLast("X");
        lista.insertLast("Y");
        lista.insertLast("Z");
        printEstado(lista);
        Object removido = lista.remove("Y");
        printEstado(lista);
        checkEquals("remove('Y') -> retornou", "Y", removido);
        checkEquals("remove('Y') -> deslocou 'Z' para indice 1", "Z", getArray(lista)[1]);
        checkEquals("size apos remocao", 2, lista.size());
    }

    static void testeRemoveListaVazia() throws Exception {
        ListaArray lista = new ListaArray(10);
        printEstado(lista);
        checkExcecao("remove('X') em lista vazia", ListaVaziaException.class, () -> lista.remove("X"));
    }

    static void testeRemoveElementoInexistente() throws Exception {
        ListaArray lista = new ListaArray(10);
        lista.insertLast("A");
        printEstado(lista);
        checkExcecao("remove('Z') elemento inexistente", RuntimeException.class, () -> lista.remove("Z"));
    }

    static void testeInsertFirstListaCheia() throws Exception {
        ListaArray lista = new ListaArray(2);
        lista.insertFirst("A");
        lista.insertFirst("B");
        printEstado(lista);
        System.out.println("Lista cheia. Tentando insertFirst em lista cheia:");
        checkExcecao("insertFirst em lista cheia", ListaCheiaException.class, () -> lista.insertFirst("C"));
    }

    static void testeInsertLastListaCheia() throws Exception {
        ListaArray lista = new ListaArray(2);
        lista.insertLast("A");
        lista.insertLast("B");
        printEstado(lista);
        System.out.println("Lista cheia. Tentando insertLast em lista cheia:");
        checkExcecao("insertLast em lista cheia", ListaCheiaException.class, () -> lista.insertLast("C"));
    }

    static void testeInsertBeforeInexistente() throws Exception {
        ListaArray lista = new ListaArray(10);
        lista.insertLast("A");
        printEstado(lista);
        checkExcecao("insertBefore com elemento inexistente", RuntimeException.class, () -> lista.insertBefore("Z", "X"));
    }

    static void testeInsertAfterInexistente() throws Exception {
        ListaArray lista = new ListaArray(10);
        lista.insertLast("A");
        printEstado(lista);
        checkExcecao("insertAfter com elemento inexistente", RuntimeException.class, () -> lista.insertAfter("Z", "X"));
    }

    static void testeReplaceElementInexistente() throws Exception {
        ListaArray lista = new ListaArray(10);
        lista.insertLast("A");
        printEstado(lista);
        checkExcecao("replaceElement com elemento inexistente", RuntimeException.class, () -> lista.replaceElement("Z", "X"));
    }

    static void testeSwapElementsInexistente() throws Exception {
        ListaArray lista = new ListaArray(10);
        lista.insertLast("A");
        printEstado(lista);
        checkExcecao("swapElements com elemento inexistente", RuntimeException.class, () -> lista.swapElements("A", "Z"));
    }

    static void testeCrescimentoInsertFirst() throws Exception {
        ListaArray lista = new ListaArray(3);
        lista.insertFirst("1");
        lista.insertFirst("2");
        lista.insertFirst("3");
        printEstado(lista);
        System.out.println("Lista de capacidade 3 esta cheia. Tentando insertFirst desencadearia crescimento, mas lancara ListaCheiaException.");
        checkExcecao("insertFirst em lista cheia deveria lancar excecao", ListaCheiaException.class,
                () -> lista.insertFirst("4"));
    }

    static void testeCrescimentoInsertLast() throws Exception {
        ListaArray lista = new ListaArray(3);
        lista.insertLast("1");
        lista.insertLast("2");
        lista.insertLast("3");
        printEstado(lista);
        System.out.println("Lista de capacidade 3 esta cheia. Tentando insertLast desencadearia crescimento, mas lancara ListaCheiaException.");
        checkExcecao("insertLast em lista cheia deveria lancar excecao", ListaCheiaException.class,
                () -> lista.insertLast("4"));
    }

    static void testeEncolhimento() throws Exception {
        ListaArray lista = new ListaArray(12);
        lista.insertLast("A");
        lista.insertLast("B");
        lista.insertLast("C");
        lista.insertLast("D");
        printEstado(lista);
        System.out.println("Removendo elementos para possivel encolhimento (size <= capacity/3):");
        lista.remove("D");
        lista.remove("C");
        lista.remove("B");
        printEstado(lista);
        System.out.println("Apos remocoes, tamanho = 1, capacidade anterior = 12.");
        System.out.println("1 <= 12/3 = 1 <= 4? Sim. Deveria encolher para 6.");
        checkEquals("size() apos remocoes", 1, lista.size());
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

    static void printEstado(ListaArray lista) throws Exception {
        Object[] array = getArray(lista);
        StringBuilder sb = new StringBuilder();
        for (Object o : array) {
            if (o == null) {
                sb.append("[   ] ");
            } else {
                sb.append(String.format("[ %s ] ", o));
            }
        }
        System.out.println(sb.toString());
        System.out.println("   size=" + getInt(lista, "size")
                + "  capacity=" + getInt(lista, "capacity")
                + "  array.length=" + array.length);
    }

    static Object[] getArray(ListaArray lista) throws Exception {
        Field f = ListaArray.class.getDeclaredField("array");
        f.setAccessible(true);
        return (Object[]) f.get(lista);
    }

    static int getInt(ListaArray lista, String campo) throws Exception {
        Field f = ListaArray.class.getDeclaredField(campo);
        f.setAccessible(true);
        return f.getInt(lista);
    }
}