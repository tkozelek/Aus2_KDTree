package kozelek.GUI.views;

import kozelek.data.GPS;
import kozelek.data.Nehnutelnost;
import kozelek.data.Parcela;
import kozelek.data.objekt.Entity;
import kozelek.data.objekt.GPSObjekt;
import kozelek.data.objekt.interfaces.IObject;

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
    private JList list1;
    private T data;

    public EditView(T data) {
        this.data = data;
        this.setValues();
        setTitle("Edit Window");
        setMinimumSize(new Dimension(600, 400));
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
    }

    public T getData() {
        return data;
    }

    public T getOther() {
        if (data instanceof Entity ent) {
            return (T) ent.getOther();
        }
        return null;
    }

    public void setData(T data) {
        this.data = data;
        this.setValues();
    }

    private void setValues() {
        if (data != null && data instanceof Entity ent) {
            this.x1Field.setText(String.valueOf(ent.getGPS().getX()));
            this.y1Field.setText(String.valueOf(ent.getGPS().getY()));

            Entity other = (Entity) ent.getOther();
            if (other != null) {
                this.x2Field.setText(String.valueOf(other.getGPS().getX()));
                this.y2Field.setText(String.valueOf(other.getGPS().getY()));
            }

            this.descField.setText(ent.getDescription());
            this.cisloField.setText(String.valueOf(ent.getId()));
        }
        this.updateList();
    }

    private void updateList() {
        DefaultListModel<T> model = new DefaultListModel<>();

        ArrayList<T> objekty = null;
        if (data instanceof Nehnutelnost neh) {
            objekty = (ArrayList<T>) neh.getParcely();
        } else if (data instanceof Parcela par) {
            objekty = (ArrayList<T>) par.getNehnutelnosti();
        } else if (data instanceof GPSObjekt obj) {
            objekty = obj.getNehnutelnost() != null ?
                    (ArrayList<T>) obj.getNehnutelnost().getParcely() :
                    (ArrayList<T>) obj.getParcela().getNehnutelnosti();
        }
        if (objekty != null) {
            model.addAll(objekty);
            this.list1.setModel(model);
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
}
