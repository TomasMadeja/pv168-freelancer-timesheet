package cz.muni.fi.pv168.freelancertimesheet.gui.tabs;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;

import javax.swing.*;
import java.awt.*;

public class InvoiceTab extends JPanel implements GenericElement {

    public InvoiceTab() {
        super();
    }

    @Override
    public InvoiceTab setupLayout() {
        setLayout(new FlowLayout());
        return this;
    }

    @Override
    public InvoiceTab setupVisuals() {
        return this;
    }

    @Override
    public InvoiceTab setupNested() {
        var java = new JCheckBox("Java");
        var is = new JCheckBox("Is");
        var awesome = new JCheckBox("Awesome :)");
        add(java);
        add(is);
        add(awesome);
        return this;
    }

    public static InvoiceTab setup() {
        InvoiceTab invoiceTab = new InvoiceTab();
        invoiceTab
                .setupLayout()
                .setupVisuals()
                .setupNested();
        return invoiceTab;
    }
}
