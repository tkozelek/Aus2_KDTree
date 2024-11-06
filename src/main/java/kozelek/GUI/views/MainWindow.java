package kozelek.GUI.views;

import javax.swing.*;

public class MainWindow extends JFrame {
    private JButton findAllButton;
    private JButton findPropertiesButton;
    private JButton findParcelasButton;
    private JList list1;
    private JButton editButton;
    private JButton exportButton;
    private JButton generateDataButton;
    private JButton addButton;
    private JTextField x1Field;
    private JTextField y1Field;
    private JTextField x2Field;
    private JTextField y2Field;
    private JButton deleteButton;
    private JPanel panel1;

    public MainWindow() {
        add(panel1);
        setTitle("Hlavne okno");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JButton getFindAllButton() {
        return findAllButton;
    }

    public JButton getFindPropertiesButton() {
        return findPropertiesButton;
    }

    public JButton getFindParcelasButton() {
        return findParcelasButton;
    }

    public JList getList1() {
        return list1;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getExportButton() {
        return exportButton;
    }

    public JButton getGenerateDataButton() {
        return generateDataButton;
    }

    public JButton getAddButton() {
        return addButton;
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

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
