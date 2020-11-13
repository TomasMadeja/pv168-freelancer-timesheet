package cz.muni.fi.pv168.freelancertimesheet.gui.tabs;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.Popups;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.task.TaskTable;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.task.TaskForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Whole pane is divided into two sections
 * Top one has text fields and buttons
 * Bottom one has big table
 */
public class TaskTab extends JPanel implements GenericElement {

    public TaskTab() {
        super();
    }

    @Override
    public TaskTab setupLayout() {
        GridLayout layout = new GridLayout(2, 1);
        this.setLayout(layout);
        return this;
    }

    @Override
    public TaskTab setupVisuals() {
        return this;
    }

    @Override
    public TaskTab setupNested() {
        this.add(TaskForm.setup());
        this.add(TaskTable.setup());
        return this;
    }

    public static TaskTab setup() {
        TaskTab taskTab = new TaskTab();
        taskTab
                .setupLayout()
                .setupVisuals()
                .setupNested();
        return taskTab;
    }
}