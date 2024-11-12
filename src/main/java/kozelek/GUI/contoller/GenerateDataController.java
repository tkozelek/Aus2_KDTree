package kozelek.GUI.contoller;

import kozelek.GUI.Wrapper;
import kozelek.GUI.views.AddWindow;
import kozelek.GUI.views.GenerateDataView;
import kozelek.data.GPS;
import kozelek.data.Nehnutelnost;
import kozelek.data.Parcela;

import javax.swing.*;

public class GenerateDataController {
    private GenerateDataView view;
    private Wrapper wrapper;

    private double x1,x2;
    private double y1,y2;
    private int startAtNehnutelnost, startAtParcela;
    private int countNehnutelnost, countParcela;
    private double duplicateNehnutelnost, duplicateParcela;

    public GenerateDataController(GenerateDataView addView, Wrapper wrapper) {
        this.view = addView;
        this.wrapper = wrapper;

        view.getGoBackButton().addActionListener(e -> goBack());
        view.getGenerateButton().addActionListener(e -> generate());
    }

    private void generate() {
        if (this.spracujVstupy()) {
            this.wrapper.generujAVlozObe(this.startAtNehnutelnost, countNehnutelnost, startAtParcela, countParcela, duplicateNehnutelnost, duplicateParcela);
            JOptionPane.showMessageDialog(this.view,
                    "Success.");
        }
    }

    private void goBack() {
        view.dispose();
    }

    private boolean spracujVstupy() {
        if (!this.view.getX1Field().getText().isBlank() && !this.view.getX1Field().getText().isBlank() &&
                !this.view.getX2Field().getText().isBlank() && !this.view.getY2Field().getText().isBlank()) {
            this.x1 = Double.parseDouble(this.view.getX1Field().getText());
            this.y1 = Double.parseDouble(this.view.getY1Field().getText());
            this.x2 = Double.parseDouble(this.view.getX2Field().getText());
            this.y2 = Double.parseDouble(this.view.getY2Field().getText());

            this.duplicateParcela = Double.parseDouble(this.view.getDuplicateParcelaField().getText());
            this.duplicateNehnutelnost = Double.parseDouble(this.view.getDuplicateNehnutelnostField().getText());

            this.startAtNehnutelnost = Integer.parseInt(view.getStartAtNehnutelnost().getText());
            this.startAtParcela = Integer.parseInt(view.getStartAtParcela().getText());
            this.countNehnutelnost = Integer.parseInt(view.getCountNehnutelnost().getText());
            this.countParcela = Integer.parseInt(view.getCountParcela().getText());

            this.wrapper.nastavMaxMin(x1, y1, x2, y2);
            return true;
        }
        JOptionPane.showMessageDialog(this.view,
                "Empty fields.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        return false;
    }
}
