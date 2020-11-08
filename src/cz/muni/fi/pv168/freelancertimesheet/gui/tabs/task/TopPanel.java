package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.task;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel implements GenericElement {
    public TopPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public TopPanel(LayoutManager layout) {
        super(layout);
    }

    public TopPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public TopPanel() {
        super();
    }

    @Override
    public TopPanel setupLayout() {
        GridLayout topLayout = new GridLayout(4, 2);
        topLayout.setHgap(10);
        topLayout.setVgap(20);

        setLayout(topLayout);
        return this;
    }

    @Override
    public TopPanel setupVisuals() {
        return this;
    }

    @Override
    public TopPanel setupNested() {
        add(new JLabel("Enter date:"));
        add(new JTextField());
        add(new JLabel("Enter task name:"));
        add(new JTextField());
        add(new JLabel("Enter description:"));
        add(new JTextField());
        add(new JButton("Add Task"));
        return this;
    }

    public static TopPanel setup() {
        TopPanel panel = new TopPanel();
        panel
                .setupLayout()
                .setupVisuals()
                .setupNested();
        return panel;
    }
}
