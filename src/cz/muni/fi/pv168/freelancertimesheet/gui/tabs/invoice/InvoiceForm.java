package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.invoice;

import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

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
        GridLayout topLayout = new GridLayout(6, 2);
        topLayout.setHgap(10);
        topLayout.setVgap(20);

        setLayout(topLayout);
        return this;
    }

    @Override
    public InvoiceForm setupVisuals() {
        return this;
    }

    public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }

    @Override
    public InvoiceForm setupNested() {
        add(new JLabel("Customer Name:"));
        add(new JTextField());
        add(new JLabel("Address:"));
        add(new JTextField());
        add(new JLabel("Email:"));
        add(new JTextField());
        add(new JLabel("Telephone Number:"));
        add(new JTextField());
        add(new JLabel("Invoice Date:"));
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setBounds(110, 100, 200, 25);
        model.setSelected(true);
        datePicker.setVisible(true);
        add(datePicker);
        add(new JLabel("Due Date:"));
        add(new JTextField());
        return this;
    }

    public static InvoiceForm setup() {
        InvoiceForm invoiceForm = new InvoiceForm();
        invoiceForm
                .setupLayout()
                .setupVisuals()
                .setupNested();
        return invoiceForm;
    }
}
