package cz.muni.fi.pv168.freelancertimesheet.gui.tabs;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;

import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.task.WorkForm;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.task.TaskTable;

import javax.swing.*;
import java.awt.*;

/**
 * Whole pane is divided into two sections
 * Top one has text fields and buttons
 * Bottom one has big table
 */
public class TaskTab extends JPanel implements GenericElement<TaskTab> {

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
        WorkForm workForm = WorkForm.setup();
        this.add(workForm);
        this.add(TaskTable.setup(workForm));

        return this;
    }

    public static TaskTab setup() {
        return new TaskTab()
                .setupLayout()
                .setupVisuals()
                .setupNested();
    }
}