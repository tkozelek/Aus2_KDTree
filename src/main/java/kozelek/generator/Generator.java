package kozelek.generator;

import kozelek.data.GPS;
import kozelek.data.Nehnutelnost;
import kozelek.data.Parcela;
import kozelek.data.objekt.Entity;
import kozelek.data.objekt.IComparator;
import kozelek.data.objekt.IObject;
import kozelek.tree.KDTree;

import java.util.ArrayList;
import java.util.Random;

public class Generator<T extends Entity> {
    private KDTree<T> tree;
    private Random rand;
    private double x1, x2;
    private double y1, y2;
    private double maxx, maxy;
    private double minx, miny;
    private ArrayList<T> entities;

    public Generator(KDTree<T> tree, double maxx, double maxy, double minx, double miny) {
        this.tree = tree;

        this.rand = new Random();
        this.maxx = maxx;
        this.maxy = maxy;
        this.minx = minx;
        this.miny = miny;
        this.entities = new ArrayList<>();
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

    public ArrayList<T> generujData(int start, int pocet, Class<? extends Entity> clazz) {
        for (int i = start; i <= pocet + start; i++) {
            GPS gps = new GPS(this.randomX(), this.randomY());

            Entity e;
            if (clazz.equals(Nehnutelnost.class)) {
                e = new Nehnutelnost(i, "Nehnutelnost s cislom " + i, gps);
                this.tree.insert((T) e);
            } else {
                e = new Parcela(i, "Parcela s cislom " + i, gps);
                this.tree.insert((T) e);
            }
            this.entities.add((T) e);
        }
        return this.entities;
    }

    public void insertIntoAll(ArrayList<? extends Entity> ent) {
        this.entities.addAll((ArrayList<? extends T>) ent);

        for (Entity e : ent) {
            this.tree.insert((T)e);
        }
    }

    public int vyhladaj(int pocetOperacii) {
        int count = 0;

        for (int i = 0; i < pocetOperacii; i++) {
            T entity;
            T random = this.entities.get(rand.nextInt(this.entities.size()));
            entity = this.tree.find(random);

            if (entity != null && entity.equals(random)) {
                count++;
            } else {
                return count;
            }
        }
        return count;
    }

    public void clear() {
        this.entities.clear();
        this.tree = null;
        this.rand = null;
    }
}
