package cz.muni.fi.pv168.freelancertimesheet.gui.containers;

import cz.muni.fi.pv168.freelancertimesheet.backend.PersistanceManager;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Invoice;

import java.util.List;

public class InvoiceContainer implements GenericContainer{
    private static InvoiceContainer invoiceContainer;
    private List<? extends Invoice> invoices;

    public InvoiceContainer() {
        invoices = PersistanceManager.getAllInvoice();
    }

    public static InvoiceContainer getContainer() {
        if (invoiceContainer == null) {
            invoiceContainer = new InvoiceContainer();
        }
        return invoiceContainer;
    }

    @Override
    public InvoiceContainer refresh() {
        invoices = PersistanceManager.getAllInvoice();
        return this;
    }

    @Override
    public Invoice get(int i) {
        return invoices.get(i);
    }

    @Override
    public int size() {
        return invoices.size();
    }
}
