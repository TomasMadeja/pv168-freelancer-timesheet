package cz.muni.fi.pv168.freelancertimesheet.gui.models;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Invoice;
import cz.muni.fi.pv168.freelancertimesheet.gui.I18N;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.InvoiceContainer;

import java.time.ZonedDateTime;

public class InvoiceTableModel extends TableModel<Invoice> {

    private final I18N i18n = new I18N(getClass());

    public InvoiceTableModel(InvoiceContainer container) {
        super(container);
        createColumns();
    }

    private void createColumns() {
        super.addColumn(new Column<ZonedDateTime, Invoice>(
                i18n.getString("invoiceDate"),
                "test1",
                false,
                ZonedDateTime.class,
                Invoice.class,
                (Object object) -> ((Invoice) object).getIssueDate(),
                null
        ));
        super.addColumn(new Column<ZonedDateTime, Invoice>(
                i18n.getString("dueDate"),
                "test2",
                false,
                ZonedDateTime.class,
                Invoice.class,
                (Object object) -> ((Invoice) object).getDueDate(),
                null
        ));
        super.addColumn(new Column<String, Invoice>(
                i18n.getString("ICO"),
                "test3",
                false,
                String.class,
                Invoice.class,
                (Object object) -> ((Invoice) object).getClient().getICO(), //TODO: add ICO to proper person
                null
        ));
        super.addColumn(new Column<String, Invoice>(
                i18n.getString("DIC"),
                "test3",
                false,
                String.class,
                Invoice.class,
                (Object object) -> ((Invoice) object).getClient().getDIC(), //TODO: add DIC to proper person
                null
        ));
        super.addColumn(new Column<String, Invoice>(
                i18n.getString("customerName"),
                "test3",
                false,
                String.class,
                Invoice.class,
                (Object object) -> ((Invoice) object).getClient().getName(),
                null
        ));

    }
}
