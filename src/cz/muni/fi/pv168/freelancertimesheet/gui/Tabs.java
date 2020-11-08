package cz.muni.fi.pv168.freelancertimesheet.gui;

import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.InvoiceTab;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.TaskTab;

import javax.swing.*;
import java.awt.*;

public class TabWindow extends JTabbedPane implements GenericElement {

    public TabWindow(int tabsPosition, int tabsBehaviour) {
        super(tabsPosition, tabsBehaviour);
    }

    @Override
    public GenericElement setupLayout() {
        return this;
    }

    public TabWindow setupVisuals() {
        return this;
    }

    public TabWindow setupNested() {
        var taskPanel = new TaskTab();
        var invoicePanel = new InvoiceTab();

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


    public static TabWindow setupPanel() {
        var tabs = new TabWindow(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
        tabs.setupNested();
        tabs.setVisible(true);
        return tabs;
    }

}



