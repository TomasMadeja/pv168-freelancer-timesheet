package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.task;

import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import cz.muni.fi.pv168.freelancertimesheet.gui.elements.TextFieldFactory;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.FormModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.table.WorkTypeTableWindow;

import javax.swing.*;
import java.awt.*;

public class WorkForm extends FormModel {

    private JLabel workTypeField;

    private JPanel buildWorkTypePicker() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        JButton workTypePicker = new JButton("Choose work type");
        workTypePicker.addActionListener(e -> WorkTypeTableWindow.setup());
        workTypeField = new JLabel("No work type selected");
        panel.add(workTypeField);
        panel.add(workTypePicker);
        inputFields.add(workTypePicker);
        return panel;
    }

    private TimePicker setupTimePicker() {
        TimePickerSettings timeSettings = new TimePickerSettings();
        timeSettings.setColor(TimePickerSettings.TimeArea.TimePickerTextValidTime, Color.blue);
        timeSettings.setFormatForMenuTimes(PickerUtilities.createFormatterFromPatternString(
                "HH:mm", timeSettings.getLocale()));
        timeSettings.setFormatForDisplayTime(PickerUtilities.createFormatterFromPatternString(
                "HH:mm", timeSettings.getLocale()));
        timeSettings.generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.FifteenMinutes, null, null);
        return new TimePicker(timeSettings);
    }

    @Override
    public WorkForm setupNested() {
        addRow(new JLabel("Enter task name:"), TextFieldFactory.createWrappedTextField());
        addRow(new JLabel("Enter description:"), TextFieldFactory.createWrappedTextField());
        addRow(new JLabel("Enter start time:"), setupTimePicker());
        addRow(new JLabel("Enter end time:"), setupTimePicker());
        addRow(new JLabel("Enter task type:"), buildWorkTypePicker());
        addConfirmButton();
        disableForm();
        return this;
    }

    public void fillForm(Object[] rowData) {
        String data;
        Component element;
        for (int i = 0; i < rowData.length; i++) {
            if (inputFields.get(i) instanceof JButton)
                continue;

            element = inputFields.get(i);
            if (rowData[i] == null)
                data = "";
            else
                data = rowData[i].toString();
            if (element instanceof TextFieldFactory.CustomWrappedClass) {
                var textField = (TextFieldFactory.CustomWrappedClass) element;
                textField.setText(data);
            } else if (element instanceof TimePicker) {
                var datePicker = (TimePicker) element;
                datePicker.setText(data); // TODO time is not showing
            }
        }
        enableForm();
    }

    public static WorkForm setup() {
        WorkForm workForm = new WorkForm();
        return (WorkForm) workForm
                .setupLayout()
                .setupVisuals()
                .setupNested();
    }
}
