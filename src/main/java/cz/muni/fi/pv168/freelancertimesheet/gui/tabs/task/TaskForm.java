package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.task;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.FormModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.WorkTypeWindow;

import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;

public class TaskForm extends FormModel {

    private JTextField taskNameField, descField, taskTypeField;
    private JButton workTypeButton;

    private DatePicker datePicker;
    private TimePicker startTimePicker, endTimePicker;

    private List<JComponent> forms;

    private DatePicker setupDatePicker() {
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        datePicker = new DatePicker(dateSettings);

        JButton datePickerButton = datePicker.getComponentToggleCalendarButton();
        datePickerButton.setText("Pick date");

        return datePicker;
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

    private void initForms() {
        taskNameField = new JTextField(20);
        descField = new JTextField(20);
        taskTypeField = new JTextField(20);
        workTypeButton = new JButton("Manage work type");

        datePicker = setupDatePicker();
        startTimePicker = setupTimePicker();
        endTimePicker = setupTimePicker();

        forms = List.of(taskNameField, taskTypeField, startTimePicker, endTimePicker, descField);

        taskTypeField.setEditable(false);
        workTypeButton.addActionListener(e -> WorkTypeWindow.setup());
    }

    @Override
    public TaskForm setupNested() {
        initForms();

        addRow(new JLabel("Enter task name:"), taskNameField);
        addRow(new JLabel("Enter description:"), descField);
        addRow(new JLabel("Enter start time:"), startTimePicker);
        addRow(new JLabel("Enter end time:"), endTimePicker);
        JPanel taskTypePanel = new JPanel();
        taskTypePanel.add(taskTypeField);
        taskTypePanel.add(workTypeButton);
        addRow(new JLabel("Enter task type:"), taskTypePanel);
        addConfirmButton();
        return this;
    }

    public void fillForm(Object[] rowData) {
        String data;
        JComponent element;
        for (int i = 1; i < forms.size(); i++) {
            element = forms.get(i);
            if (rowData[i] == null)
                data = "";
            else
                data = rowData[i].toString();

            if (element instanceof JTextField) {
                var textField = (JTextField) element;
                textField.setText(data);
            } else if (element instanceof DatePicker) {
                var datePicker = (DatePicker) element;
                datePicker.setText(data); // TODO date is not showing
            } else if (element instanceof TimePicker) {
                var timePicker = (TimePicker) element;
                timePicker.setText(data);
            }
        }

        enableForm();
    }

    public static TaskForm setup() {
        TaskForm taskForm = new TaskForm();
        return (TaskForm) taskForm
                .setupLayout()
                .setupVisuals()
                .setupNested();
    }
}
