package cz.muni.fi.pv168.freelancertimesheet.gui;

import cz.muni.fi.pv168.freelancertimesheet.gui.popups.taskform.TaskForm;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.invoice.InvoiceForm;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.task.TaskTable;

import javax.swing.*;

public class TasksPopup extends JFrame implements GenericElement<TasksPopup> {
    static int defaultWidth = 800;
    static int defaultHeight = 600;

    InvoiceForm invoiceForm;
    JPanel innerPanel;

    public TasksPopup() {
        super();
    }

    public TasksPopup(String title) {
        super(title);
    }

    public static TasksPopup setup(InvoiceForm invoiceForm) {
        TasksPopup frame = new TasksPopup("Tasks");
        frame
                .setupLayout()
                .setupVisuals()
                .setupNested();
        frame.pack();
        frame.setSize(defaultWidth, defaultHeight);
        frame.invoiceForm = invoiceForm;
        return frame;
    }

    @Override
    public TasksPopup setupLayout() {
        innerPanel = new JPanel();
        BoxLayout layout = new BoxLayout(innerPanel, BoxLayout.Y_AXIS);
        innerPanel.setLayout(layout);
        this.add(innerPanel);
        return this;
    }

    @Override
    public TasksPopup setupVisuals() {
        return this;
    }

    @Override
    public TasksPopup setupNested() {
        TaskForm taskForm = TaskForm.setup();
        innerPanel.add(taskForm);

        TaskTable taskTable = TaskTable.setup();
        innerPanel.add(taskTable);

        JButton btn = new JButton("Add selected");
        innerPanel.add(btn);
        btn.addActionListener(e -> {
            invoiceForm.updateSelectedWork(taskTable.GetSelectedTasksCount());
            this.setVisible(false);
        });
        return this;
    }
}
