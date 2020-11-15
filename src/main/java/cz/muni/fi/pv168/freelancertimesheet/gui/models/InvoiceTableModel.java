package cz.muni.fi.pv168.freelancertimesheet.gui.models;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Invoice;

import java.util.Date;

public class InvoiceTableModel extends TableModel<Invoice> {

    public InvoiceTableModel() {
        super(new Column[] { new Column(
                        "Invoice Date",
                        "test1",
                        Date.class,
                        Date.class, // TODO replace with actual object
                        (Object object) -> new Date()
                ),
                new Column(
                        "Due Date",
                        "test2",
                        Date.class,
                        Date.class, // TODO replace with actual object
                        (Object object) -> new Date()
                ),
                new Column(
                        "ICO",
                        "test3",
                        String.class,
                        Date.class, // TODO replace with actual object
                        (Object object) -> "25596641"
                ),
                new Column(
                        "DIC",
                        "test3",
                        String.class,
                        Date.class, // TODO replace with actual object
                        (Object object) -> "CZ1234567890"
                ),
                new Column(
                        "Customer Name",
                        "test3",
                        String.class,
                        Date.class, // TODO replace with actual object
                        (Object object) -> "Test Customer"
                )});
    }

//    private interface AttributePicker {
//
//    }
}
