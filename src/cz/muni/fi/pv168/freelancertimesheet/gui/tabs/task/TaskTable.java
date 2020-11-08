package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.task;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Vector;

public class TaskTable extends JScrollPane implements GenericElement {

    private JTable table;

    public TaskTable(Component view, int vsbPolicy, int hsbPolicy) {
        super(view, vsbPolicy, hsbPolicy);
    }

    public TaskTable(Component view) {
        super(view);
    }

    public TaskTable(int vsbPolicy, int hsbPolicy) {
        super(vsbPolicy, hsbPolicy);
    }

    public TaskTable() {
        super();
    }

    private TaskTable buildTable() {
        String[] columnNames = {"Date", "Task name", "Description"};
        String[][] data = {
                {"1.1.1900", "Homework", "IB002"},
                {"2.1.1900", "Homework", "IB111"}
        };

        table = new JTable(data, columnNames);
        return this;
    }

    @Override
    public TaskTable setupLayout() {
        buildTable();
        setViewportView(table);
        return this;
    }

    @Override
    public TaskTable setupVisuals() {
        return this;
    }

    @Override
    public TaskTable setupNested() {
        return this;
    }

    public static TaskTable setup() {
        TaskTable table = new TaskTable();
        table
                .setupLayout()
                .setupVisuals()
                .setupNested();
        return table;
    }
}
