package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.task;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.exampledata.RandomDataGenerator;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.WorkTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class TaskTable extends JPanel implements GenericElement<TaskTable> {

    private final TaskForm taskForm;
    private JTable table;

    public int GetSelectedTasksCount() {
        return table.getSelectedRows().length;
    }

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
        String[] rowData = new String[table.getColumnCount()];
        Arrays.fill(rowData, "");
        taskForm.fillForm(rowData);
    }

    private void fillTaskForm() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        Object[] rowData = new Object[table.getColumnCount()];
        for (int i = 1; i < table.getColumnCount(); i++) {
            rowData[i] = table.getValueAt(selectedRow, i);
        }
        rowData[0] = false;
        taskForm.fillForm(rowData);
    }

    private JTable createTable() {
        table = new JTable(new WorkTableModel());
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        RandomDataGenerator.generateWorkData((WorkTableModel)table.getModel());
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
