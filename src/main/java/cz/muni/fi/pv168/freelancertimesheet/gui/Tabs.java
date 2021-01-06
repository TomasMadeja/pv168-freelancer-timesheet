package cz.muni.fi.pv168.freelancertimesheet.gui;

import cz.muni.fi.pv168.freelancertimesheet.backend.PDFStorage;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.InvoiceTab;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.TaskTab;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.WorkTypeTab;

import javax.swing.*;
import java.awt.*;

public class Tabs extends JTabbedPane implements GenericElement {
    private PDFStorage pdfStorage;

    public Tabs(int tabsPosition, int tabsBehaviour, PDFStorage pdfStorage) {
        super(tabsPosition, tabsBehaviour);
        this.pdfStorage = pdfStorage;
    }
    private final I18N i18n = new I18N(getClass());

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
        var invoicePanel = InvoiceTab.setup(pdfStorage);
        var workTypeTab  = WorkTypeTab.setup();

        // need to create individual labels for each tabs
        // it allows to set custom size
        // TODO set width based on parent pane size
        var taskLabel = new JLabel(i18n.getString("work"), SwingConstants.CENTER);
        taskLabel.setPreferredSize(new Dimension(250, 30));
        var invoiceLabel = new JLabel(i18n.getString("invoice"), SwingConstants.CENTER);
        invoiceLabel.setPreferredSize(new Dimension(250, 30));
        var workTypeLabel = new JLabel(i18n.getString("workType"), SwingConstants.CENTER);
        workTypeLabel.setPreferredSize(new Dimension(250, 30));

        this.addTab(null, taskPanel);
        this.addTab(null, invoicePanel);
        this.addTab(null, workTypeTab);
        // Set the custom tab component
        this.setTabComponentAt(0, taskLabel);
        this.setTabComponentAt(1, invoiceLabel);
        this.setTabComponentAt(2, workTypeLabel);

        return this;
    }


    public static Tabs setup(PDFStorage pdfStorage) {
        var tabs = new Tabs(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT, pdfStorage);
        tabs.setupNested();
        tabs.setVisible(true);
        return tabs;
    }

}



