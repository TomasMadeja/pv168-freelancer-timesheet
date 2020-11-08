package cz.muni.fi.pv168.freelancertimesheet.gui;

import javax.swing.*;
import java.awt.*;

public class TabbedPane extends JTabbedPane {

    public TabbedPane(int tabsPosition, int tabsBehaviour) {
        super(tabsPosition, tabsBehaviour);
    }

    private TabbedPane setupVisuals() {
        return this;
    }

    private TabbedPane setupNested() {
        var taskPanel = new TaskPanel();
        var invoicePanel = new InvoicePanel();

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


    public static TabbedPane setupPanel() {
        var tabs = new TabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
        tabs.setupNested();
        tabs.setVisible(true);
        return tabs;
    }

}



