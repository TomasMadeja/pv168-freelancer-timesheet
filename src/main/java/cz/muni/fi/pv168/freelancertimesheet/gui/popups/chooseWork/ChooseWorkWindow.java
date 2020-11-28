package cz.muni.fi.pv168.freelancertimesheet.gui.popups.chooseWork;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.ChooseWorkTableModel;

import javax.swing.*;
import java.awt.*;

public class ChooseWorkWindow extends JFrame implements GenericElement<ChooseWorkWindow> {

    GridBagConstraints gbc;

    public ChooseWorkWindow() {
        super("Choose Work");
    }


    private JTable createTable() {
        JTable table = new JTable(new ChooseWorkTableModel());
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//        RandomDataGenerator.generateWorkData((ChooseWorkTableModel)table.getModel());
        return table;
    }

    private JButton confirmSelectionButton() {
        var panel = new JPanel();
        var button = new JButton();

        button.setText("Confirm Selection");

//        panel.add(button);
        return button;
    }

    private GridBagConstraints getGbc(int x, int y, int height, int width, double weightY) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridheight = height;
        gbc.gridwidth = width;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = weightY;

        return gbc;
    }

    @Override
    public ChooseWorkWindow setupLayout() {
        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
        this.setSize(new Dimension(1000, 500)); // TODO
        return this;
    }

    @Override
    public ChooseWorkWindow setupVisuals() {
        return this;
    }


    @Override
    public ChooseWorkWindow setupNested() {
        var table = new JScrollPane(createTable());
        var confirmButton = confirmSelectionButton();


        gbc = getGbc(0, 0, 3, 5, 0.5);
        gbc.fill = GridBagConstraints.BOTH;
        this.add(table, gbc);
        gbc = getGbc(3, 4, 1, 1, 0.3);
        gbc.fill = GridBagConstraints.NONE;
        this.add(confirmButton, gbc);

        return this;
    }

    public static ChooseWorkWindow setup() {
        ChooseWorkWindow window = new ChooseWorkWindow()
                .setupLayout()
                .setupVisuals()
                .setupNested();
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        return window;
    }
}
