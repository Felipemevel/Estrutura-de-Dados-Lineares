public class Main {
    public static void main(String[] args) {

        ArvoreBinariaPesquisa arvore = new ArvoreBinariaPesquisa();
        arvore.insert(10);
        arvore.insert(5);
        arvore.insert(2);
        arvore.insert(15);
        arvore.insert(8);
        arvore.insert(22);
        arvore.show();

        System.out.println("\n=================");
        System.out.println("==================");
        System.out.println("=================\n");

        arvore.remove(5);

        arvore.show();

    }
}
