package kozelek.GUI.views;

import javax.swing.*;
import java.awt.*;

public class AddWindow extends JFrame {

    private JButton backButton;
    private JList list1;
    private JTextField cisloField;
    private JTextField descField;
    private JTextField x1Field;
    private JTextField y1Field;
    private JTextField x2Field;
    private JTextField y2Field;
    private JRadioButton jeNehnutelnostRadio;
    private JButton submitAddButton;
    private JPanel panel1;

    public AddWindow() {
        setTitle("Add Window");
        setSize(1200, 600);
        setMinimumSize(new Dimension(600, 400));
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
    }

    public JButton getSubmitAddButton() {
        return submitAddButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JList getList1() {
        return list1;
    }

    public void setList1(JList list1) {
        this.list1 = list1;
    }


    public JTextField getX1Field() {
        return x1Field;
    }

    public void setX1Field(JTextField x1Field) {
        this.x1Field = x1Field;
    }

    public JTextField getY1Field() {
        return y1Field;
    }

    public void setY1Field(JTextField y1Field) {
        this.y1Field = y1Field;
    }

    public JTextField getX2Field() {
        return x2Field;
    }


    public JTextField getY2Field() {
        return y2Field;
    }


    public JRadioButton getJeNehnutelnostRadio() {
        return jeNehnutelnostRadio;
    }

    public JTextField getCisloField() {
        return cisloField;
    }


    public JTextField getDescField() {
        return descField;
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
