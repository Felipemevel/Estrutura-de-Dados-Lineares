package sequencia;

import sequencia.Exceptions.IndiceInvalidoException;
import sequencia.Exceptions.PositionInvalidoException;
import sequencia.Exceptions.SequenciaVaziaException;

public class TesteSequenciaNode {

    private static int passou = 0;
    private static int falhou = 0;

    public static void main(String[] args) {
        testesInsercao();
        testesConsultaEReplace();
        testesRemocao();
        testesNavegacao();
        testesPosition();
        testesSwapEInsertRelativo();
        testesRankOf();
        testesSequenciaVazia();
        testesIndicesInvalidos();
        testesPositionInvalida();
        resumo();
    }

    // ---------- Testes base ----------

    private static void testesInsercao() {
        titulo("Inserção");
        SequenciaPosition s = criar("A", "B", "C");
        verificar("size após 3 inserções", s.size() == 3);
        verificar("ordem A,B,C", ordem(s).equals("ABC"));

        s.insertFirst("X");
        verificar("insertFirst", ordem(s).equals("XABC"));

        s.insertLast("Z");
        verificar("insertLast", ordem(s).equals("XABCZ"));

        s.insertAtRank(2, "M");
        verificar("insertAtRank no meio", ordem(s).equals("XAMBCZ"));

        s.insertAtRank(s.size(), "F");
        verificar("insertAtRank no fim (r == size)", ordem(s).equals("XAMBCZF"));
    }

    private static void testesConsultaEReplace() {
        titulo("Consulta e replace");
        SequenciaPosition s = criar("A", "B", "C");
        verificar("elemAtRank(1)", "B".equals(s.elemAtRank(1)));
        verificar("first()", "A".equals(s.first()));
        verificar("last()", "C".equals(s.last()));

        Object antigo = s.replaceAtRank(1, "Y");
        verificar("replaceAtRank retorna o antigo", "B".equals(antigo));
        verificar("replaceAtRank altera o elemento", ordem(s).equals("AYC"));
        verificar("replaceAtRank não muda o size", s.size() == 3);
    }

    private static void testesRemocao() {
        titulo("Remoção");
        SequenciaPosition s = criar("A", "B", "C", "D");
        verificar("removeAtRank(1) retorna B", "B".equals(s.removeAtRank(1)));
        verificar("ordem após remover meio", ordem(s).equals("ACD"));
        verificar("removeAtRank(0) retorna A", "A".equals(s.removeAtRank(0)));
        verificar("removeAtRank último retorna D", "D".equals(s.removeAtRank(1)));
        verificar("resta apenas C", ordem(s).equals("C") && s.size() == 1);
        s.removeAtRank(0);
        verificar("vazia após remover tudo", s.isEmpty());
    }

    private static void testesNavegacao() {
        titulo("before / after");
        SequenciaPosition s = criar("A", "B", "C");
        verificar("before(B) = A", "A".equals(s.before(s.atRank(1))));
        verificar("after(B) = C", "C".equals(s.after(s.atRank(1))));
        verificar("after(A) = B", "B".equals(s.after(s.atRank(0))));
        verificar("before(C) = B", "B".equals(s.before(s.atRank(2))));
    }

    private static void testesPosition() {
        titulo("Operações por Position");
        SequenciaPosition s = criar("A", "B", "C");
        Position p = s.atRank(1);

        verificar("replaceElement retorna o antigo", "B".equals(s.replaceElement(p, "Y")));
        verificar("replaceElement altera", ordem(s).equals("AYC"));

        s.remove(p);
        verificar("remove(Position)", ordem(s).equals("AC") && s.size() == 2);
    }

    private static void testesSwapEInsertRelativo() {
        titulo("swap / insertBefore / insertAfter");
        SequenciaPosition s = criar("A", "B", "C");
        s.swapElement(s.atRank(0), s.atRank(2));
        verificar("swapElement", ordem(s).equals("CBA"));

        s = criar("A", "B", "C");
        s.insertBefore(s.atRank(1), "X");
        verificar("insertBefore", ordem(s).equals("AXBC"));

        s = criar("A", "B", "C");
        s.insertAfter(s.atRank(1), "X");
        verificar("insertAfter no meio", ordem(s).equals("ABXC"));

        s = criar("A");
        s.insertAfter(s.atRank(0), "X");
        verificar("insertAfter no último", ordem(s).equals("AX"));

        s = criar("A");
        s.insertBefore(s.atRank(0), "X");
        verificar("insertBefore no primeiro", ordem(s).equals("XA"));
    }

    private static void testesRankOf() {
        titulo("rankOf");
        SequenciaPosition s = criar("A", "B", "C");
        Position p = s.atRank(2);
        verificar("rankOf retorna índice correto", s.rankOf(p) == 2);

        s.removeAtRank(0);
        verificar("rankOf após remoção anterior", s.rankOf(p) == 1);
    }

    // ---------- Testes de borda ----------

    private static void testesSequenciaVazia() {
        titulo("Sequência vazia");
        SequenciaPosition s = new SequenciaPosition();
        verificar("isEmpty e size 0", s.isEmpty() && s.size() == 0);
        esperaExcecao("atRank(0) vazia", SequenciaVaziaException.class, () -> s.atRank(0));
        esperaExcecao("first() vazia", SequenciaVaziaException.class, () -> s.first());
        esperaExcecao("last() vazia", SequenciaVaziaException.class, () -> s.last());
        esperaExcecao("removeAtRank(0) vazia", SequenciaVaziaException.class, () -> s.removeAtRank(0));

        SequenciaPosition t = new SequenciaPosition();
        t.insertLast("A");
        verificar("insertLast em vazia", ordem(t).equals("A"));
    }

    private static void testesIndicesInvalidos() {
        titulo("Índices inválidos");
        SequenciaPosition s = criar("A", "B", "C");
        esperaExcecao("atRank(-1)", IndiceInvalidoException.class, () -> s.atRank(-1));
        esperaExcecao("atRank(size)", IndiceInvalidoException.class, () -> s.atRank(3));
        esperaExcecao("insertAtRank(-1)", IndiceInvalidoException.class, () -> s.insertAtRank(-1, "X"));
        esperaExcecao("insertAtRank(size+1)", IndiceInvalidoException.class, () -> s.insertAtRank(4, "X"));
        verificar("sequência intacta após erros", ordem(s).equals("ABC"));
    }

    private static void testesPositionInvalida() {
        titulo("Position inválida");
        SequenciaPosition s = criar("A", "B", "C");
        esperaExcecao("before(primeiro)", IndiceInvalidoException.class, () -> s.before(s.atRank(0)));
        esperaExcecao("after(último)", IndiceInvalidoException.class, () -> s.after(s.atRank(2)));

        Position removida = s.atRank(1);
        s.removeAtRank(1);
        esperaExcecao("Position removida", PositionInvalidoException.class, () -> s.before(removida));

        SequenciaPosition outra = criar("A", "B", "C");
        esperaExcecao("Position de outra sequência", PositionInvalidoException.class,
                () -> s.replaceElement(outra.atRank(0), "X"));

        Position estranha = new Position() {
            @Override
            public Object getElement() { return "X"; }
        };
        esperaExcecao("Position de outra implementação", PositionInvalidoException.class,
                () -> s.remove(estranha));
    }

    // ---------- Auxiliares ----------

    private static SequenciaPosition criar(Object... elementos) {
        SequenciaPosition s = new SequenciaPosition();
        for (int i = 0; i < elementos.length; i++) s.insertAtRank(i, elementos[i]);
        return s;
    }

    private static String ordem(SequenciaPosition s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.size(); i++) sb.append(s.elemAtRank(i));
        return sb.toString();
    }

    private static void esperaExcecao(String nome, Class<? extends Exception> tipo, Runnable acao) {
        try {
            acao.run();
            verificar(nome + " (esperava " + tipo.getSimpleName() + ")", false);
        } catch (Exception e) {
            verificar(nome + " -> " + tipo.getSimpleName(), tipo.isInstance(e));
        }
    }

    private static void verificar(String nome, boolean condicao) {
        if (condicao) passou++; else falhou++;
        System.out.println((condicao ? "  [OK]     " : "  [FALHOU] ") + nome);
    }

    private static void titulo(String nome) {
        System.out.println("\n== " + nome + " ==");
    }

    private static void resumo() {
        System.out.println("\nPassaram: " + passou + " | Falharam: " + falhou);
    }
}