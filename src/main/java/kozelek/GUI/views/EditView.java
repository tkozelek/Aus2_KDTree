package kozelek.GUI.views;

import kozelek.data.Nehnutelnost;
import kozelek.data.Parcela;
import kozelek.data.objekt.Entity;
import kozelek.data.objekt.IObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EditView<T extends IObject> extends JFrame {
    private JPanel panel1;
    private JButton backButton;
    private JTextField cisloField;
    private JTextField descField;
    private JTextField x1Field;
    private JTextField y1Field;
    private JTextField x2Field;
    private JTextField y2Field;
    private JButton submitEditButton;
    private JRadioButton jeNehnutelnostRadio;
    private JList list1;
    private T data;

    public EditView(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    private void setValues() {
        if (data instanceof Nehnutelnost neh) {
            this.x1Field.setText(String.valueOf(neh.getGPS().getX()));
            this.y1Field.setText(String.valueOf(neh.getGPS().getY()));

            Entity other = neh.getOther();
            this.x2Field.setText(String.valueOf(other.getGPS().getX()));
            this.y2Field.setText(String.valueOf(other.getGPS().getY()));

            this.descField.setText(neh.getDescription());
            this.cisloField.setText(String.valueOf(neh.getId()));

            this.jeNehnutelnostRadio.setSelected(true);
        } else if (data instanceof Parcela par) {
            this.x1Field.setText(String.valueOf(par.getGPS().getX()));
            this.y1Field.setText(String.valueOf(par.getGPS().getY()));

            Entity other = par.getOther();
            this.x2Field.setText(String.valueOf(other.getGPS().getX()));
            this.y2Field.setText(String.valueOf(other.getGPS().getY()));

            this.descField.setText(par.getDescription());
            this.cisloField.setText(String.valueOf(par.getId()));

            this.jeNehnutelnostRadio.setSelected(false);
        }
        this.updateList();
    }

    private void updateList() {
        DefaultListModel<T> model = new DefaultListModel<>();

        ArrayList<T> objekty = null;
        if (data instanceof Nehnutelnost neh) {
            objekty = (ArrayList<T>) neh.getParcely();
            if (objekty != null) {
                model.addAll(objekty);
                this.list1.setModel(model);
            }
        }

    }

    public JPanel getPanel1() {
        return panel1;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JTextField getCisloField() {
        return cisloField;
    }

    public JTextField getDescField() {
        return descField;
    }

    public JTextField getX1Field() {
        return x1Field;
    }

    public JTextField getY1Field() {
        return y1Field;
    }

    public JTextField getX2Field() {
        return x2Field;
    }

    public JTextField getY2Field() {
        return y2Field;
    }

    public JButton getSubmitEditButton() {
        return submitEditButton;
    }

    public JRadioButton getJeNehnutelnostRadio() {
        return jeNehnutelnostRadio;
    }
}
