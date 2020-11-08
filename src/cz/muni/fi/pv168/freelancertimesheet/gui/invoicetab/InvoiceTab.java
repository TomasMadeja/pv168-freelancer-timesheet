package cz.muni.fi.pv168.freelancertimesheet.gui.invoicetab;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;

import javax.swing.*;
import java.awt.*;

public class InvoiceTab extends JPanel implements GenericElement {

    public InvoiceTab() {
        super();
    }

    public InvoiceTab(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public InvoiceTab(LayoutManager layout) {
        super(layout);
    }

    public InvoiceTab(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    @Override
    public InvoiceTab setupLayout() {
        return null;
    }

    @Override
    public InvoiceTab setupVisuals() {
        return this;
    }

    @Override
    public InvoiceTab setupNested() {
        return null;
    }

    public static InvoiceTab setup() {
        return null;
    }
}
