package cz.muni.fi.pv168.freelancertimesheet.gui.popups.taskform;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkTypeImpl;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.FormModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.TimeZone;

public class TaskForm extends FormModel {

    private final DatePicker datePicker;
    private final TimePicker startTimePicker;
    private final TimePicker endTimePicker;
    private final JTextField nameTextField = new JTextField();
    private final JTextArea descriptionTextArea = new JTextArea();
    private final JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);

    private final JTextField workTypeTextField;
    private final JButton workTypeButton;


    public TaskForm() {
        super();

        datePicker = setupDatePicker();
        startTimePicker = setupTimePicker();
        endTimePicker = setupTimePicker();

        workTypeTextField = new JTextField();
        workTypeButton = new JButton("Manage work type");
    }

    public static TaskForm setup() {
        TaskForm form = new TaskForm();
        form.setupLayout()
                .setupVisuals()
                .setupNested();
        return form;
    }


    @Override
    public TaskForm setupNested() {
        addRow(new JLabel("Date:"), datePicker);
        addRow(new JLabel("Name:"), nameTextField);
        addRow(new JLabel("Description:"), descriptionScrollPane);
        addRow(new JLabel("Start time:"), startTimePicker);
        addRow(new JLabel("End time:"), endTimePicker);

        // TODO setup workTypePanel
        var workTypePanel = new JPanel();
//        workTypeButton.addActionListener(e -> WorkTypeWindow.setup());
        workTypePanel.add(workTypeTextField, workTypeButton);
        addRow(new JLabel("Task type:"), workTypePanel);

        addConfirmButton();
        return this;
    }

    public void setupConfirmButtonAction(ActionListener listener) {
        confirmButton.addActionListener(listener);
    }

    private DatePicker setupDatePicker() {
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        var datePicker = new DatePicker(dateSettings);

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

    public Work prepareDataFromForms() {
        // TODO add support for WorkTypes
        var tmpWorkType = WorkTypeImpl.createWorkType(
                "TestType1",
                "Never gonna give you up",
                new BigDecimal("20")
        );

        return WorkImpl.createWork(
                nameTextField.getText(),
                descriptionTextArea.getText(),
                getDateTimeFromForms(startTimePicker.getTime()),
                getDateTimeFromForms(endTimePicker.getTime()),
                tmpWorkType
        );
    }

    private ZonedDateTime getDateTimeFromForms(LocalTime time) {
        return LocalDateTime.of(datePicker.getDate(), time).atZone(TimeZone.getDefault().toZoneId());
    }

}
