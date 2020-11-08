package cz.muni.fi.pv168.freelancertimesheet.gui.tabs;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.invoice.InvoiceForm;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.invoice.InvoiceTable;

import javax.swing.*;
import java.awt.*;

public class InvoiceTab extends JPanel implements GenericElement {

    public InvoiceTab() {
        super();
    }

    @Override
    public InvoiceTab setupLayout() {
        GridLayout layout = new GridLayout(2, 1);
        this.setLayout(layout);
        return this;
    }

    @Override
    public InvoiceTab setupVisuals() {
        return this;
    }

    @Override
    public InvoiceTab setupNested() {
        add(InvoiceForm.setup());
        add(InvoiceTable.setup());
        return this;
    }

    public static InvoiceTab setup() {
        InvoiceTab invoiceTab = new InvoiceTab();
        invoiceTab
                .setupLayout()
                .setupVisuals()
                .setupNested();
        return invoiceTab;
    }
}
