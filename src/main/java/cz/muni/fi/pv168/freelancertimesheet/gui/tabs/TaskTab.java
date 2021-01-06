package cz.muni.fi.pv168.freelancertimesheet.gui.tabs;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.I18N;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.workform.WorkFormWindow;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.form.WorkTypeFormWindow;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.task.TaskTable;

import javax.swing.*;
import java.awt.*;

/**
 * Whole pane is divided into two sections
 * Top one has text fields and buttons
 * Bottom one has big table
 */
public class TaskTab extends JPanel implements GenericElement<TaskTab> {

    private final I18N i18n = new I18N(getClass());

    private GridBagConstraints gbc;

    public TaskTab() {
        super();
    }

    @Override
    public TaskTab setupLayout() {
        GridBagLayout layout = new GridBagLayout();
        gbc = new GridBagConstraints();
        this.setLayout(layout);
        return this;
    }

    @Override
    public TaskTab setupVisuals() {
        return this;
    }

    @Override
    public TaskTab setupNested() {
        var panel = new JPanel();
        var newTaskButton = new JButton(i18n.getString("newTask"));
        newTaskButton.addActionListener(e -> WorkFormWindow.setup());
        var newTaskTypeButton = new JButton(i18n.getString("newTaskType"));
       newTaskTypeButton.addActionListener(e -> WorkTypeFormWindow.setup(null));

        panel.add(newTaskButton);
        panel.add(newTaskTypeButton);

        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.1;
        this.add(panel, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(TaskTable.setup(), gbc);

        return this;
    }

    public static TaskTab setup() {
        return new TaskTab()
                .setupLayout()
                .setupVisuals()
                .setupNested();
    }
}