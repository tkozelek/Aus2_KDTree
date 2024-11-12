package kozelek.GUI;

import kozelek.data.GPS;
import kozelek.data.Nehnutelnost;
import kozelek.data.Parcela;
import kozelek.data.objekt.Entity;
import kozelek.data.objekt.interfaces.IObject;

import java.util.ArrayList;
import java.util.Random;

public class GeneratorGUI<T extends IObject> {
    private Random rand;
    private double maxx, maxy;
    private double minx, miny;
    private ArrayList<T> entities;

    public GeneratorGUI(double maxx, double maxy, double minx, double miny) {
        this.rand = new Random();
        this.maxx = maxx;
        this.maxy = maxy;
        this.minx = minx;
        this.miny = miny;
        this.entities = new ArrayList<>();
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

    public ArrayList<T> generuj(int start, int pocet, double duplicate, Class<? extends IObject> clazz) {
        for (int i = start; i < pocet + start; i++) {
            double rand = this.rand.nextDouble();
            GPS gps1 = this.randomGPS();
            GPS gps2 = this.randomGPS();
            if (!entities.isEmpty() && rand < duplicate) {
                T entity = this.getRandomEntity();
                if (entity instanceof Entity) {
                    if (this.rand.nextBoolean())
                        gps1 = ((Entity) entity).getGPS();
                    else
                        gps2 = ((Entity) entity).getGPS();
                }
            }

            IObject e;
            IObject e2;
            if (clazz.equals(Nehnutelnost.class)) {
                e = new Nehnutelnost(i, "Nehnutelnost s cislom " + i, gps1);
                e2 = new Nehnutelnost(i, "Nehnutelnost s cislom " + i, gps2);

                this.entities.add((T) e);
                this.entities.add((T) e2);
            } else if (clazz.equals(Parcela.class)) {
                e = new Parcela(i, "Parcela s cislom " + i, gps1);
                e2 = new Parcela(i, "Parcela s cislom " + i, gps2);

                this.entities.add((T) e);
                this.entities.add((T) e2);
            }

        }
        return this.entities;
    }


    public double getMaxx() {
        return maxx;
    }

    public void setMaxx(double maxx) {
        this.maxx = maxx;
    }

    public double getMaxy() {
        return maxy;
    }

    public void setMaxy(double maxy) {
        this.maxy = maxy;
    }

    public double getMinx() {
        return minx;
    }

    public void setMinx(double minx) {
        this.minx = minx;
    }

    public double getMiny() {
        return miny;
    }

    public void setMiny(double miny) {
        this.miny = miny;
    }

    public String toCsvString() {
        return "" +
                maxx + Entity.CSV_DELIMITER +
                maxy + Entity.CSV_DELIMITER +
                minx + Entity.CSV_DELIMITER +
                miny + "\n";
    }
}
