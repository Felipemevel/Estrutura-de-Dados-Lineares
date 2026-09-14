package lista.testes;

import lista.ListaDuplamenteLigada;
import lista.exceptions.IndiceInvalidoException;
import lista.exceptions.ListaVaziaException;
import node.Node;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TesteListaLigadaDupla {

    static List<String> falhas = new ArrayList<>();
    static int totalTestes = 0;

    public static void main(String[] args) {

        teste("Construtor ListaDuplamenteLigada() (padrao)", TesteListaLigadaDupla::testeConstrutorPadrao);
        teste("Construtor ListaDuplamenteLigada(Object)", TesteListaLigadaDupla::testeConstrutorComObjeto);

        teste("size() / isEmpty() em lista vazia", TesteListaLigadaDupla::testeListaVazia);

        teste("insertFirst(Object) - insercao no inicio", TesteListaLigadaDupla::testeInsertFirst);

        teste("insertLast(Object) - insercao no final", TesteListaLigadaDupla::testeInsertLast);

        teste("insertBefore(Object, Object) - insercao antes de elemento", TesteListaLigadaDupla::testeInsertBefore);

        teste("insertAfter(Object, Object) - insercao apos elemento", TesteListaLigadaDupla::testeInsertAfter);

        teste("replaceElement(Object, Object) - substituicao e retorno do antigo", TesteListaLigadaDupla::testeReplaceElement);

        teste("swapElements(Object, Object) - troca de elementos", TesteListaLigadaDupla::testeSwapElements);

        teste("remove(Object) - remocao e desvinculacao do no", TesteListaLigadaDupla::testeRemove);

        teste("first() e last() - consulta dos nós das extremidades", TesteListaLigadaDupla::testeFirstLast);

        teste("isFirst(Node) e isLast(Node) - verificacao de posicao", TesteListaLigadaDupla::testeIsFirstIsLast);

        teste("before(Node) e after(Node) - navegacao entre nos", TesteListaLigadaDupla::testeBeforeAfter);

        teste("remove(Object) em lista vazia -> deve lancar excecao", TesteListaLigadaDupla::testeRemoveListaVazia);

        teste("remove(Object) elemento inexistente -> deve lancar excecao", TesteListaLigadaDupla::testeRemoveElementoInexistente);

        teste("insertBefore elemento inexistente -> deve lancar excecao", TesteListaLigadaDupla::testeInsertBeforeInexistente);

        teste("insertAfter elemento inexistente -> deve lancar excecao", TesteListaLigadaDupla::testeInsertAfterInexistente);

        teste("replaceElement elemento inexistente -> deve lancar excecao", TesteListaLigadaDupla::testeReplaceElementInexistente);

        teste("swapElements com elementos inexistentes -> deve lancar excecao", TesteListaLigadaDupla::testeSwapElementsInexistente);

        teste("first() e last() em lista vazia -> deve lancar excecao", TesteListaLigadaDupla::testeFirstLastListaVazia);

        teste("before() e after() nos limites -> deve lancar excecao", TesteListaLigadaDupla::testeBeforeAfterLimites);

        resumoFinal();
    }

    static void testeConstrutorPadrao() throws Exception {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada();
        printEstado(lista);
        checkEquals("ListaDuplamenteLigada() -> size inicial", 0, lista.size());
        checkEquals("ListaDuplamenteLigada() -> isEmpty inicial", true, lista.isEmpty());
    }

    static void testeConstrutorComObjeto() throws Exception {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada("A");
        printEstado(lista);
        checkEquals("ListaDuplamenteLigada('A') -> size inicial", 1, lista.size());
        checkEquals("ListaDuplamenteLigada('A') -> isEmpty inicial", false, lista.isEmpty());
        Node primeiro = (Node) lista.first();
        checkEquals("ListaDuplamenteLigada('A') -> elemento contido", "A", primeiro.getElement());
    }

    static void testeListaVazia() throws Exception {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada();
        printEstado(lista);
        checkEquals("size() com lista vazia", 0, lista.size());
        checkEquals("isEmpty() com lista vazia", true, lista.isEmpty());
    }

    static void testeInsertFirst() throws Exception {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada();
        lista.insertFirst("B");
        lista.insertFirst("A");
        printEstado(lista);
        Node primeiro = (Node) lista.first();
        Node ultimo = (Node) lista.last();
        checkEquals("insertFirst('A') -> primeiro elemento", "A", primeiro.getElement());
        checkEquals("insertFirst('B') -> ultimo elemento", "B", ultimo.getElement());
        checkEquals("size apos 2 insercoes no inicio", 2, lista.size());
    }

    static void testeInsertLast() throws Exception {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada();
        lista.insertLast("X");
        lista.insertLast("Y");
        printEstado(lista);
        Node primeiro = (Node) lista.first();
        Node ultimo = (Node) lista.last();
        checkEquals("insertLast('X') -> primeiro elemento", "X", primeiro.getElement());
        checkEquals("insertLast('Y') -> ultimo elemento", "Y", ultimo.getElement());
        checkEquals("size apos 2 insercoes no final", 2, lista.size());
    }

    static void testeInsertBefore() throws Exception {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada();
        lista.insertLast("A");
        lista.insertLast("C");
        lista.insertBefore("C", "B");
        printEstado(lista);
        Node primeiro = (Node) lista.first();
        Node segundo = (Node) lista.after(primeiro);
        checkEquals("insertBefore('C', 'B') -> segundo elemento", "B", segundo.getElement());
        checkEquals("size apos insercao", 3, lista.size());
    }

    static void testeInsertAfter() throws Exception {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada();
        lista.insertLast("A");
        lista.insertLast("C");
        lista.insertAfter("A", "B");
        printEstado(lista);
        Node primeiro = (Node) lista.first();
        Node segundo = (Node) lista.after(primeiro);
        checkEquals("insertAfter('A', 'B') -> segundo elemento", "B", segundo.getElement());
        checkEquals("size apos insercao", 3, lista.size());
    }

    static void testeReplaceElement() throws Exception {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada();
        lista.insertLast("A");
        Object antigo = lista.replaceElement("A", "B");
        printEstado(lista);
        Node primeiro = (Node) lista.first();
        checkEquals("replaceElement('A', 'B') -> retornou o antigo", "A", antigo);
        checkEquals("replaceElement('A', 'B') -> elemento atualizado", "B", primeiro.getElement());
    }

    static void testeSwapElements() throws Exception {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada();
        lista.insertLast("X");
        lista.insertLast("Y");
        lista.swapElements("X", "Y");
        printEstado(lista);
        Node primeiro = (Node) lista.first();
        Node ultimo = (Node) lista.last();
        checkEquals("swapElements('X', 'Y') -> primeiro elemento", "Y", primeiro.getElement());
        checkEquals("swapElements('X', 'Y') -> ultimo elemento", "X", ultimo.getElement());
    }

    static void testeRemove() throws Exception {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada();
        lista.insertLast("X");
        lista.insertLast("Y");
        lista.insertLast("Z");
        printEstado(lista);
        Object removido = lista.remove("Y");
        printEstado(lista);
        Node primeiro = (Node) lista.first();
        Node segundo = (Node) lista.after(primeiro);
        checkEquals("remove('Y') -> retornou", "Y", removido);
        checkEquals("remove('Y') -> 'Z' agora e o segundo elemento", "Z", segundo.getElement());
        checkEquals("size apos remocao", 2, lista.size());
    }

    static void testeFirstLast() throws Exception {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada();
        lista.insertLast("Inicio");
        lista.insertLast("Fim");
        printEstado(lista);
        Node f = (Node) lista.first();
        Node l = (Node) lista.last();
        checkEquals("first() -> elemento inicial", "Inicio", f.getElement());
        checkEquals("last() -> elemento final", "Fim", l.getElement());
    }

    static void testeIsFirstIsLast() throws Exception {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada();
        lista.insertLast("A");
        lista.insertLast("B");
        printEstado(lista);
        Node f = (Node) lista.first();
        Node l = (Node) lista.last();
        checkEquals("isFirst(f)", true, lista.isFirst(f));
        checkEquals("isFirst(l)", false, lista.isFirst(l));
        checkEquals("isLast(l)", true, lista.isLast(l));
        checkEquals("isLast(f)", false, lista.isLast(f));
    }

    static void testeBeforeAfter() throws Exception {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada();
        lista.insertLast("A");
        lista.insertLast("B");
        printEstado(lista);
        Node f = (Node) lista.first();
        Node l = (Node) lista.last();
        Node aposFirst = (Node) lista.after(f);
        Node antesLast = (Node) lista.before(l);
        checkEquals("after(f) == l", l, aposFirst);
        checkEquals("before(l) == f", f, antesLast);
    }

    static void testeRemoveListaVazia() throws Exception {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada();
        printEstado(lista);
        checkExcecao("remove('X') em lista vazia", ListaVaziaException.class, () -> lista.remove("X"));
    }

    static void testeRemoveElementoInexistente() throws Exception {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada();
        lista.insertLast("A");
        printEstado(lista);
        checkExcecao("remove('Z') elemento inexistente", IndiceInvalidoException.class, () -> lista.remove("Z"));
    }

    static void testeInsertBeforeInexistente() throws Exception {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada();
        lista.insertLast("A");
        printEstado(lista);
        checkExcecao("insertBefore com elemento inexistente", IndiceInvalidoException.class, () -> lista.insertBefore("Z", "X"));
    }

    static void testeInsertAfterInexistente() throws Exception {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada();
        lista.insertLast("A");
        printEstado(lista);
        checkExcecao("insertAfter com elemento inexistente", IndiceInvalidoException.class, () -> lista.insertAfter("Z", "X"));
    }

    static void testeReplaceElementInexistente() throws Exception {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada();
        lista.insertLast("A");
        printEstado(lista);
        checkExcecao("replaceElement com elemento inexistente", IndiceInvalidoException.class, () -> lista.replaceElement("Z", "X"));
    }

    static void testeSwapElementsInexistente() throws Exception {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada();
        lista.insertLast("A");
        printEstado(lista);
        checkExcecao("swapElements com elemento inexistente", IndiceInvalidoException.class, () -> lista.swapElements("A", "Z"));
    }

    static void testeFirstLastListaVazia() throws Exception {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada();
        printEstado(lista);
        checkExcecao("first() em lista vazia", ListaVaziaException.class, lista::first);
        checkExcecao("last() em lista vazia", ListaVaziaException.class, lista::last);
    }

    static void testeBeforeAfterLimites() throws Exception {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada();
        lista.insertLast("A");
        lista.insertLast("B");
        printEstado(lista);
        Node f = (Node) lista.first();
        Node l = (Node) lista.last();
        checkExcecao("before(first) -> no limite inicial", IndiceInvalidoException.class, () -> lista.before(f));
        checkExcecao("after(last) -> no limite final", IndiceInvalidoException.class, () -> lista.after(l));
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

    static void printEstado(ListaDuplamenteLigada lista) throws Exception {
        Node sentinel = getSentinel(lista);
        StringBuilder sb = new StringBuilder();
        Node current = sentinel.getNext();
        if (current == sentinel) {
            sb.append("[ VAZIA ]");
        } else {
            while (current != sentinel) {
                sb.append(String.format("[ %s ]", current.getElement()));
                if (current.getNext() != sentinel) {
                    sb.append(" <-> ");
                }
                current = current.getNext();
            }
        }
        System.out.println(sb.toString());
        System.out.println("   size=" + lista.size());
    }

    static Node getSentinel(ListaDuplamenteLigada lista) throws Exception {
        Field f = ListaDuplamenteLigada.class.getDeclaredField("sentinel");
        f.setAccessible(true);
        return (Node) f.get(lista);
    }
}