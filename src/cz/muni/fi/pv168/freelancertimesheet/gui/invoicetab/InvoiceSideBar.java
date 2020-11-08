package cz.muni.fi.pv168.freelancertimesheet.gui.invoicetab;


import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;

import javax.swing.*;
import java.awt.*;

public class InvoiceSideBar extends JPanel implements GenericElement {

    public InvoiceSideBar(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public InvoiceSideBar(LayoutManager layout) {
        super(layout);
    }

    public InvoiceSideBar(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public InvoiceSideBar() {
        super();
    }

    @Override
    public InvoiceSideBar setupLayout() {
        return this;
    }

    @Override
    public InvoiceSideBar setupVisuals() {
        return this;
    }

    @Override
    public InvoiceSideBar setupNested() {
        return this;
    }

    public InvoiceSideBar setup() {
        return null;
    }
}
