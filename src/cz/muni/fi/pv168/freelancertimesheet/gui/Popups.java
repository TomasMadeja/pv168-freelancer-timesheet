package cz.muni.fi.pv168.freelancertimesheet.gui;

import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.TaskTab;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.task.TaskForm;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.task.TaskTable;

import javax.swing.*;
import java.awt.*;

public class Popups extends JFrame {
    static int defaultWidth = 600;
    static int defaultHeight = 400;

    public Popups() {
        super();
    }

    public static JFrame GetTaskForm() {
        return GetPopup(TaskForm.setup(), "Task form");
    }

    public static JFrame GetTaskTable() {
        return GetPopup(TaskTable.setup(), "Task table");
    }

    public static JFrame GetTaskTab() {
        return GetPopup(TaskTab.setup(), "Tasks");
    }

    public static JFrame GetTaskTabInvoice() {
        JFrame frame = GetPopup(TaskTab.setup(), "Tasks");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 0.5;
        gbc.gridheight = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        frame.add(new JButton("Add selected"), gbc);
        return frame;
    }

    public static JFrame GetPopup(JComponent content, String title) {
        JFrame frame = new JFrame(title);

        frame.setSize(defaultWidth, defaultHeight);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(content, gbc);
        return frame;
    }
}
