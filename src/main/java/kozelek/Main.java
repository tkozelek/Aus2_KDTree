package kozelek;

import kozelek.data.GPS;
import kozelek.data.Nehnutelnost;
import kozelek.tree.KDTree;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        KDTree<Nehnutelnost> tree = new KDTree<>();
        Random rand = new Random(1);

        int x = 100;
        int y = 100;

        long startTime = System.nanoTime();

        for (int i = 0; i < 500000; i++) {
            Nehnutelnost neh = new Nehnutelnost(i, i + " ahoj" ,new GPS(rand.nextInt(x), rand.nextInt(y)),
                    new GPS(rand.nextInt(x), rand.nextInt(y)));
            tree.insert(neh);
        }

        long endTime = System.nanoTime();

        long elapsedTime = (endTime - startTime) / 1_000_000;
        System.out.println("Insertion completed in " + elapsedTime + " milliseconds.");

        System.out.println("ahoj");
    }
}