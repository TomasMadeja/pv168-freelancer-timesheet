package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.client;

import cz.muni.fi.pv168.freelancertimesheet.gui.TasksPopup;
import cz.muni.fi.pv168.freelancertimesheet.gui.elements.DateTimePickerFactory;
import cz.muni.fi.pv168.freelancertimesheet.gui.elements.TextFieldFactory;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.FormModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.invoice.InvoiceForm;

import javax.swing.*;
import java.awt.*;

public class ClientForm extends FormModel {

    public ClientForm() {
        super();
    }

    @Override
    public ClientForm setupNested() {
        addRow(new JLabel("Customer Name:"), TextFieldFactory.createWrappedTextField());
        addRow(new JLabel("Address:"), TextFieldFactory.createWrappedTextField());
        addRow(new JLabel("ICO:"), TextFieldFactory.createWrappedTextField());
        addRow(new JLabel("DIC:"), TextFieldFactory.createWrappedTextField());
        addConfirmButton();
        return this;
    }

    public static ClientForm setup() {
        ClientForm clientForm = new ClientForm();
        clientForm
                .setupLayout()
                .setupVisuals()
                .setupNested();
        return clientForm;
    }
}
