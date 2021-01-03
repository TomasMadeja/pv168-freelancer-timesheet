package cz.muni.fi.pv168.freelancertimesheet.gui.containers;

import cz.muni.fi.pv168.freelancertimesheet.backend.PersistanceManager;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Invoice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InvoiceContainer implements GenericContainer{
    private static InvoiceContainer invoiceContainer;
    private List<? extends Invoice> rows;

    public InvoiceContainer() {
        rows = PersistanceManager.getAllInvoice();
    }

    public static InvoiceContainer getContainer() {
        if (invoiceContainer == null) {
            invoiceContainer = new InvoiceContainer();
        }
        return invoiceContainer;
    }

    @Override
    public InvoiceContainer refresh() {
        rows = PersistanceManager.getAllInvoice();
        return this;
    }

    @Override
    public Invoice get(int i) {
        return rows.get(i);
    }

    @Override
    public int size() {
        return rows.size();
    }

    @Override
    public void remove(int i) {
        PersistanceManager.removeEntity(rows.get(i));
        rows.remove(i);
    }

    @Override
    public void removeList(int[] indices) {
        List<Invoice> mappedEntities = new ArrayList<>();
        int[] reverseIndices = Arrays.stream(indices)
                .boxed()
                .sorted(Collections.reverseOrder())
                .mapToInt(Integer::intValue)
                .toArray();
        Arrays.stream(reverseIndices)
                .forEach((i) -> mappedEntities.add(rows.get(i)));
        PersistanceManager.removeCollection(mappedEntities);
        Arrays.stream(reverseIndices)
                .forEach((i) -> rows.remove(i));
    }
}
