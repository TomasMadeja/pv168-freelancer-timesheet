package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.task;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class TaskTable extends JPanel implements GenericElement<TaskTable> {

    private final TaskForm taskForm;
    private JTable table;
    private int tableColumnCount;

    public TaskTable() {
        super();
        taskForm = new TaskForm();
    }

    public TaskTable(TaskForm taskForm) {
        super();
        this.taskForm = taskForm;
    }

    private JPanel createTableButtonPanel() {
        JPanel panel = new JPanel();
        GridLayout layout = new GridLayout(1, 7);
        panel.setLayout(layout);

        var newButton = new JButton("New");
        var editButton = new JButton("Edit");
        var deleteButton = new JButton("Delete");

        newButton.addActionListener(e -> resetTaskForm());
        editButton.addActionListener(e -> fillTaskForm());


        panel.add(newButton);
        panel.add(editButton);
        panel.add(deleteButton);

        var searchLabel = new JLabel("Search:", SwingConstants.CENTER);
        panel.add(searchLabel);
        panel.add(new JTextField());

        panel.add(new JLabel());
        panel.add(new JLabel());

        return panel;
    }

    private void resetTaskForm() {
        String[] rowData = new String[tableColumnCount];
        Arrays.fill(rowData, "");
        taskForm.fillForm(rowData);
    }

    private void fillTaskForm() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        String[] rowData = new String[tableColumnCount];
        for (int i = 0; i < tableColumnCount; i++) {
            rowData[i] = (String) table.getValueAt(selectedRow, i);
        }
        taskForm.fillForm(rowData);
    }

    private JTable createTable() {
        String[] columnNames = {"Date", "Task name", "Work Type", "Start Time", "End Time", "Description"};
        String[][] data = {
                {"November 4, 2020", "Sample task", "Work type", "14:50", "16:00", "Desc"},
                {"November 4, 2020", "Sample task2", "Work type", "02:00", "04:00", "Desc"},
                {"November 2, 2020", "Sample task3", "Work type", "15:00", "15:30", "Desc"}
        };
        tableColumnCount = 6;


        table = new JTable(data, columnNames) {
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

    public static TaskTable setup(TaskForm taskForm) {
        return new TaskTable(taskForm)
                .setupLayout()
                .setupVisuals()
                .setupNested();
    }
}
