package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.task;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;

import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

public class TaskForm extends JPanel implements GenericElement<TaskForm> {

    private JPanel centerPanel;

    public TaskForm() {
        super();
    }


    @Override
    public TaskForm setupLayout() {
        BoxLayout mainLayout = new BoxLayout(this, BoxLayout.X_AXIS);
        this.setLayout(mainLayout);

        centerPanel = new JPanel();
        BoxLayout layout = new BoxLayout(centerPanel, BoxLayout.Y_AXIS);
        centerPanel.setLayout(layout);

        this.add(new JPanel());
        this.add(centerPanel);
        this.add(new JPanel());

        return this;
    }

    @Override
    public TaskForm setupVisuals() {
        return this;
    }

    private DatePicker setupDatePicker() {
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        DatePicker datePicker = new DatePicker(dateSettings);
        datePicker.setDateToToday();

        JButton datePickerButton = datePicker.getComponentToggleCalendarButton();
        datePickerButton.setText("Pick date");

        // TODO
//        URL calendarIconUrl = Main.class.getResource("/resource/datepickerbutton.png");
//        ImageIcon calendarIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(calendarIconUrl));
//        datePickerButton.setIcon(calendarIcon);

        return datePicker;
    }

    private TimePicker setupTimePicker() {
        TimePickerSettings timeSettings = new TimePickerSettings();
        timeSettings.setColor(TimePickerSettings.TimeArea.TimePickerTextValidTime, Color.blue);
        timeSettings.initialTime = LocalTime.now();
        timeSettings.setFormatForMenuTimes(PickerUtilities.createFormatterFromPatternString(
                "HH:mm", timeSettings.getLocale()));
        timeSettings.setFormatForDisplayTime(PickerUtilities.createFormatterFromPatternString(
                "HH:mm", timeSettings.getLocale()));
        timeSettings.generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.FifteenMinutes, null, null);

        return new TimePicker(timeSettings);
    }

    @Override
    public TaskForm setupNested() {
        JPanel topPanel = new JPanel(new GridLayout(6, 2));
        topPanel.add(new JLabel("Enter date:"));

        topPanel.add(setupDatePicker());

        topPanel.add(new JLabel("Enter task name:"));
        topPanel.add(new JTextField(20));

        topPanel.add(new JLabel("Enter description:"));
        topPanel.add(new JTextField(20));

        topPanel.add(new JLabel("Enter start time:"));
        topPanel.add(setupTimePicker());

        topPanel.add(new JLabel("Enter end time:"));
        topPanel.add(setupTimePicker());

        topPanel.add(new JLabel("Enter task type:"));
        JPanel taskTypePanel = new JPanel();
        taskTypePanel.add(new JTextField(20));
        taskTypePanel.add(new JButton("Manage work type"));
        topPanel.add(taskTypePanel);

        JPanel confirmPanel = new JPanel();
        confirmPanel.add(new JButton("Confirm"));

        centerPanel.add(topPanel);
        centerPanel.add(confirmPanel);

        return this;
    }

    public static TaskForm setup() {
        return new TaskForm()
                .setupLayout()
                .setupVisuals()
                .setupNested();
    }
}
