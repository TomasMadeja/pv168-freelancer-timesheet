package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.invoice;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;

import javax.swing.*;
import java.awt.*;

public class InvoiceTable extends JScrollPane implements GenericElement {
    public InvoiceTable(Component view, int vsbPolicy, int hsbPolicy) {
        super(view, vsbPolicy, hsbPolicy);
    }

    public InvoiceTable(Component view) {
        super(view);
    }

    public InvoiceTable(int vsbPolicy, int hsbPolicy) {
        super(vsbPolicy, hsbPolicy);
    }

    public InvoiceTable() {
        super();
    }

    @Override
    public InvoiceTable setupLayout() {
        return this;
    }

    @Override
    public InvoiceTable setupVisuals() {
        return this;
    }

    @Override
    public InvoiceTable setupNested() {
        return this;
    }

    public static InvoiceTable setup() {
        InvoiceTable invoiceTable = new InvoiceTable();
        return invoiceTable;
    }
}
