package kozelek.GUI.contoller;

import kozelek.GUI.Wrapper;
import kozelek.GUI.views.AddWindow;
import kozelek.GUI.views.EditView;
import kozelek.GUI.views.GenerateDataView;
import kozelek.GUI.views.MainWindow;
import kozelek.data.Nehnutelnost;
import kozelek.data.objekt.IObject;

import javax.swing.*;
import java.util.ArrayList;


public class MainController<T extends IObject> {
    private Wrapper wrapper;
    private MainWindow mainWindowView;
    private DefaultListModel<T> model;

    public MainController(Wrapper wrapper, MainWindow mainWindowView) {
        this.wrapper = wrapper;
        this.mainWindowView = mainWindowView;
        // find
        mainWindowView.getFindAllButton().addActionListener(e -> findAll());
        mainWindowView.getFindParcelasButton().addActionListener(e -> findParcels());
        mainWindowView.getFindPropertiesButton().addActionListener(e -> findProperties());

        mainWindowView.getExportButton().addActionListener(e -> exportToCsv());

        mainWindowView.getEditButton().addActionListener(e -> editData());
        mainWindowView.getGenerateDataButton().addActionListener(e -> generateData());
        mainWindowView.getDeleteButton().addActionListener(e -> deleteSelected());

        mainWindowView.getAddButton().addActionListener(e -> addNew());
        mainWindowView.setVisible(true);

        this.model = new DefaultListModel<>();
    }

    private void editData() {
        Nehnutelnost neh1 = new Nehnutelnost(0,"0", null);
        EditView<IObject> editView = new EditView<>(neh1);
        new EditController<>(editView, wrapper, this);
        editView.setVisible(true);
    }

    private void addNew() {
        AddWindow addWindow = new AddWindow();
        new AddController(addWindow, wrapper, this);
        addWindow.setVisible(true);
    }

    private void deleteSelected() {

    }

    private void generateData() {
        GenerateDataView generateDataView = new GenerateDataView();
        new GenerateDataController(generateDataView, wrapper, this);
        generateDataView.setVisible(true);
    }

    private void exportToCsv() {

    }

    private void findProperties() {

    }

    private void findParcels() {

    }

    private void findAll() {

    }

    private void addToModel(ArrayList<T> objekty) {
        if (objekty != null && !objekty.isEmpty()) {
            model.clear();
            model.addAll(objekty);
        } else {
            JOptionPane.showMessageDialog(this.mainWindowView,
                    "Not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateList(ArrayList<T> objekty) {
        this.addToModel(objekty);
        this.updateList();
    }

    private void updateList() {
        if (!model.isEmpty()) {
            this.mainWindowView.getList1().setModel(model);
        }
    }
}
