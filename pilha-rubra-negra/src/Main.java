import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static List<String> falhas = new ArrayList<>();
    static int totalTestes = 0;

    public static void main(String[] args) {

        teste("Construtor RubroNegra() (capacidade padrao)", Main::testeConstrutorPadrao);
        teste("Construtor RubroNegra(int capacity)", Main::testeConstrutorComCapacidade);

        teste("sizeVermelha() / sizeNegra() / sizeTotal() em pilha vazia", Main::testeTamanhosPilhaVazia);

        teste("pushVermelha(Object) - push simples e efeito no array", Main::testePushVermelhaSimples);
        teste("pushNegra(Object) - push simples e efeito no array", Main::testePushNegraSimples);

        teste("sizeVermelha()/sizeNegra() apos pushes", Main::testeTamanhosAposPush);

        teste("popVermelha(Object) - remove em ordem LIFO", Main::testePopVermelhaLifo);
        teste("popNegra(Object) - remove em ordem LIFO", Main::testePopNegraLifo);

        teste("popVermelha(Object) em pilha vazia -> deve lancar PilhaVazia", Main::testePopVermelhaVazia);
        teste("popNegra(Object) em pilha vazia -> deve lancar PilhaVazia", Main::testePopNegraVazia);

        teste("topVermelha() - valor do topo", Main::testeTopVermelha);
        teste("topNegra() - valor do topo", Main::testeTopNegra);

        teste("Reaproveitar espaco: push -> pop -> push de novo", Main::testeReuso);

        teste("validacaoTamanho()/alterarTamanho() - crescimento via pushVermelha ate encher o array",
                Main::testeCrescimentoViaVermelha);
        teste("validacaoTamanho()/alterarTamanho() - crescimento via pushNegra",
                Main::testeCrescimentoViaNegra);
        teste("validacaoTamanho()/alterarTamanho() - encolhimento apos varios pops",
                Main::testeEncolhimento);

        teste("Uso combinado: pushNegra e pushVermelha alternados ate se encontrarem no meio",
                Main::testeColisaoNegraVermelha);

        resumoFinal();
    }

    static void testeConstrutorPadrao() throws Exception {
        RubroNegra p = new RubroNegra();
        printEstado(p);
        checkEquals("RubroNegra() -> capacity interno", 10, getInt(p, "capacity"));
        checkEquals("RubroNegra() -> array.length", 10, getArray(p).length);
        checkEquals("RubroNegra() -> headNegra inicial", 10, getInt(p, "headNegra"));
        checkEquals("RubroNegra() -> headVermelha inicial", -1, getInt(p, "headVermelha"));
    }

    static void testeConstrutorComCapacidade() throws Exception {
        RubroNegra p = new RubroNegra(15);
        printEstado(p);
        checkEquals("RubroNegra(15) -> capacity interno", 15, getInt(p, "capacity"));
        checkEquals("RubroNegra(15) -> array.length", 15, getArray(p).length);
        checkEquals("RubroNegra(15) -> headNegra inicial", 15, getInt(p, "headNegra"));
        checkEquals("RubroNegra(15) -> headVermelha inicial", -1, getInt(p, "headVermelha"));
    }

    static void testeTamanhosPilhaVazia() throws Exception {
        RubroNegra p = new RubroNegra(6);
        printEstado(p);
        System.out.println("sizeNegra()=" + p.sizeNegra() + " sizeVermelha()=" + p.sizeVermelha() + " sizeTotal()=" + p.sizeTotal());
        checkEquals("sizeVermelha() com pilha vazia", 0, p.sizeVermelha());
        checkEquals("sizeNegra() com pilha vazia", 0, p.sizeNegra());
        checkEquals("sizeTotal() com pilha vazia", 0, p.sizeTotal());
    }

    static void testePushVermelhaSimples() throws Exception {
        RubroNegra p = new RubroNegra(10);
        p.pushVermelha(7);
        printEstado(p);
        checkEquals("pushVermelha(7) -> array[0]", 7, getArray(p)[0]);
        checkEquals("pushVermelha(7) -> headVermelha", 0, getInt(p, "headVermelha"));
    }

    static void testePushNegraSimples() throws Exception {
        RubroNegra p = new RubroNegra(10);
        p.pushNegra(9);
        printEstado(p);
        checkEquals("pushNegra(9) -> array[9] (ultima posicao)", 9, getArray(p)[9]);
        checkEquals("pushNegra(9) -> headNegra", 9, getInt(p, "headNegra"));
    }

    static void testeTamanhosAposPush() throws Exception {
        RubroNegra p = new RubroNegra(20);
        p.pushVermelha("a");
        p.pushVermelha("b");
        p.pushVermelha("c");
        printEstado(p);
        checkEquals("sizeVermelha() apos 3 pushVermelha", 3, p.sizeVermelha());
    }

    static void testePopVermelhaLifo() throws Exception {
        RubroNegra p = new RubroNegra(10);
        p.pushVermelha("x");
        p.pushVermelha("y");
        p.pushVermelha("z");
        printEstado(p);
        Object r1 = p.popVermelha(null);
        printEstado(p);
        checkEquals("popVermelha() #1 (deve ser o ultimo empurrado)", "z", r1);
        Object r2 = p.popVermelha(null);
        printEstado(p);
        checkEquals("popVermelha() #2", "y", r2);
    }

    static void testePopNegraLifo() throws Exception {
        RubroNegra p = new RubroNegra(10);
        p.pushNegra("x");
        p.pushNegra("y");
        p.pushNegra("z");
        printEstado(p);
        Object r1 = p.popNegra(null);
        printEstado(p);
        checkEquals("popNegra() #1 (deve ser o ultimo empurrado)", "z", r1);
        Object r2 = p.popNegra(null);
        printEstado(p);
        checkEquals("popNegra() #2", "y", r2);
    }

    static void testePopVermelhaVazia() throws Exception {
        RubroNegra p = new RubroNegra(10);
        printEstado(p);
        checkExcecao("popVermelha(null) em pilha vazia", PilhaVazia.class, () -> p.popVermelha(null));
    }

    static void testePopNegraVazia() throws Exception {
        RubroNegra p = new RubroNegra(10);
        printEstado(p);
        checkExcecao("popNegra(null) em pilha vazia", PilhaVazia.class, () -> p.popNegra(null));
    }

    static void testeTopVermelha() throws Exception {
        RubroNegra p = new RubroNegra(10);
        p.pushVermelha("topo-vermelho");
        printEstado(p);
        Object top = p.topVermelha();
        checkEquals("topVermelha() apos pushVermelha(\"topo-vermelho\")", "topo-vermelho", top);
    }

    static void testeTopNegra() throws Exception {
        RubroNegra p = new RubroNegra(10);
        p.pushNegra("topo-negro");
        printEstado(p);
        Object top = p.topNegra();
        checkEquals("topNegra() apos pushNegra(\"topo-negro\")", "topo-negro", top);
    }

    static void testeReuso() throws Exception {
        RubroNegra p = new RubroNegra(10);
        p.pushNegra("um");
        printEstado(p);
        p.popNegra(null);
        printEstado(p);
        p.pushNegra("novo");
        printEstado(p);
        checkEquals("pushNegra apos esvaziar -> array[9] reaproveitado", "novo", getArray(p)[9]);
    }

    static void testeCrescimentoViaVermelha() throws Exception {
        RubroNegra p = new RubroNegra(6);
        for (int i = 1; i <= 6; i++) {
            p.pushVermelha(i);
            printEstado(p);
        }
        System.out.println("Array de capacidade 6 esta cheio (6 elementos). Testando o gatilho de crescimento com o 7o push:");
        p.pushVermelha(7);
        printEstado(p);
        checkEquals("sizeVermelha() apos 7 pushVermelha (deveria ter crescido)", 7, p.sizeVermelha());
    }

    static void testeCrescimentoViaNegra() throws Exception {
        RubroNegra p = new RubroNegra(6);
        p.pushNegra("primeiro");
        printEstado(p);
        System.out.println("Testando o gatilho de validacaoTamanho()/alterarTamanho() com o 2o pushNegra:");
        p.pushNegra("segundo");
        printEstado(p);
        checkEquals("sizeNegra() apos 2 pushNegra (deveria ter crescido)", 2, p.sizeNegra());
    }

    static void testeEncolhimento() throws Exception {
        RubroNegra p = new RubroNegra(12);
        p.pushVermelha(1);
        p.pushVermelha(2);
        p.pushVermelha(3);
        printEstado(p);
        System.out.println("Removendo elementos para tentar acionar o encolhimento (sizeTotal <= array.length/3):");
        p.popVermelha(null);
        printEstado(p);
        p.popVermelha(null);
        printEstado(p);
        checkEquals("sizeTotal() apos os pops", 1, p.sizeTotal());
    }

    static void testeColisaoNegraVermelha() throws Exception {
        RubroNegra p = new RubroNegra(8);
        for (int i = 1; i <= 5; i++) {
            p.pushVermelha(i);
            printEstado(p);
            p.pushNegra(i * 100);
            printEstado(p);
        }
        checkEquals("sizeTotal() apos 5 pushVermelha + 5 pushNegra", 10, p.sizeTotal());
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

    static void printEstado(RubroNegra p) throws Exception {
        Object[] array = getArray(p);
        StringBuilder sb = new StringBuilder();
        for (Object o : array) {
            if (o == null) {
                sb.append("[   ] ");
            } else {
                sb.append(String.format("[ %s ] ", o));
            }
        }
        System.out.println(sb.toString());
        System.out.println("   headNegra=" + getInt(p, "headNegra")
                + "  headVermelha=" + getInt(p, "headVermelha")
                + "  capacity(campo)=" + getInt(p, "capacity")
                + "  array.length=" + array.length);
    }

    static Object[] getArray(RubroNegra p) throws Exception {
        Field f = RubroNegra.class.getDeclaredField("array");
        f.setAccessible(true);
        return (Object[]) f.get(p);
    }

    static int getInt(RubroNegra p, String campo) throws Exception {
        Field f = RubroNegra.class.getDeclaredField(campo);
        f.setAccessible(true);
        return f.getInt(p);
    }
}