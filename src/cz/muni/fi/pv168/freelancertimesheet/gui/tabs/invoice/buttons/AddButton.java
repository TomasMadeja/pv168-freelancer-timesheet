package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.invoice.buttons;

import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.invoice.InvoiceForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AddButton extends AbstractAction {
    private InvoiceForm form;

    public AddButton(InvoiceForm form) {
        super("Add");
        this.form = form;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.form.enableForm();
    }
}
