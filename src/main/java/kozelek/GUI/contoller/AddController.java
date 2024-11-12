package kozelek.GUI.contoller;

import kozelek.GUI.Wrapper;
import kozelek.GUI.views.AddWindow;
import kozelek.data.GPS;
import kozelek.data.Nehnutelnost;
import kozelek.data.Parcela;

import javax.swing.*;

public class AddController {
    private AddWindow view;
    private Wrapper wrapper;

    public AddController(AddWindow addView, Wrapper wrapper) {
        this.view = addView;
        this.wrapper = wrapper;

        addView.getSubmitAddButton().addActionListener(e -> handleSave());
        addView.getBackButton().addActionListener(e -> goBack());
    }

    private void goBack() {
        this.view.dispose();
    }

    private void handleSave() {
        try {
            int id = Integer.parseInt(view.getCisloField().getText());
            String desc = view.getDescField().getText();
            double x1 = Double.parseDouble(view.getX1Field().getText());
            double x2 = Double.parseDouble(view.getX2Field().getText());
            double y1 = Double.parseDouble(view.getY1Field().getText());
            double y2 = Double.parseDouble(view.getY2Field().getText());


            if (view.getJeNehnutelnostRadio().isSelected()) {
                Nehnutelnost neh1 = new Nehnutelnost(id, desc, new GPS(x1, y1));
                Nehnutelnost neh2 = new Nehnutelnost(id, desc, new GPS(x2, y2));

                wrapper.vlozNehnutelnost(neh1, neh2);
            } else {
                Parcela par1 = new Parcela(id, desc, new GPS(x1, y1));
                Parcela par2 = new Parcela(id, desc, new GPS(x2, y2));

                wrapper.vlozParcelu(par1, par2);
            }
            JOptionPane.showMessageDialog(this.view,
                    "Success.");
        } catch (NumberFormatException ex) {
            view.showError("Invalid GPS coordinates");
        }
    }
}
