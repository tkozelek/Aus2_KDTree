package kozelek.GUI.views;

import javax.swing.*;
import java.awt.*;

public class GenerateDataView extends JFrame {
    private JButton goBackButton;
    private JTextField x1Field;
    private JTextField y1Field;
    private JTextField x2Field;
    private JTextField y2Field;
    private JTextField countNehnutelnost;
    private JButton generateButton;
    private JPanel panel1;
    private JTextField countParcela;
    private JTextField startAtNehnutelnost;
    private JTextField startAtParcela;
    private JTextField duplicateNehnutelnostField;
    private JTextField duplicateParcelaField;

    public GenerateDataView() {
        setTitle("Generate data Window");
        setSize(1200, 600);
        setMinimumSize(new Dimension(600, 400));
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
    }

    public JTextField getDuplicateNehnutelnostField() {
        return duplicateNehnutelnostField;
    }

    public JTextField getDuplicateParcelaField() {
        return duplicateParcelaField;
    }

    public JTextField getStartAtParcela() {
        return startAtParcela;
    }

    public JTextField getStartAtNehnutelnost() {
        return startAtNehnutelnost;
    }

    public JTextField getCountParcela() {
        return countParcela;
    }

    public JButton getGenerateButton() {
        return generateButton;
    }

    public JTextField getCountNehnutelnost() {
        return countNehnutelnost;
    }

    public JTextField getY2Field() {
        return y2Field;
    }

    public JTextField getX2Field() {
        return x2Field;
    }

    public JTextField getX1Field() {
        return x1Field;
    }

    public JButton getGoBackButton() {
        return goBackButton;
    }

    public JTextField getY1Field() {
        return y1Field;
    }
}
