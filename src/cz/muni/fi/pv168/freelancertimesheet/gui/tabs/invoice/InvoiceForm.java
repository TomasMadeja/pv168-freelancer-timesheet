package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.invoice;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.Popups;
import cz.muni.fi.pv168.freelancertimesheet.gui.elements.DateTimePickerFactory;
import cz.muni.fi.pv168.freelancertimesheet.gui.elements.TextFieldFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class InvoiceForm extends JPanel implements GenericElement {
    private GridBagLayout layout;
    private GridBagConstraints layoutConstraints;

    private JButton confirmButton;
    private JButton cancelButton;
    private List<Component> inputFields;

    private JLabel totalPriceField;

    private JLabel selectedWorks; // TODO add proper container
    
    public InvoiceForm() {
        super();
        inputFields = new ArrayList<Component>();
    }

    private InvoiceForm addRow(Component left, Component right) {
        layoutConstraints.gridwidth = GridBagConstraints.RELATIVE;
        layoutConstraints.weightx = 0;
        add(left, layoutConstraints);
        layoutConstraints.gridwidth = GridBagConstraints.REMAINDER;
        layoutConstraints.gridx = 1;
        layoutConstraints.weightx = 1;
        add(right, layoutConstraints);
        inputFields.add(right);
        layoutConstraints.gridy = layoutConstraints.gridy + 1;
        layoutConstraints.gridx = 0;
        return this;
    }

    private InvoiceForm addConfirmButton() {
        JPanel panel = new JPanel(new BorderLayout());
        confirmButton = new JButton("Confirm");
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener((ActionEvent e) -> disableForm());
        JPanel subpanel = new JPanel(new GridLayout(1, 2));
        subpanel.add(cancelButton);
        subpanel.add(confirmButton);
        panel.add(subpanel, BorderLayout.EAST);
        layoutConstraints.gridx = 1;
        layoutConstraints.gridwidth = GridBagConstraints.RELATIVE;
        layoutConstraints.weightx = 0;
        add(panel, layoutConstraints);
        layoutConstraints.gridy = layoutConstraints.gridy + 1;
        layoutConstraints.gridx = 0;
        return this;
    }

    private JPanel buildWorkPicker() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        JButton workPicker = new JButton("Choose Work");
        workPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame popup = Popups.GetTaskTabInvoice();
                popup.setVisible(true);
            }
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
    public InvoiceForm setupLayout() {
//        GridLayout topLayout = new GridLayout(6, 2);
//        setLayout(topLayout);
        layout = new GridBagLayout();
        layoutConstraints = new GridBagConstraints();
        layoutConstraints.weighty = 1;
        layoutConstraints.fill = GridBagConstraints.BOTH;
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 0;
        setLayout(layout);
//        topLayout.setHgap(10);
//        topLayout.setVgap(20);
        return this;
    }

    @Override
    public InvoiceForm setupVisuals() {
        return this;
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

    public InvoiceForm disableForm() {
        enableForm(false);
        return this;
    }

    public InvoiceForm enableForm() {
        enableForm(true);
        return this;
    }

    public InvoiceForm enableForm(boolean enabled) {
        confirmButton.setEnabled(enabled);
        cancelButton.setEnabled(enabled);
        for (Component c: inputFields) {
            c.setEnabled(enabled);
        }
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
