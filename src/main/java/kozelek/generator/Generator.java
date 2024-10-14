package kozelek.generator;

import kozelek.data.GPS;
import kozelek.data.Nehnutelnost;
import kozelek.data.Parcela;
import kozelek.data.objekt.Entity;
import kozelek.tree.TreeWrapper;

import java.util.ArrayList;
import java.util.Random;

public class Generator<T extends Entity> {
    private TreeWrapper<T> tree;
    private Random rand;
    private double x1, x2;
    private double y1, y2;
    private double maxx, maxy;
    private double minx, miny;
    private ArrayList<GPS> tl, br;

    public Generator(TreeWrapper<T> tree, double maxx, double maxy, double minx, double miny) {
        this.tree = tree;
        this.rand = new Random();
        this.maxx = maxx;
        this.maxy = maxy;
        this.minx = minx;
        this.miny = miny;
        this.tl = new ArrayList<>();
        this.br = new ArrayList<>();
    }

    public void setSeed(long seed) { this.rand.setSeed(seed); }

    public double randomX() {
        return this.rand.nextDouble(this.minx, this.maxx);
    } // (this.x1 + (this.x2 - this.x1) * this.rand.nextDouble());

    public double randomY() {
        return this.rand.nextDouble(this.miny, this.maxy);
    } // return (this.y1 + (this.y2 - this.y1) * this.rand.nextDouble());

    public GPS randomGPS() {
        return new GPS(this.randomX(), this.randomY());
    }

    public void generujData(int start, int pocet, Class<? extends Entity> clazz) {
        for (int i = start; i <= pocet + start; i++) {
            GPS tl = new GPS(this.randomX(), this.randomY());
            GPS br = new GPS(this.randomX(), this.randomY());

            this.br.add(br);
            this.tl.add(tl);

            if (clazz.equals(Nehnutelnost.class))
                this.tree.insert((T) new Nehnutelnost(i, "Nehnutelnost s cislom " + i, tl, br));
            else
                this.tree.insert((T) new Parcela(i, "Parcela s cislom " + i, tl, br));
        }
    }

    public ArrayList<T> vyhladajNehnutelnosti(int pocetOperacii) {
        ArrayList<T> found = new ArrayList<>();
        for (int i = 0; i < pocetOperacii; i++) {
            boolean isLeft = rand.nextBoolean();
            T neh = null;
            if (isLeft) {
                if (rand.nextBoolean()) {
                    neh = this.tree.find(this.randomGPS(), true);
                } else {
                    neh = this.tree.find(this.tl.get(rand.nextInt(this.tl.size())), true);
                }
            } else {
                neh = this.tree.find(this.br.get(rand.nextInt(this.br.size())), false);
            }

            if (neh != null) {
                found.add(neh);
            }
            neh = null;

        }
        return found;
    }

//    public ArrayList<T> vyhladajParcely(int pocetOperacii) {
//        ArrayList<T> found = new ArrayList<>();
//        for (int i = 0; i < pocetOperacii; i++) {
//            GPS point = this.randomGPS();
//            ArrayList<T> temp = this.tree.findObjectsContaining(point, null);
//            if (temp != null) {
//                found.addAll(this.tree.filter(temp, Parcela.class));
//            }
//        }
//        return found;
//    }
}
