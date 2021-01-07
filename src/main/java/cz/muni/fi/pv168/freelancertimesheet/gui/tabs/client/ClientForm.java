package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.client;

import cz.muni.fi.pv168.freelancertimesheet.gui.I18N;
import cz.muni.fi.pv168.freelancertimesheet.gui.TasksPopup;
import cz.muni.fi.pv168.freelancertimesheet.gui.elements.DateTimePickerFactory;
import cz.muni.fi.pv168.freelancertimesheet.gui.elements.TextFieldFactory;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.FormModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.invoice.InvoiceForm;

import javax.swing.*;
import java.awt.*;

public class ClientForm extends FormModel {

    private final I18N i18n = new I18N(getClass());

    public ClientForm() {
        super();
    }

    @Override
    public ClientForm setupNested() {
        addRow(new JLabel(i18n.getString("customerName")), TextFieldFactory.createWrappedTextField());
        addRow(new JLabel(i18n.getString("address")), TextFieldFactory.createWrappedTextField());
        addRow(new JLabel(i18n.getString("ICO")), TextFieldFactory.createWrappedTextField());
        addRow(new JLabel(i18n.getString("DIC")), TextFieldFactory.createWrappedTextField());
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
