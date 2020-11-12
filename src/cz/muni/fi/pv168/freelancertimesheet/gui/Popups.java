package cz.muni.fi.pv168.freelancertimesheet.gui;

import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.TaskTab;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.task.TaskForm;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.task.TaskTable;

import javax.swing.*;

public class Popups extends JFrame {
    static int defaultWidth = 600;
    static int defaultHeight = 300;
    static PopupFactory popupFactory = new PopupFactory();

    public Popups() {
        super();
    }

    public static JFrame GetTaskForm() {
        return GetPopup(TaskForm.setup(), "Task formular");
    }

    public static JFrame GetTaskTable() {
        return GetPopup(TaskTable.setup(), "Task table");
    }

    public static JFrame GetTaskTab() {
        return GetPopup(TaskTab.setup(), "Tasks");
    }

    public static JFrame GetPopup(JComponent content, String title) {
        JFrame dialog = new JFrame(title);
        dialog.setSize(defaultWidth, defaultHeight);
        dialog.add(content);
        return dialog;
    }
}
