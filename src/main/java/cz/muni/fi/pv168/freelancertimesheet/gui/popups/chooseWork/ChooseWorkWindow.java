package cz.muni.fi.pv168.freelancertimesheet.gui.popups.chooseWork;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.I18N;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.WorkContainer;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.ChooseWorkTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class ChooseWorkWindow extends JFrame implements GenericElement<ChooseWorkWindow> {

    private static final I18N i18n = new I18N(ChooseWorkWindow.class);

    GridBagConstraints gbc;
    Function<List<Work>, Object> updateCallback;

    private final HashMap<Work, Boolean> selectedRows;

    public ChooseWorkWindow(Function<List<Work>, Object> updateCallback) {
        super(i18n.getString("title"));
        this.updateCallback = updateCallback;
        selectedRows = new HashMap<>();
    }


    private JTable createTable() {
        var model = new ChooseWorkTableModel(new WorkContainer(), selectedRows);
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        return table;
    }

    private JButton confirmSelectionButton(Function<List<Work>, Object> updateWorkSelection) {
        var button = new JButton();
        button.setText(i18n.getString("confirm"));

        // TODO modify so the invoice tab can get this info
        button.addActionListener(e -> {
            List<Work> selectedWorks = new ArrayList<>();
            selectedRows.forEach((workRow, isSelected) -> {
                if (isSelected) {
                    System.out.println(workRow);
                    selectedWorks.add(workRow);
                }
            });
            updateWorkSelection.apply(selectedWorks);
            this.dispose();
        });

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
        var confirmButton = confirmSelectionButton(updateCallback);

        gbc = getGbc(0, 0, 3, 5, 0.5);
        gbc.fill = GridBagConstraints.BOTH;
        this.add(table, gbc);
        gbc = getGbc(3, 4, 1, 1, 0.3);
        gbc.fill = GridBagConstraints.NONE;
        this.add(confirmButton, gbc);

        return this;
    }

    public static ChooseWorkWindow setup(Function<List<Work>, Object> updateCallback) {
        ChooseWorkWindow window = new ChooseWorkWindow(updateCallback)
                .setupLayout()
                .setupVisuals()
                .setupNested();
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        return window;
    }
}
