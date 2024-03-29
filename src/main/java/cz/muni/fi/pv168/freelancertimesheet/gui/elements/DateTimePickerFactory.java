package cz.muni.fi.pv168.freelancertimesheet.gui.elements;


import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;

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

    public static DatePicker createGenericDatePicker(String title) {
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        DatePicker datePicker = new DatePicker(dateSettings);
        datePicker.getComponentDateTextField().setEditable(false);

        JButton datePickerButton = datePicker.getComponentToggleCalendarButton();
        datePicker.setText(title);

        return datePicker;
    }

    public static DateTimePicker createGenericDateTimePicker(String dateTitle, String timeTitle) {
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        var datePicker = new DateTimePicker(dateSettings, new TimePickerSettings());
        datePicker.getDatePicker().getComponentDateTextField().setEditable(false);
        datePicker.getTimePicker().getComponentTimeTextField().setEditable(false);

        datePicker.getDatePicker().getComponentToggleCalendarButton().setText(dateTitle);
        datePicker.getTimePicker().getComponentTimeTextField().setText(timeTitle);

        return datePicker;
    }
}
