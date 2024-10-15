package kozelek;

import kozelek.data.GPS;
import kozelek.data.Nehnutelnost;
import kozelek.data.Parcela;
import kozelek.data.objekt.Entity;
import kozelek.generator.Generator;
import kozelek.tree.KDTree;
import kozelek.tree.TreeWrapper;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static Scanner input;
    public static void main(String[] args) {
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
            TreeWrapper<Nehnutelnost> treeNehnutelnost = new TreeWrapper<>();
            TreeWrapper<Parcela> treeParcela = new TreeWrapper<>();
            TreeWrapper<Entity> treeAll = new TreeWrapper<>();

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

            if (a < 5000 || b < 5000) {
                System.out.printf("%d %d\n", a, b);
                System.out.println(i);
                break;
            }

            generatorNeh.clear();
            generatorParcela.clear();
            generatorAll.clear();
        }
    }

    public static void test() {
        TreeWrapper<Nehnutelnost> treeNehnutelnost = new TreeWrapper<>();
        TreeWrapper<Parcela> treeParcela = new TreeWrapper<>();
        TreeWrapper<Entity> treeAll = new TreeWrapper<>();

        Generator<Nehnutelnost> generatorNeh = new Generator<>(treeNehnutelnost, 100, 100, 0, 0);
        Generator<Parcela> generatorParcela = new Generator<>(treeParcela, 100, 100, 0, 0);
        Generator<Entity> generatorAll = new Generator<>(treeAll, 100, 100, 0, 0);

        ArrayList<Nehnutelnost> neh = generatorNeh.generujData(0, 5000, Nehnutelnost.class);
        ArrayList<Parcela> par = generatorParcela.generujData(0, 5000, Parcela.class);

        generatorAll.insertIntoAll(neh);
        generatorAll.insertIntoAll(par);

        int a = generatorNeh.vyhladaj(5000);
        int b = generatorParcela.vyhladaj(5000);
        int c = generatorAll.vyhladaj(10000);
        System.out.printf("%d %d %d",a,b,c);
    }
}