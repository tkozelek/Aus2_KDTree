package kozelek;

import kozelek.data.GPS;
import kozelek.data.Nehnutelnost;
import kozelek.generator.Generator;
import kozelek.tree.KDTree;
import kozelek.tree.TreeWrapper;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        TreeWrapper<Nehnutelnost> tree = new TreeWrapper<>();

        Generator<Nehnutelnost> generator = new Generator<>(tree, 100,100,0,0);

        generator.generujData(0, 5000, Nehnutelnost.class);

        ArrayList<Nehnutelnost> nehnutelnost = generator.vyhladajNehnutelnosti(5000);
        System.out.println(nehnutelnost.size());
    }
}