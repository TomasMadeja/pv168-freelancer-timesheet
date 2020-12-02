package cz.muni.fi.pv168.freelancertimesheet.gui.tabs;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.InvoiceWindow;
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
        GridLayout layout = new GridLayout(1, 1);
//        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);
        return this;
    }

    @Override
    public InvoiceTab setupVisuals() {
        return this;
    }

    @Override
    public InvoiceTab setupNested() {
//        InvoiceForm form = InvoiceForm.setup();
//        InvoiceWindow.setup();
//        add(form); //, BorderLayout.NORTH);
        add(InvoiceTable.setup()); //, BorderLayout.CENTER);
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
