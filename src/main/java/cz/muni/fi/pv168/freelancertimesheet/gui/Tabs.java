package cz.muni.fi.pv168.freelancertimesheet.gui;

import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.InvoiceTab;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.TaskTab;

import javax.swing.*;
import java.awt.*;

public class Tabs extends JTabbedPane implements GenericElement {

    public Tabs(int tabsPosition, int tabsBehaviour) {
        super(tabsPosition, tabsBehaviour);
    }

    @Override
    public GenericElement setupLayout() {
        return this;
    }

    @Override
    public Tabs setupVisuals() {
        return this;
    }

    @Override
    public Tabs setupNested() {
        var taskPanel = TaskTab.setup();
        var invoicePanel = InvoiceTab.setup();

        // need to create individual labels for each tabs
        // it allows to set custom size
        // TODO set width based on parent pane size
        var taskLabel = new JLabel("Task", SwingConstants.CENTER);
        taskLabel.setPreferredSize(new Dimension(250, 30));
        var invoiceLabel = new JLabel("Invoice", SwingConstants.CENTER);
        invoiceLabel.setPreferredSize(new Dimension(250, 30));

        this.addTab(null, taskPanel);
        this.addTab(null, invoicePanel);
        // Set the custom tab component
        this.setTabComponentAt(0, taskLabel);
        this.setTabComponentAt(1, invoiceLabel);

        return this;
    }


    public static Tabs setup() {
        var tabs = new Tabs(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
        tabs.setupNested();
        tabs.setVisible(true);
        return tabs;
    }

}



