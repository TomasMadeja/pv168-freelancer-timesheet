package cz.muni.fi.pv168.freelancertimesheet.gui.invoicetab;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;

import javax.swing.*;
import java.awt.*;

public class InvoiceWindow extends JPanel implements GenericElement {
    public InvoiceWindow(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public InvoiceWindow(LayoutManager layout) {
        super(layout);
    }

    public InvoiceWindow(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public InvoiceWindow() {
        super();
    }

    @Override
    public InvoiceWindow setupLayout() {
        return this;
    }

    @Override
    public InvoiceWindow setupVisuals() {
        return this;
    }

    @Override
    public InvoiceWindow setupNested() {
        return this;
    }

    public InvoiceWindow setup() {
        return null;
    }
}
