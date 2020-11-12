package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.invoice;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.Popups;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        JButton addWorkBtn = new JButton("Add Work");
        addWorkBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame popup = Popups.GetTaskTabInvoice();
                popup.setVisible(true);
            }
        });
        invoiceForm.add(addWorkBtn);
        return invoiceForm;
    }
}
