package cz.muni.fi.pv168.freelancertimesheet.gui;

import javax.swing.*;
import java.awt.*;

public class TaskPanel extends JPanel {

    // TODO divide into specific functions
    public TaskPanel() {
        // Whole pane is divided into two sections
        // Top one has text fields and buttons
        // Bottom one has big table

        JPanel topPanel = new JPanel();


        var topLayout = new GridLayout(4, 2);
        topLayout.setHgap(10);
        topLayout.setVgap(20);

        topPanel.setLayout(topLayout);

        topPanel.add(new JLabel("Enter date:"));
        topPanel.add(new JTextField());
        topPanel.add(new JLabel("Enter task name:"));
        topPanel.add(new JTextField());
        topPanel.add(new JLabel("Enter description:"));
        topPanel.add(new JTextField());
        topPanel.add(new JButton("Add Task"));

        // column names are not visible
        String[] columnNames = {"Date", "Task name", "Description"};
        String[][] data = {
                {"1.1.1900", "Homework", "IB002"},
                {"2.1.1900", "Homework", "IB111"}
        };

        var table = new JTable(data, columnNames);

        var layout = new GridLayout(2, 1);
        this.setLayout(layout);

        this.add(topPanel);
        this.add(table);

    }

}