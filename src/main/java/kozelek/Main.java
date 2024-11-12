package kozelek;

import kozelek.GUI.Wrapper;
import kozelek.GUI.contoller.MainController;
import kozelek.GUI.views.MainWindow;
import kozelek.data.GPS;
import kozelek.data.Nehnutelnost;
import kozelek.data.Parcela;
import kozelek.data.objekt.GPSObjekt;
import kozelek.data.objekt.interfaces.IObject;
import kozelek.generator.Generator;
import kozelek.generator.TestClass;
import kozelek.tree.KDTree;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner input;
    public static void main(String[] args) {
        KDTree<Nehnutelnost> treeNehnutelnost = new KDTree<>();
        KDTree<Parcela> treeParcela = new KDTree<>();
        KDTree<GPSObjekt> treeAll = new KDTree<>();
        Wrapper wrapper = new Wrapper(treeNehnutelnost, treeParcela, treeAll);

        MainWindow mw = new MainWindow();
        MainController<IObject> mc = new MainController<>(wrapper, mw);

//        wrapper.importCsv("neh.csv", Nehnutelnost.class);
//        wrapper.importCsv("par.csv", Parcela.class);
//
//        System.out.println(wrapper.getParcelyLevelOrder().size());
//        System.out.println(wrapper.getNehnutelnostiLevelOrder().size());
//        System.out.println(wrapper.getVsetkyLevelOrder().size());

//        int pocetDelete = 10000;
//
//        for (int i = 1000; i < 10000+1; i++) {
//            KDTree<Nehnutelnost> tree = new KDTree<>();
//            Generator<Nehnutelnost> gen = new Generator<>(tree, 1000,1000, 0,0);
//
//            gen.setSeed(i);
//
//            gen.generujData(0, 20000, Nehnutelnost.class, 0.2);
//
//            int a = gen.vymaz(pocetDelete);
//            int b = gen.getLevelOrder().size();
//            int c = gen.vyhladaj(10000);
//
//
//            System.out.printf("%d %d %d\n", a, b, c);
//            gen.clear();
//        }



//        System.out.printf("InOrderSize: %d \nA: %d \nLevelOrderSize: %d\n", before, a, after);


//        input = new Scanner(System.in);
//        System.out.println("_______");
//        System.out.println("1. Test");
//        System.out.println("2. Test seed");
//        System.out.println("3. GUI");
//        int i = input.nextInt();
//
//        switch (i) {
//            case 1 -> test();
//            case 2 -> testSeed();
//            case 3 -> new MainWindow<IObject>(wrapper);
//            default -> {
//            }
//        }
    }

    public static void testSeed() {
//        System.out.println("Zadaj seedy od:");
//        int od = input.nextInt();
//        System.out.println("Zadaj počet seedov:");
//        int doo = input.nextInt();
        int od = 1966;
        int doo = 1;

        for (int i = od; i < od + doo; i++) {
            KDTree<TestClass> treeNehnutelnost = new KDTree<>();

            Generator<TestClass> generatorNeh = new Generator<>(treeNehnutelnost, 100, 100, 0, 0);

            generatorNeh.setSeed(i);

            ArrayList<TestClass> neh = generatorNeh.generujData(0, 5000, TestClass.class, 0.2);


            int a = generatorNeh.vyhladaj(100);

            int c = generatorNeh.vymaz(100);

//            if (c < 10) {
//                System.out.printf("Vymaz: %d\n", c);
//                System.out.println(i);
//                break;
//            }
            generatorNeh.clear();
        }
    }

    public static void test() {
        System.out.println("Zadaj pocet operacii pre insert:");
        int pocetOperaciiInsert = input.nextInt();
        System.out.println("Zadaj počet operacii pre find: ");
        int pocetOperaciiFind = input.nextInt();
        System.out.println("Zadaj počet operacii pre delete: ");
        int pocetOperaciiDelete = input.nextInt();

        KDTree<Nehnutelnost> treeNehnutelnost = new KDTree<>();
        KDTree<Parcela> treeParcela = new KDTree<>();

        Nehnutelnost nehTemp = new Nehnutelnost(1, "ahoj", new GPS(10.0, 15.0));
        Parcela parTemp = new Parcela(1, "ahoj", new GPS(10.0, 15.0));

        Generator<Nehnutelnost> generatorNeh = new Generator<>(treeNehnutelnost, 100, 100, 0, 0);
        Generator<Parcela> generatorParcela = new Generator<>(treeParcela, 100, 100, 0, 0);

        ArrayList<Nehnutelnost> neh = generatorNeh.generujData(0, pocetOperaciiInsert, Nehnutelnost.class,0);
        ArrayList<Parcela> par = generatorParcela.generujData(0, pocetOperaciiInsert, Parcela.class,0);

        int a = generatorNeh.vyhladaj(pocetOperaciiFind);
        int b = generatorParcela.vyhladaj(pocetOperaciiFind);
        System.out.printf("1. Find: Nehnutelnost: %d Parcela: %d\n",a,b);

//        int c = generatorNeh.vymaz(pocetOperaciiDelete);
//        int d = generatorParcela.vymaz(pocetOperaciiDelete);
//        System.out.printf("Vymaz: %d %d\n", c, d);

//        generatorNeh.edit(5000);
//        generatorParcela.edit(5000);

        a = generatorNeh.vyhladaj(pocetOperaciiFind);
        b = generatorParcela.vyhladaj(pocetOperaciiFind);
        System.out.printf("2. Find: Nehnutelnost: %d Parcela: %d\n",a, b);
    }
}