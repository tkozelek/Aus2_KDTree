package kozelek.generator;

import kozelek.data.GPS;
import kozelek.data.Nehnutelnost;
import kozelek.data.Parcela;
import kozelek.data.objekt.Entity;
import kozelek.data.objekt.GPSObjekt;
import kozelek.data.objekt.IObject;
import kozelek.tree.KDTree;
import kozelek.tree.Node;

import java.util.ArrayList;
import java.util.Random;

public class Generator<T extends IObject> {
    private KDTree<T> tree;
    private Random rand;
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

    public Node<T> getKoren() {
        return this.tree.getRoot();
    }

    public void setSeed(long seed) { this.rand.setSeed(seed); }

    public double randomX() {
        return (this.minx + (this.maxx - this.minx) * this.rand.nextDouble());
    } // (this.x1 + (this.x2 - this.x1) * this.rand.nextDouble());

    public double randomY() {
        return (this.miny + (this.maxy - this.miny) * this.rand.nextDouble());
    } // return (this.y1 + (this.y2 - this.y1) * this.rand.nextDouble());

    public GPS randomGPS() {
        return new GPS(this.randomX(), this.randomY());
    }

    public T getRandomEntity() {
        return this.entities.get(this.rand.nextInt(this.entities.size()));
    }

    public ArrayList<T> generujData(int start, int pocet, Class<? extends IObject> clazz, double duplicate) {
        for (int i = start; i < pocet + start; i++) {
            double rand = this.rand.nextDouble();
            GPS gps1 = this.randomGPS();
            if (!entities.isEmpty() && rand < duplicate) {
                T entity = this.getRandomEntity();
                if (entity instanceof Entity) {
                    gps1 = ((Entity) entity).getGPS();
                }
            }

            Entity e;
            if (clazz.equals(Nehnutelnost.class)) {
                e = new Nehnutelnost(i, "Nehnutelnost s cislom " + i, gps1);
                this.tree.insert((T) e);
                this.entities.add((T) e);
            }  else {
                e = new Parcela(i, "Parcela s cislom " + i, gps1);
                this.tree.insert((T) e);
                this.entities.add((T) e);
            }

        }
        return this.entities;
    }

    public ArrayList<T> generujObe(int start, int pocet) {
        for (int i = 0; i < pocet; i++) {
            GPS gps1 = this.randomGPS();
            GPS gps2 = this.randomGPS();

            GPSObjekt obj = null;
            GPSObjekt obj2 = null;
            if (this.rand.nextBoolean()) { // neh
                Nehnutelnost neh1 = new Nehnutelnost(i, "Neh " + i, gps1);
                Nehnutelnost neh2 = new Nehnutelnost(i, "Neh " + i, gps2);

                obj = new GPSObjekt(neh1, null);
                obj2 = new GPSObjekt(neh2, null);

                obj.setOther(obj2);
                obj2.setOther(obj);

                this.tree.insert((T) obj);
                this.tree.insert((T) obj2);
            } else {
                Parcela par1 = new Parcela(i, "Neh " + i, gps1);
                Parcela par2 = new Parcela(i, "Neh " + i, gps2);

                obj = new GPSObjekt(null, par1);
                obj2 = new GPSObjekt(null, par2);

                obj.setOther(obj2);
                obj2.setOther(obj);

                this.tree.insert((T) obj);
                this.tree.insert((T) obj2);
            }
            this.entities.add((T) obj);
            this.entities.add((T) obj2);
        }
        return this.entities;
    }

    public int vyhladaj(int pocetOperacii) {
        int count = 0;

        for (int i = 0; i < pocetOperacii; i++) {
            ArrayList<T> found;
            T random = this.entities.get(rand.nextInt(this.entities.size()));
            found = this.tree.find(random);

            for (T obj : found) {
                if (obj != null && obj.equalUUID(random)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    public ArrayList<Node<T>> getInOrder() {
        return this.tree.inOrder(this.tree.getRoot());
    }

    public int vymaz(int pocetOperacii) {
        int count = 0;
        for (int i = 0; i < pocetOperacii; i++) {

            T entity;
            T random = this.entities.remove(rand.nextInt(this.entities.size()));
            T e = null;
            entity = this.tree.delete(random);

            if (entity != null && entity.equalUUID(random)) {
                count++;
            } else {
                return count;
            }
        }
        return count;
    }

    public void edit(int pocetOperacii) {
        for (int i = 0; i < pocetOperacii; i++) {
            T random = this.entities.remove(rand.nextInt(this.entities.size()));
            T rand = null;
            T rand2 = null;
            if (random instanceof Nehnutelnost) {
                rand = (T) new Nehnutelnost(555555,"aasdasd", this.randomGPS());
                rand2 = (T) new Nehnutelnost(555555,"aasdasd", this.randomGPS());
            } else if (random instanceof Parcela) {
                rand = (T) new Parcela(555555,"aasdasd", this.randomGPS());
                rand2 = (T) new Parcela(555555,"aasdasd", this.randomGPS());
            }
            this.entities.add(rand);
            this.entities.add(rand2);
            this.tree.edit(random, rand);
            if (random instanceof Entity) {
                this.tree.edit((T)((Entity) random).getOther(), rand2);
            }
        }
    }

    public void editGPS(int pocetOperacii) {
        for (int i = 0; i < pocetOperacii; i++) {
            GPSObjekt random = (GPSObjekt) this.entities.remove(this.rand.nextInt(this.entities.size()));
            GPSObjekt random2 = random.getOther();
            this.entities.remove(random2);

            GPSObjekt neww = null;
            GPSObjekt neww2 = null;

            if (random.getNehnutelnost() != null) {
                Nehnutelnost neh1 = new Nehnutelnost(999999, "hahaha", this.randomGPS());
                Nehnutelnost neh2 = new Nehnutelnost(999999, "hahaha", this.randomGPS());

                neww = new GPSObjekt(neh1, null);
                neww2 = new GPSObjekt(neh2, null);
            } else {
                Parcela par1 = new Parcela(999999, "hahaha", this.randomGPS());
                Parcela par2 = new Parcela(999999, "hahaha", this.randomGPS());

                neww = new GPSObjekt(null, par1);
                neww2 = new GPSObjekt(null, par2);
            }

            if (this.rand.nextBoolean()) {
                if (random.getNehnutelnost() != null) {
                    Nehnutelnost neh1 = new Nehnutelnost(999999, "hahaha", this.randomGPS());
                    Nehnutelnost neh2 = new Nehnutelnost(999999, "hahaha", this.randomGPS());

                    neww = new GPSObjekt(neh1, null);
                    neww2 = new GPSObjekt(neh2, null);
                } else {
                    Parcela par1 = new Parcela(999999, "hahaha", this.randomGPS());
                    Parcela par2 = new Parcela(999999, "hahaha", this.randomGPS());

                    neww = new GPSObjekt(null, par1);
                    neww2 = new GPSObjekt(null, par2);
                }
            } else {
                if (random.getNehnutelnost() != null) {
                    Nehnutelnost neh1 = new Nehnutelnost(999999, "hahaha", random.getGPS());
                    Nehnutelnost neh2 = new Nehnutelnost(999999, "hahaha", random2.getGPS());

                    neww = new GPSObjekt(neh1, null);
                    neww2 = new GPSObjekt(neh2, null);
                } else {
                    Parcela par1 = new Parcela(999999, "hahaha", random.getGPS());
                    Parcela par2 = new Parcela(999999, "hahaha", random2.getGPS());

                    neww = new GPSObjekt(null, par1);
                    neww2 = new GPSObjekt(null, par2);
                }
            }
            neww.setOther(neww2);
            neww2.setOther(neww);

            this.tree.edit((T) random, (T) neww);
            this.tree.edit((T) random.getOther(), (T) neww2);

            this.entities.add((T) neww);
            this.entities.add((T) neww2);
        }
    }

    public ArrayList<Node<T>> getLevelOrder() {
        return this.tree.levelOrder(this.getKoren());
    }

    public void clear() {
        this.entities.clear();
        this.tree = null;
        this.rand = null;
    }
}
