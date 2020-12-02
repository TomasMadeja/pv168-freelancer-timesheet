package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.invoice;

import cz.muni.fi.pv168.freelancertimesheet.gui.TasksPopup;
import cz.muni.fi.pv168.freelancertimesheet.gui.elements.DateTimePickerFactory;
import cz.muni.fi.pv168.freelancertimesheet.gui.elements.TextFieldFactory;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.FormModel;

import javax.swing.*;
import java.awt.*;

public class InvoiceForm extends FormModel {
    private JLabel totalPriceField;

    private JLabel selectedWorks; // TODO add proper container

    private JPanel buildWorkPicker() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        JButton workPicker = new JButton("Choose Work");
        InvoiceForm invoiceForm = this;
        workPicker.addActionListener(e -> {
            JFrame popup = TasksPopup.setup(invoiceForm);
            popup.setVisible(true);
        });
        selectedWorks = new JLabel("Selected Work: 0");
        panel.add(selectedWorks);
        panel.add(workPicker);
        inputFields.add(workPicker);
        return panel;
    }

    private JLabel priceDisplay() {
        totalPriceField = new JLabel("0 CZK");
        return totalPriceField;
    }

    @Override
    public InvoiceForm setupNested() {
        addRow(new JLabel("Customer Name:"), TextFieldFactory.createWrappedTextField());
        addRow(new JLabel("Address:"), TextFieldFactory.createWrappedTextField());
        addRow(new JLabel("Email:"), TextFieldFactory.createWrappedTextField());
        addRow(new JLabel("Telephone Number:"), TextFieldFactory.createWrappedTextField());
        addRow(new JLabel("ICO:"), TextFieldFactory.createWrappedTextField());
        addRow(new JLabel("DIC:"), TextFieldFactory.createWrappedTextField());
        addRow(new JLabel("Invoice Date:"), DateTimePickerFactory.createGenericDatePicker("Select Date"));
        addRow(new JLabel("Due Date:"), DateTimePickerFactory.createGenericDatePicker("Select Date"));
        addRow(new JLabel("Work:"), buildWorkPicker());
        addRow(new JLabel("Total:"), priceDisplay());
        addConfirmButton();
        return this;
    }

    public InvoiceForm updateSelectedWork(int count) {
        selectedWorks.setText("Selected Work: " + count);
        return this;
    }

    public static InvoiceForm setup() {
        InvoiceForm invoiceForm = new InvoiceForm();
        invoiceForm
                .setupLayout()
                .setupVisuals()
                .setupNested()
                .disableForm();
        return invoiceForm;
    }
}
