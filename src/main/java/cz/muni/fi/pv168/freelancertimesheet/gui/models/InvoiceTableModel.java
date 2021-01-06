package cz.muni.fi.pv168.freelancertimesheet.gui.models;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Invoice;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.InvoiceContainer;

import java.time.ZonedDateTime;

public class InvoiceTableModel extends TableModel<Invoice> {

    public InvoiceTableModel(InvoiceContainer container) {
        super(container);
        createColumns();
    }

    private void createColumns() {
        super.addColumn(new Column<ZonedDateTime, Invoice>(
                "Invoice Date",
                "test1",
                false,
                ZonedDateTime.class,
                Invoice.class,
                (Object object) -> ((Invoice) object).getIssueDate(),
                null
        ));
        super.addColumn(new Column<ZonedDateTime, Invoice>(
                "Due Date",
                "test2",
                false,
                ZonedDateTime.class,
                Invoice.class,
                (Object object) -> ((Invoice) object).getDueDate(),
                null
        ));
        super.addColumn(new Column<String, Invoice>(
                "ICO",
                "test3",
                false,
                String.class,
                Invoice.class,
                (Object object) -> ((Invoice) object).getClient().getICO(), //TODO: add ICO to proper person
                null
        ));
        super.addColumn(new Column<String, Invoice>(
                "DIC",
                "test3",
                false,
                String.class,
                Invoice.class,
                (Object object) -> ((Invoice) object).getClient().getDIC(), //TODO: add DIC to proper person
                null
        ));
        super.addColumn(new Column<String, Invoice>(
                "Customer Name",
                "test3",
                false,
                String.class,
                Invoice.class,
                (Object object) -> ((Invoice) object).getClient().getName(),
                null
        ));

    }
}
