package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.invoice;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;

import javax.swing.*;
import java.awt.*;

public class InvoiceForm extends JPanel implements GenericElement {
    public InvoiceForm(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public InvoiceForm(LayoutManager layout) {
        super(layout);
    }

    public InvoiceForm(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public InvoiceForm() {
        super();
    }

    @Override
    public InvoiceForm setupLayout() {
        return this;
    }

    @Override
    public InvoiceForm setupVisuals() {
        return this;
    }

    @Override
    public InvoiceForm setupNested() {
        return this;
    }

    public static InvoiceForm setup() {
        InvoiceForm invoiceForm = new InvoiceForm();
        return invoiceForm;
    }
}
