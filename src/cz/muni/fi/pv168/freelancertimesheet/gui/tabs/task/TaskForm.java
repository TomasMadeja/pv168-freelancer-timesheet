package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.task;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;

import javax.swing.*;
import java.awt.*;

public class TaskForm extends JPanel implements GenericElement {
    public TaskForm(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public TaskForm(LayoutManager layout) {
        super(layout);
    }

    public TaskForm(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public TaskForm() {
        super();
    }

    @Override
    public TaskForm setupLayout() {
        GridLayout topLayout = new GridLayout(4, 2);
        topLayout.setHgap(10);
        topLayout.setVgap(20);

        setLayout(topLayout);
        return this;
    }

    @Override
    public TaskForm setupVisuals() {
        return this;
    }

    @Override
    public TaskForm setupNested() {
        add(new JLabel("Enter date:"));
        add(new JTextField());
        add(new JLabel("Enter task name:"));
        add(new JTextField());
        add(new JLabel("Enter description:"));
        add(new JTextField());
        add(new JButton("Add Task"));
        return this;
    }

    public static TaskForm setup() {
        TaskForm panel = new TaskForm();
        panel
                .setupLayout()
                .setupVisuals()
                .setupNested();
        return panel;
    }
}
