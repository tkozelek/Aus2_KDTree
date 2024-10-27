package kozelek;

import kozelek.data.GPS;
import kozelek.data.Nehnutelnost;
import kozelek.data.Parcela;
import kozelek.data.objekt.Entity;
import kozelek.data.objekt.IComparator;
import kozelek.data.objekt.IObject;
import kozelek.generator.Generator;
import kozelek.tree.KDTree;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner input;
    public static void main(String[] args) {
//        KDTree<Nehnutelnost> tree = new KDTree();
//        Generator<Nehnutelnost> gen = new Generator<>(tree,100,100,0,0);
//
//        gen.setSeed(1330);
//
//        gen.generujData(0, 5000, Nehnutelnost.class);
//        gen.vyhladaj(5000);

        input = new Scanner(System.in);
        System.out.println("_______");
        System.out.println("1. Test");
        System.out.println("2. Test seed");
        int i = input.nextInt();
        switch (i) {
            case 1 -> test();
            case 2 -> testSeed();
            default -> {
            }
        }
    }

    public static void testSeed() {
        System.out.println("Zadaj seedy od:");
        int od = input.nextInt();
        System.out.println("Zadaj počet seedov:");
        int doo = input.nextInt();

        for (int i = od; i < doo; i++) {
            KDTree<Nehnutelnost> treeNehnutelnost = new KDTree<>();
            KDTree<Parcela> treeParcela = new KDTree<>();
            KDTree<Entity> treeAll = new KDTree<>();

            Generator<Nehnutelnost> generatorNeh = new Generator<>(treeNehnutelnost, 100, 100, 0, 0);
            Generator<Parcela> generatorParcela = new Generator<>(treeParcela, 100, 100, 0, 0);
            Generator<Entity> generatorAll = new Generator<>(treeAll, 100, 100, 0, 0);

            generatorNeh.setSeed(i);
            generatorParcela.setSeed(i);
            generatorAll.setSeed(i);

            ArrayList<Nehnutelnost> neh = generatorNeh.generujData(0, 5000, Nehnutelnost.class);
            ArrayList<Parcela> par = generatorParcela.generujData(0, 5000, Parcela.class);

            generatorAll.insertIntoAll(neh);
            generatorAll.insertIntoAll(par);

            int a = generatorNeh.vyhladaj(5000);
            int b = generatorParcela.vyhladaj(5000);
            int c = generatorAll.vyhladaj(5000);

            if (a < 5 || b < 5) {
                System.out.printf("%d %d %d\n", a, b, c);
                System.out.println(i);
                break;
            }

            generatorNeh.clear();
            generatorParcela.clear();
            generatorAll.clear();
        }
    }

    public static void test() {
        System.out.println("Zadaj pocet operacii pre insert:");
        int pocetOperaciiInsert = input.nextInt();
        System.out.println("Zadaj počet operacii pre find: (Vsetky *= 2)");
        int pocetOperaciiFind = input.nextInt();

        KDTree<Nehnutelnost> treeNehnutelnost = new KDTree<>();
        KDTree<Parcela> treeParcela = new KDTree<>();
        KDTree<Entity> treeAll = new KDTree<>();

        Nehnutelnost nehTemp = new Nehnutelnost(1, "ahoj", new GPS(10.0, 15.0));
        Parcela parTemp = new Parcela(1, "ahoj", new GPS(10.0, 15.0));

        treeAll.insert(parTemp);
        treeAll.insert(nehTemp);

        Generator<Nehnutelnost> generatorNeh = new Generator<>(treeNehnutelnost, 100, 100, 0, 0);
        Generator<Parcela> generatorParcela = new Generator<>(treeParcela, 100, 100, 0, 0);
        Generator<Entity> generatorAll = new Generator<>(treeAll, 100, 100, 0, 0);

        ArrayList<Nehnutelnost> neh = generatorNeh.generujData(0, pocetOperaciiInsert, Nehnutelnost.class);
        ArrayList<Parcela> par = generatorParcela.generujData(0, pocetOperaciiInsert, Parcela.class);

        generatorAll.insertIntoAll(neh);
        generatorAll.insertIntoAll(par);

        int a = generatorNeh.vyhladaj(pocetOperaciiFind);
        int b = generatorParcela.vyhladaj(pocetOperaciiFind);
        int c = generatorAll.vyhladaj(pocetOperaciiFind * 2);
        System.out.printf("Nehnutelnost: %d\nParcela: %d\nVšetky: %d",a,b,c);
    }
}