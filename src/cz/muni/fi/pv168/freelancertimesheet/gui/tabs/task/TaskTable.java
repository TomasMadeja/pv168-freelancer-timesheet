package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.task;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;

import javax.swing.*;
import java.awt.*;

public class TaskTable extends JPanel implements GenericElement<TaskTable> {

    public TaskTable() {
        super();
    }

    private JPanel createTableButtonPanel() {
        JPanel panel = new JPanel();
        GridLayout layout = new GridLayout(1, 7);
        panel.setLayout(layout);

        panel.add(new JButton("New"));
        panel.add(new JButton("Edit"));
        panel.add(new JButton("Delete"));

        var searchLabel = new JLabel("Search:", SwingConstants.CENTER);
        panel.add(searchLabel);
        panel.add(new JTextField());

        panel.add(new JLabel());
        panel.add(new JLabel());

        return panel;
    }

    private JTable createTable() {
        String[] columnNames = {"Date", "Task name", "Work Type", "Start Time", "End Time", "Description"};
        String[][] data = {
                {"1.1.1900", "Sample task", "Work type", "14:50", "16:00", "Desc"},
                {"1.1.1900", "Sample task2", "Work type", "02:00", "04:00", "Desc"},
                {"2.1.1900", "Sample task3", "Work type", "15:00", "15:30", "Desc"}
        };


        var table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
//        table.setModel(model);
        return table;
    }

    @Override
    public TaskTable setupLayout() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        return this;
    }

    @Override
    public TaskTable setupVisuals() {
        return this;
    }

    @Override
    public TaskTable setupNested() {
        this.add(createTableButtonPanel());

        // table needs to be wrapped in JScrollPane in order to show column names
        this.add(new JScrollPane(createTable()));

        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return this;
    }

    public static TaskTable setup() {
        return new TaskTable()
                .setupLayout()
                .setupVisuals()
                .setupNested();
    }
}
