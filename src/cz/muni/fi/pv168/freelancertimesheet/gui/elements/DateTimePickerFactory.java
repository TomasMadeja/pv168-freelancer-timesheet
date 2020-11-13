package cz.muni.fi.pv168.freelancertimesheet.gui.elements;

import cz.muni.fi.pv168.freelancertimesheet.gui.tabs.invoice.InvoiceForm;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

public class DateTimePickerFactory {

    public static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

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

    public static class CustomDatePicker extends JDatePickerImpl {

        /**
         * You are able to set the format of the date being displayed on the label.
         * Formatting is described at:
         * <p>
         * http://java.sun.com/j2se/1.4.2/docs/api/java/text/SimpleDateFormat.html
         *
         * @param datePanel
         * @param formatter
         */
        public CustomDatePicker(JDatePanelImpl datePanel, JFormattedTextField.AbstractFormatter formatter) {
            super(datePanel, formatter);
        }

        @Override
        public void setEnabled(boolean enabled) {
            super.setEnabled(enabled);
            getComponent(1).setEnabled(enabled);
        }
    }

    public static JDatePickerImpl createGenericDatePicker() {
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new CustomDatePicker(datePanel, new DateLabelFormatter());
        datePicker.setBounds(110, 100, 200, 25);
        model.setSelected(true);
        datePicker.setVisible(true);

        return datePicker;
    }
}
