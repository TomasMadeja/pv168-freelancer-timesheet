package cz.muni.fi.pv168.freelancertimesheet.gui.popups.taskform;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import cz.muni.fi.pv168.freelancertimesheet.backend.DBConnectionUtils;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkTypeImpl;
import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.TimeZone;

public class TaskForm extends JFrame implements GenericElement<TaskForm> {

    private JPanel centerPanel;


    private JTextField taskNameField, descField, taskTypeField;
    private JButton workTypeButton, confirmButton;

    private DatePicker datePicker;
    private TimePicker startTimePicker, endTimePicker;


    public TaskForm() {
        super();
    }


    @Override
    public TaskForm setupLayout() {

        centerPanel = new JPanel();
        BoxLayout layout = new BoxLayout(centerPanel, BoxLayout.Y_AXIS);
        centerPanel.setLayout(layout);

        this.add(centerPanel);

        return this;
    }

    @Override
    public TaskForm setupVisuals() {
        return this;
    }

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
        confirmButton = new JButton("Confirm");

        datePicker = setupDatePicker();
        startTimePicker = setupTimePicker();
        endTimePicker = setupTimePicker();

        taskTypeField.setEditable(false);
        confirmButton.addActionListener(e -> confirmFilledForms());
//        workTypeButton.addActionListener(e -> WorkTypeWindow.setup());
    }

    @Override
    public TaskForm setupNested() {
        JPanel panel = new JPanel(new GridLayout(6, 2));
        initForms();

        panel.add(new JLabel("Enter task name:"));
        panel.add(datePicker);

        panel.add(new JLabel("Enter task name:"));
        panel.add(taskNameField);

        panel.add(new JLabel("Enter description:"));
        panel.add(descField);

        panel.add(new JLabel("Enter start time:"));
        panel.add(startTimePicker);

        panel.add(new JLabel("Enter end time:"));
        panel.add(endTimePicker);

        panel.add(new JLabel("Enter task type:"));
        JPanel taskTypePanel = new JPanel();
        taskTypePanel.add(taskTypeField);
        taskTypePanel.add(workTypeButton);
        panel.add(taskTypePanel);

        JPanel confirmPanel = new JPanel();
        confirmPanel.add(confirmButton);

        centerPanel.add(panel);
        centerPanel.add(confirmPanel);

        return this;
    }


    private void confirmFilledForms() {
        // TODO check if all needed fields have value

        pushDataToDatabase(prepareDataFromForms());
        emptyForms();
    }

    private Work prepareDataFromForms() {
        // TODO add support for WorkTypes
        var tmpWorkType = WorkTypeImpl.createWorkType(
                "TestType1",
                "Never gonna give you up",
                new BigDecimal("20")
        );

        return WorkImpl.createWork(
                taskNameField.getText(),
                descField.getText(),
                getDateTimeFromForms(startTimePicker.getTime()),
                getDateTimeFromForms(endTimePicker.getTime()),
                tmpWorkType
        );
    }

    private void emptyForms() {
        datePicker.setText("");
        taskNameField.setText("");
        descField.setText("");
        startTimePicker.setText("");
        endTimePicker.setText("");
    }

    private ZonedDateTime getDateTimeFromForms(LocalTime time) {
        return LocalDateTime.of(datePicker.getDate(), time).atZone(TimeZone.getDefault().toZoneId());
    }

    private void pushDataToDatabase(Work work) {
        var entityManager = DBConnectionUtils.getSessionFactory().createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(work);

        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear();
    }


    public static TaskForm setup() {
        var taskForm = new TaskForm()
                .setupLayout()
                .setupVisuals()
                .setupNested();
        taskForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        taskForm.pack();
        taskForm.setVisible(true);
        return taskForm;

    }
}
