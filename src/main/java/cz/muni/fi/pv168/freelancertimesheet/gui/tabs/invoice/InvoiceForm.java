package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.invoice;

import com.github.lgooddatepicker.components.DatePicker;
import cz.muni.fi.pv168.freelancertimesheet.backend.PersistanceManager;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.*;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.ClientImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.InvoiceImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.IssuerImpl;
import cz.muni.fi.pv168.freelancertimesheet.gui.I18N;
import cz.muni.fi.pv168.freelancertimesheet.gui.elements.DateTimePickerFactory;
import cz.muni.fi.pv168.freelancertimesheet.gui.elements.TextFieldFactory;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.FormModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.chooseWork.ChooseWorkWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Supplier;

public class InvoiceForm extends FormModel {
    private final I18N i18n = new I18N(getClass());

    private JLabel totalPriceField;

    private List<Work> selectedWorksData;

    private Supplier<Void> onConfirmCallback = null;

    private JLabel selectedWorks; // TODO add proper container

    public InvoiceForm() {
        super();
    }

    public InvoiceForm(Supplier<Void> onConfirmCallback) {
        super();
        this.onConfirmCallback = onConfirmCallback;
    }

    private JPanel buildWorkPicker() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        JButton workPicker = new JButton(i18n.getString("chooseWork"));
        InvoiceForm invoiceForm = this;
        workPicker.addActionListener(e -> {
            JFrame popup = ChooseWorkWindow.setup(this::assignWorkSelection);
            popup.setVisible(true);
        });
        selectedWorks = new JLabel(i18n.getString("selectedWork") + " 0");
        panel.add(selectedWorks);
        panel.add(workPicker);
        inputFields.add(workPicker);
        return panel;
    }

    private InvoiceForm assignWorkSelection(List<Work> selectedWorksData) {
        this.selectedWorksData = selectedWorksData;
        return this;
    }

    private JLabel priceDisplay() {
        totalPriceField = new JLabel("0 " + i18n.getGlobalString("currency"));
        return totalPriceField;
    }

    @Override
    public InvoiceForm setupNested() {
        addRow(new JLabel(i18n.getString("customerName")), TextFieldFactory.createWrappedTextField());
        addRow(new JLabel(i18n.getString("address")), TextFieldFactory.createWrappedTextField());
        addRow(new JLabel(i18n.getString("email")), TextFieldFactory.createWrappedTextField());
        addRow(new JLabel(i18n.getString("telephoneNumber")), TextFieldFactory.createWrappedTextField());
        addRow(new JLabel(i18n.getString("ICO")), TextFieldFactory.createWrappedTextField());
        addRow(new JLabel(i18n.getString("DIC")), TextFieldFactory.createWrappedTextField());
        addRow(new JLabel(i18n.getString("invoiceDate")), DateTimePickerFactory.createGenericDatePicker("Select Date"));
        addRow(new JLabel(i18n.getString("dueDate")), DateTimePickerFactory.createGenericDatePicker("Select Date"));
        addRow(new JLabel(i18n.getString("work")), buildWorkPicker());
        addRow(new JLabel(i18n.getString("total")), priceDisplay());
        addConfirmButton();
        makeConfirmAddData();
        return this;
    }

    public InvoiceForm updateSelectedWork(int count) {
        selectedWorks.setText(i18n.getString("selectedWork") + " " + count);
        return this;
    }

    public static InvoiceForm setup(Supplier<Void> onConfirmCallback) {
        InvoiceForm invoiceForm = new InvoiceForm(onConfirmCallback);
        invoiceForm
                .setupLayout()
                .setupVisuals()
                .setupNested();
        return invoiceForm;
    }

    public static InvoiceForm setup() {
        return setup((Supplier<Void>) null);
    }

    private Invoice getDataFromForm() {
        TextFieldFactory.CustomWrappedClass clientName = (TextFieldFactory.CustomWrappedClass)inputFields.get(0);
        TextFieldFactory.CustomWrappedClass clientAddress = (TextFieldFactory.CustomWrappedClass)inputFields.get(1);
        TextFieldFactory.CustomWrappedClass email = (TextFieldFactory.CustomWrappedClass)inputFields.get(2);
        TextFieldFactory.CustomWrappedClass phoneNumber = (TextFieldFactory.CustomWrappedClass)inputFields.get(3);
        TextFieldFactory.CustomWrappedClass ico = (TextFieldFactory.CustomWrappedClass)inputFields.get(4);
        TextFieldFactory.CustomWrappedClass dic = (TextFieldFactory.CustomWrappedClass)inputFields.get(5);

        //TODO: add email and phone number fields
        Client client = (Client) ClientImpl.createEntity(
                clientName.getText(),
                clientAddress.getText(),
                ico.getText(),
                dic.getText(),
                phoneNumber.getText(),
                email.getText());
        //TODO: add issuer info
        Issuer issuer = (Issuer) IssuerImpl.createEntity(
                clientName.getText(),
                clientAddress.getText(),
                ico.getText(), dic.getText(),
                phoneNumber.getText(),
                email.getText());
        ZonedDateTime issueDate = ((DatePicker) inputFields.get(6)).getDate().atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime dueDate = ((DatePicker) inputFields.get(7)).getDate().atStartOfDay(ZoneId.systemDefault());
        return InvoiceImpl.createInvoice(client, issuer, issueDate, dueDate, selectedWorksData);
    }

    private void validateData(Invoice invoice) {
        invoice.validateAttributes();
    }

    private void addDataToDatabase() {
        Invoice invoice = getDataFromForm();
        validateData(invoice);
        PersistanceManager.persistInvoice(invoice);
    }

    private void makeConfirmAddData() {
        confirmButton.addActionListener((ActionEvent e) -> {
            addDataToDatabase();
            if (onConfirmCallback != null)
                onConfirmCallback.get();
        });
    }

}
