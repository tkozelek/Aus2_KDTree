package kozelek.GUI.contoller;

import kozelek.GUI.Wrapper;
import kozelek.GUI.views.AddWindow;
import kozelek.GUI.views.EditView;
import kozelek.GUI.views.GenerateDataView;
import kozelek.GUI.views.MainWindow;
import kozelek.data.GPS;
import kozelek.data.Nehnutelnost;
import kozelek.data.Parcela;
import kozelek.data.objekt.Entity;
import kozelek.data.objekt.GPSObjekt;
import kozelek.data.objekt.interfaces.IHasOther;
import kozelek.data.objekt.interfaces.IObject;

import javax.swing.*;
import java.util.ArrayList;


public class MainController<T extends IObject> {
    private Wrapper wrapper;
    private MainWindow view;
    private DefaultListModel<IObject> model;
    private double x1,y1,x2,y2;

    public MainController(Wrapper wrapper, MainWindow mainWindowView) {
        this.wrapper = wrapper;
        this.view = mainWindowView;
        // find
        mainWindowView.getFindAllButton().addActionListener(e -> findAll());
        mainWindowView.getFindParcelasButton().addActionListener(e -> findParcels());
        mainWindowView.getFindPropertiesButton().addActionListener(e -> findProperties());

        mainWindowView.getExportButton().addActionListener(e -> exportToCsv());
        mainWindowView.getImportButton().addActionListener(e -> importFromCsv());

        mainWindowView.getEditButton().addActionListener(e -> editData());
        mainWindowView.getGenerateDataButton().addActionListener(e -> generateData());
        mainWindowView.getDeleteButton().addActionListener(e -> deleteSelected());

        mainWindowView.getAddButton().addActionListener(e -> addNew());
        mainWindowView.setVisible(true);

        this.model = new DefaultListModel<IObject>();
    }


    private void editData() {
        IObject item = this.getSelectedItem();
        if (item instanceof GPSObjekt) {
            JOptionPane.showMessageDialog(this.view,
                    "Cant edit GPSOBJEKT.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (item != null) {
            EditView<T> editview = new EditView<T>((T) item);
            new EditController<>(editview, wrapper, this);
            editview.setVisible(true);
        }
    }

    private IObject getSelectedItem() {
        int index = this.view.getList1().getSelectedIndex();
        T item = null;
        if (index >= 0)
            return this.model.get(index);
        else
            JOptionPane.showMessageDialog(this.view,
                    "Non selected.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        return null;
    }

    private void addNew() {
        AddWindow addWindow = new AddWindow();
        new AddController(addWindow, wrapper);
        addWindow.setVisible(true);
    }

    private void delete(int index, IObject item) {
        if (item == null) {
            return;
        }
        this.model.remove(index);
        if (item instanceof IHasOther i) {
            IHasOther other = i.getOther();

            int indexOfOther = this.model.indexOf(other);
            if (indexOfOther >= 0) {
                this.model.remove(indexOfOther);
            }
        }

        if (item instanceof Nehnutelnost neh) {
            this.wrapper.vyradNehnutelnost(neh);
            this.wrapper.vyradVsetky(new GPSObjekt(neh, null));
        } else if (item instanceof Parcela par) {
            this.wrapper.vyradParcelu(par);
            this.wrapper.vyradVsetky(new GPSObjekt(null, par));
        }

        if (item instanceof GPSObjekt gps) {
            this.wrapper.vyradVsetky(gps);
            if (gps.getNehnutelnost() != null) {
                this.wrapper.vyradNehnutelnost(gps.getNehnutelnost());
            } else {
                this.wrapper.vyradParcelu(gps.getParcela());
            }
        }
        this.updateList();
    }

    private void deleteSelected() {
        int index = this.view.getList1().getSelectedIndex();
        IObject item = this.getSelectedItem();
        this.delete(index, item);
    }

    private void generateData() {
        GenerateDataView generateDataView = new GenerateDataView();
        new GenerateDataController(generateDataView, wrapper);
        generateDataView.setVisible(true);
    }

    private void exportToCsv() {
        if (this.view.getCsvField().getText().isEmpty()) {
            JOptionPane.showMessageDialog(this.view,
                    "Empty.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        String filename = this.view.getCsvField().getText();
        String fileNameNeh = filename + "_neh.csv";
        String filenamePar = filename + "_par.csv";
        this.wrapper.exportParcely(filenamePar);
        this.wrapper.exportNehnutelnosti(fileNameNeh);
        JOptionPane.showMessageDialog(this.view,
                "Success.",
                "Success",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void importFromCsv() {
        if (this.view.getCsvField().getText().isEmpty()) {
            JOptionPane.showMessageDialog(this.view,
                    "Empty.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        String filename = this.view.getCsvField().getText();
        String fileNameNeh = filename + "_neh.csv";
        String filenamePar = filename + "_par.csv";
        this.wrapper.importCsv(fileNameNeh, Nehnutelnost.class);
        this.wrapper.importCsv(filenamePar, Parcela.class);
        JOptionPane.showMessageDialog(this.view,
                "Success.",
                "Success",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void findProperties() {
        if (this.spracujVstupyOneLine()) {
            ArrayList<Nehnutelnost> properties = this.wrapper.vyhladatNehnutelnosti(new GPS(x1, y1));
            if (properties != null) {
                ArrayList<IObject> objects = new ArrayList<>(properties);
                updateList(objects);
            } else {
                JOptionPane.showMessageDialog(this.view,
                        "Not found.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void findParcels() {
        if (this.spracujVstupyOneLine()) {
            ArrayList<Parcela> parcels = this.wrapper.vyhladatParcely(new GPS(x1, y1));
            if (parcels != null) {
                ArrayList<IObject> objects = new ArrayList<>(parcels);
                updateList(objects);
            } else {
                JOptionPane.showMessageDialog(this.view,
                        "Not found.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void findAll() {
        if (this.spracujVstupy()) {
            ArrayList<GPSObjekt> gpsobjects = this.wrapper.vyhladajVsetky(new GPS(x1, y1), new GPS(x2,y2));
            if (gpsobjects != null) {
                ArrayList<IObject> objects = new ArrayList<>(gpsobjects);
                updateList(objects);
            } else {
                JOptionPane.showMessageDialog(this.view,
                        "Not found.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean spracujVstupy() {
        if (!this.view.getX1Field().getText().isBlank() && !this.view.getY1Field().getText().isBlank() &&
                !this.view.getX2Field().getText().isBlank() && !this.view.getY2Field().getText().isBlank()) {
            this.x1 = Double.parseDouble(this.view.getX1Field().getText());
            this.y1 = Double.parseDouble(this.view.getY1Field().getText());
            this.x2 = Double.parseDouble(this.view.getX2Field().getText());
            this.y2 = Double.parseDouble(this.view.getY2Field().getText());
            return true;
        } else {
            JOptionPane.showMessageDialog(this.view,
                    "Empty fields.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private boolean spracujVstupyOneLine() {
        if (!this.view.getX1Field().getText().isBlank() && !this.view.getY1Field().getText().isBlank()) {
            this.x1 = Double.parseDouble(this.view.getX1Field().getText());
            this.y1 = Double.parseDouble(this.view.getY1Field().getText());
            return true;
        } else {
            JOptionPane.showMessageDialog(this.view,
                    "Empty fields.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }

    public void replaceInModel(T old, T newItem) {
        int index = this.model.indexOf(old);
        if (index >= 0) {
            this.model.remove(index);
            this.model.insertElementAt(newItem, index);
            this.updateList();
        }
    }

    private void addToModel(ArrayList<IObject> objekty) {
        if (objekty != null && !objekty.isEmpty()) {
            model.clear();
            model.addAll(objekty);
        } else {
            JOptionPane.showMessageDialog(this.view,
                    "Not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateList(ArrayList<IObject> objekty) {
        this.addToModel(objekty);
        this.updateList();
    }

    private void updateList() {
        if (!model.isEmpty()) {
            this.view.getList1().setModel(model);
        }
    }
}
