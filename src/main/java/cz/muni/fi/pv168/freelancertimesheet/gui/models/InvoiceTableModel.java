package cz.muni.fi.pv168.freelancertimesheet.gui.models;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Invoice;

import java.time.ZonedDateTime;

public class InvoiceTableModel extends TableModel<Invoice> {

    public InvoiceTableModel() {
        super();
        createColumns();
    }

    private void createColumns() {
        super.addColumn(new Column<>(
                "Invoice Date",
                "test1",
                ZonedDateTime.class,
                Invoice.class,
                (Object object) -> ((Invoice) object).getIssueDate(),
                null
        ));
        super.addColumn(new Column<>(
                "Due Date",
                "test2",
                ZonedDateTime.class,
                Invoice.class,
                (Object object) -> ((Invoice) object).getDueDate(),
                null
        ));
        super.addColumn(new Column<>(
                "ICO",
                "test3",
                String.class,
                Invoice.class,
                (Object object) -> "25596641", //TODO: add ICO to proper person
                null
        ));
        super.addColumn(new Column<>(
                "DIC",
                "test3",
                String.class,
                Invoice.class,
                (Object object) -> "CZ1234567890", //TODO: add DIC to proper person
                null
        ));
        super.addColumn(new Column<>(
                "Customer Name",
                "test3",
                String.class,
                Invoice.class,
                (Object object) -> ((Invoice) object).getClient().getName(),
                null
        ));

    }
}
