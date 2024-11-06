package kozelek.GUI;

import kozelek.data.GPS;
import kozelek.data.Nehnutelnost;
import kozelek.data.Parcela;
import kozelek.data.objekt.Entity;
import kozelek.data.objekt.GPSObjekt;
import kozelek.data.objekt.IObject;
import kozelek.tree.KDTree;
import kozelek.tree.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

public class Wrapper {
    private final KDTree<Nehnutelnost> nehnutelnosti;
    private final KDTree<Parcela> parcely;
    private final KDTree<GPSObjekt> vsetky;
    private GeneratorGUI<Nehnutelnost> genNeh;
    private GeneratorGUI<Parcela> genPar;

    private final double maxx = 1000d;
    private final double maxy = 1000d;
    private final double minx = 0d;
    private final double miny = 0d;

    public Wrapper(KDTree<Nehnutelnost> nehnutelnosti, KDTree<Parcela> parcely, KDTree<GPSObjekt> vsetky) {
        this.nehnutelnosti = nehnutelnosti;
        this.parcely = parcely;
        this.vsetky = vsetky;

        genNeh = new GeneratorGUI<>(maxx, maxy, minx, miny);
        genPar = new GeneratorGUI<>(maxx, maxy, minx, miny);
    }

    public void nastavMaxMin(double maxx, double maxy, double minx, double miny) {
        genNeh = new GeneratorGUI<>(maxx, maxy, minx, miny);
        genPar = new GeneratorGUI<>(maxx, maxy, minx, miny);
    }

    public void generujAVlozNehnutelnosti(int start, int pocet, double duplicate) {
        ArrayList<Nehnutelnost> vygenerovane = this.genNeh.generuj(start, pocet, duplicate, Nehnutelnost.class);

        while (!vygenerovane.isEmpty()) {
            Nehnutelnost neh1 = vygenerovane.removeLast();
            Nehnutelnost neh2 = vygenerovane.removeLast();

            this.vlozNehnutelnost(neh1, neh2);
        }
    }

    // program nepodporuje duplikacie
    // zial som nestihol :(
    // takže ak nastavíte duplikáty vyššie ako 0
    // štruktúra môže byť nefunkčná
    // resp nemusí fungovať správne :)

    public void generujAVlozParcely(int start, int pocet, double duplicate) {
        ArrayList<Parcela> vygenerovane = this.genPar.generuj(start, pocet, duplicate, Parcela.class);

        while (!vygenerovane.isEmpty()) { // mozme kludne posledne, kedze su aj tak random
            Parcela par1 = vygenerovane.removeLast();
            Parcela par2 = vygenerovane.removeLast();

            this.vlozParcelu(par1, par2);
        }
    }

    // INSERT
    public void vlozNehnutelnost(Nehnutelnost neh1, Nehnutelnost neh2) {
        // nastavime referenciu vzajomnu
        neh1.setOther(neh2);
        neh2.setOther(neh1);

        // vlozime
        this.nehnutelnosti.insert(neh1);
        this.nehnutelnosti.insert(neh2);

        this.najdiANastavReferencie(neh1);
        this.najdiANastavReferencie(neh2);

        GPSObjekt obj1 = new GPSObjekt(neh1, null);
        GPSObjekt obj2 = new GPSObjekt(neh2, null);

        this.vlozVsetky(obj1, obj2);
    }

    public void vlozParcelu(Parcela par1, Parcela par2) {
        // nastavime referenciu vzajomnu
        par1.setOther(par2);
        par2.setOther(par1);

        // vlozime
        this.parcely.insert(par1);
        this.parcely.insert(par2);

        this.najdiANastavReferencie(par1);
        this.najdiANastavReferencie(par2);

        GPSObjekt obj1 = new GPSObjekt(null, par1);
        GPSObjekt obj2 = new GPSObjekt(null, par2);

        this.vlozVsetky(obj1, obj2);
    }

    public void vlozVsetky(GPSObjekt obj1, GPSObjekt obj2) {
        obj1.setOther(obj2);
        obj2.setOther(obj1);

        this.vsetky.insert(obj1);
        this.vsetky.insert(obj2);
    }

    public void najdiANastavReferencie(IObject object) {
        if (object instanceof Parcela par) {
            ArrayList<Nehnutelnost> nehs = null;
            nehs = this.nehnutelnosti.find(new Nehnutelnost(0, "", par.getGPS())); // nehnutelnost
            if (nehs != null) {
                for (Nehnutelnost neh : nehs) {
                    par.addNehnutelnost(neh);
                    neh.addParcela(par);
                }
            }
        } else if (object instanceof Nehnutelnost neh) {
            ArrayList<Parcela> pars = null;
            pars =  this.parcely.find(new Parcela(0, "", neh.getGPS())); // nehnutelnost
            if (pars != null) {
                for (Parcela par : pars) {
                    par.addNehnutelnost(neh);
                    neh.addParcela(par);
                }
            }
        }
    }

    // FIND
    public ArrayList<GPSObjekt> vyhladajVsetky(GPS gps1, GPS gps2) {
        Nehnutelnost neh = new Nehnutelnost(0, "", gps1);
        Parcela par = new Parcela(0, "", gps1);

        ArrayList<GPSObjekt> found = new ArrayList<>();

        found.addAll(this.vsetky.find(new GPSObjekt(neh, par)));

        neh.setGPS(gps2);
        par.setGPS(gps2);
        found.addAll(this.vsetky.find(new GPSObjekt(neh, par)));

        return found;
    }

    public ArrayList<Nehnutelnost> vyhladatNehnutelnosti(GPS gps) {
        Nehnutelnost neh = new Nehnutelnost(0, "", gps);

        return new ArrayList<>(this.nehnutelnosti.find(neh));
    }

    public ArrayList<Parcela> vyhladatParcely(GPS gps) {
        Parcela par = new Parcela(0, "", gps);

        return new ArrayList<>(this.parcely.find(par));
    }

    // EDIT
    public void upravNehnutelnost(Nehnutelnost old, Nehnutelnost neww) {
        this.nehnutelnosti.edit(old, neww);

        GPSObjekt newObj = new GPSObjekt(neww, null);

        GPSObjekt oldd = this.vsetky.findOneNode(new GPSObjekt(old, null)).getBoundObject();

        this.upravVsetky(oldd, newObj);
    }

    public void upravParcely(Parcela old, Parcela neww) {
        this.parcely.edit(old, neww);

        GPSObjekt newObj = new GPSObjekt(null, neww);

        GPSObjekt oldd = this.vsetky.findOneNode(new GPSObjekt(null, old)).getBoundObject();

        this.upravVsetky(oldd, newObj);
    }

    public void upravVsetky(GPSObjekt old, GPSObjekt neww) {
        // musime zmenit referenciu
        GPSObjekt oldOther = old.getOther();

        oldOther.setOther(neww);
        neww.setOther(oldOther);

        this.vsetky.edit(old, neww);
    }

    // DELETE
    public void vyradNehnutelnost(Nehnutelnost neh) {
        Nehnutelnost other = (Nehnutelnost) neh.getOther();

        this.nehnutelnosti.delete(neh);
        this.nehnutelnosti.delete(other);

        GPSObjekt oldd = this.vsetky.findOneNode(new GPSObjekt(neh, null)).getBoundObject();

        this.vyradVsetky(oldd);
    }

    public void vyradParcelu(Parcela par) {
        Parcela other = (Parcela) par.getOther();

        this.parcely.delete(par);
        this.parcely.delete(other);

        GPSObjekt oldd = this.vsetky.findOneNode(new GPSObjekt(null, par)).getBoundObject();

        this.vyradVsetky(oldd);
    }

    public void vyradVsetky(GPSObjekt obj) {
        GPSObjekt other = obj.getOther();

        this.vsetky.delete(obj);
        this.vsetky.delete(other);
    }

    public void exportNehnutelnosti(String filePath) {
        File exportsDir = new File("exports");
        if (!exportsDir.exists()) {
            try {
                exportsDir.mkdir();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        filePath = "exports/" + filePath;
        ArrayList<Node<Nehnutelnost>> items = this.nehnutelnosti.levelOrder(this.nehnutelnosti.getRoot()); // dá všetky objekty

        File file = new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(genNeh.toCsvString());
            if (items != null) {
                for (Node<Nehnutelnost> objekt : items) {
                    writer.write(objekt.getBoundObject().toCsvString());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportParcely(String filePath) {
        File exportsDir = new File("exports");
        if (!exportsDir.exists()) {
            try {
                exportsDir.mkdir();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        filePath = "exports/" + filePath;
        ArrayList<Node<Parcela>> items = this.parcely.levelOrder(this.parcely.getRoot()); // dá všetky objekty

        File file = new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(genPar.toCsvString());
            if (items != null) {
                for (Node<Parcela> objekt : items) {
                    writer.write(objekt.getBoundObject().toCsvString());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void importCsv(String filePath, Class<? extends IObject> clazz) {
        filePath = "exports/" + filePath;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            String[] split = line.split(Entity.CSV_DELIMITER);
            double maxx = Double.parseDouble(split[0]);
            double maxy = Double.parseDouble(split[1]);
            double minx = Double.parseDouble(split[2]);
            double miny = Double.parseDouble(split[3]);
            this.nastavMaxMin(maxx, maxy, minx, miny);

            // id, popis, x, y, uuid, (other)x, y, uuid

            while ((line = reader.readLine()) != null) {
                split = line.split(Entity.CSV_DELIMITER);

                int id = Integer.parseInt(split[0]);
                String desc = split[1];
                double x = Double.parseDouble(split[2]);
                double y = Double.parseDouble(split[3]);
                UUID uuid = UUID.fromString(split[4]);

                double otherx = Double.parseDouble(split[5]);
                double othery = Double.parseDouble(split[6]);
                UUID otheruuid = UUID.fromString(split[7]);

                if (clazz.equals(Nehnutelnost.class)) {
                    Nehnutelnost neh = new Nehnutelnost(id, desc, new GPS(x, y));
                    neh.setUuid(uuid);

                    Nehnutelnost other = new Nehnutelnost(0, "", new GPS(otherx, othery));
                    other.setUuid(otheruuid);
                    this.setOtherAndInsertNehnutelnost(neh, other);
                } else if (clazz.equals(Parcela.class)) {
                    Parcela par = new Parcela(id, desc, new GPS(x, y));
                    par.setUuid(uuid);

                    Parcela other = new Parcela(0, "", new GPS(otherx, othery));
                    other.setUuid(otheruuid);
                    this.setOtherAndInsertParcela(par, other);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setOtherAndInsertNehnutelnost(Nehnutelnost neh, Nehnutelnost other) {
        Node<Nehnutelnost> found = this.nehnutelnosti.findOneNode(other);
        Nehnutelnost n = null;
        if (found != null) {
            n = found.getBoundObject();
            n.setOther(neh);
            neh.setOther(n);
        }
        this.nehnutelnosti.insert(neh);

        this.najdiANastavReferencie(neh);

        if (found != null && n.getOther() != null) {
            GPSObjekt obj1 = new GPSObjekt(neh, null);
            GPSObjekt obj2 = new GPSObjekt(n, null);
            this.vlozVsetky(obj1, obj2);
        }
    }

    public void setOtherAndInsertParcela(Parcela par, Parcela other) {
        Node<Parcela> found = this.parcely.findOneNode(other);
        Parcela n = null;
        if (found != null) {
            n = found.getBoundObject();
            n.setOther(par);
            par.setOther(n);
        }
        this.parcely.insert(par);

        this.najdiANastavReferencie(par);

        if (found != null && n.getOther() != null) {
            GPSObjekt obj1 = new GPSObjekt(null, par);
            GPSObjekt obj2 = new GPSObjekt(null, n);
            this.vlozVsetky(obj1, obj2);
        }
    }

    public ArrayList<Node<Nehnutelnost>> getNehnutelnostiLevelOrder() {
        return this.nehnutelnosti.levelOrder(this.nehnutelnosti.getRoot());
    }

    public ArrayList<Node<Parcela>> getParcelyLevelOrder() {
        return this.parcely.levelOrder(this.parcely.getRoot());
    }

    public ArrayList<Node<GPSObjekt>> getVsetkyLevelOrder() {
        return this.vsetky.levelOrder(this.vsetky.getRoot());
    }

}
