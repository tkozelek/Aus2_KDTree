package kozelek.GUI.contoller;

import kozelek.GUI.Wrapper;
import kozelek.GUI.views.AddWindow;
import kozelek.data.GPS;
import kozelek.data.Nehnutelnost;
import kozelek.data.Parcela;

import javax.swing.*;

public class AddController {
    private AddWindow addView;
    private Wrapper wrapper;
    private MainController mainController;

    public AddController(AddWindow addView, Wrapper wrapper, MainController mainController) {
        this.addView = addView;
        this.wrapper = wrapper;
        this.mainController = mainController;

        addView.getSubmitAddButton().addActionListener(e -> handleSave());
        addView.getBackButton().addActionListener(e -> goBack());
    }

    private void goBack() {
        this.addView.dispose();
    }

    private void handleSave() {
        try {
            int id = Integer.parseInt(addView.getCisloField().getText());
            String desc = addView.getDescField().getText();
            double x1 = Double.parseDouble(addView.getX1Field().getText());
            double x2 = Double.parseDouble(addView.getX2Field().getText());
            double y1 = Double.parseDouble(addView.getY1Field().getText());
            double y2 = Double.parseDouble(addView.getY2Field().getText());


            if (addView.getJeNehnutelnostRadio().isSelected()) {
                Nehnutelnost neh1 = new Nehnutelnost(id, desc, new GPS(x1, y1));
                Nehnutelnost neh2 = new Nehnutelnost(id, desc, new GPS(x2, y2));

                wrapper.vlozNehnutelnost(neh1, neh2);
            } else {
                Parcela par1 = new Parcela(id, desc, new GPS(x1, y1));
                Parcela par2 = new Parcela(id, desc, new GPS(x2, y2));

                wrapper.vlozParcelu(par1, par2);
            }
            JOptionPane.showMessageDialog(this.addView,
                    "Success.");
        } catch (NumberFormatException ex) {
            addView.showError("Invalid GPS coordinates");
        }
    }
}
