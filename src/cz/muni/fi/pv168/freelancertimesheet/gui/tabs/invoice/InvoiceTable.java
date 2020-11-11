package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.invoice;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.InvoiceTableModel;

import javax.swing.*;
import java.awt.*;

public class InvoiceTable extends JScrollPane implements GenericElement {
    private JTable table;

    public InvoiceTable() {
        super();
    }

    public InvoiceTable buildTable() {
        table = new JTable(new InvoiceTableModel());
        table.setAutoCreateRowSorter(true);
        table.setRowHeight(20);
        setViewportView(table);
        return this;
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
        invoiceTable
                .buildTable();
        return invoiceTable;
    }
}
