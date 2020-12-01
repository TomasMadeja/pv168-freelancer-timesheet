package cz.muni.fi.pv168.freelancertimesheet.gui.popups.taskform;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import cz.muni.fi.pv168.freelancertimesheet.gui.GenericElement;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.WorkTypeWindow;

import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;

public class TaskForm extends JFrame implements GenericElement<TaskForm> {

    private JPanel centerPanel;


    private JTextField taskNameField, descField, taskTypeField;
    private JButton workTypeButton, confirmButton;

    private DatePicker datePicker;
    private TimePicker startTimePicker, endTimePicker;

    private List<JComponent> forms;

    private GridBagConstraints gbc;

    public TaskForm() {
        super();
    }


    @Override
    public TaskForm setupLayout() {
//        var mainLayout = new BoxLayout(this, BoxLayout.X_AXIS);
//        this.setLayout(mainLayout);

        centerPanel = new JPanel();
        BoxLayout layout = new BoxLayout(centerPanel, BoxLayout.Y_AXIS);
        centerPanel.setLayout(layout);

//        this.add(new JPanel());
        this.add(centerPanel);
//        this.add(new JPanel());

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

        // TODO
//        URL calendarIconUrl = Main.class.getResource("/resource/datepickerbutton.png");
//        ImageIcon calendarIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(calendarIconUrl));
//        datePickerButton.setIcon(calendarIcon);

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

        forms = List.of(taskNameField, taskTypeField, startTimePicker, endTimePicker, descField);

        taskTypeField.setEditable(false);
        confirmButton.addActionListener(e -> confirmFilledForms());
        workTypeButton.addActionListener(e -> WorkTypeWindow.setup());
    }

    @Override
    public TaskForm setupNested() {
        JPanel panel = new JPanel(new GridLayout(6, 2));
        initForms();

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

    public void enableForm() {
        taskNameField.setEnabled(true);
        descField.setEnabled(true);
        taskTypeField.setEnabled(true);
        workTypeButton.setEnabled(true);
        confirmButton.setEnabled(true);

        datePicker.setEnabled(true);
        startTimePicker.setEnabled(true);
        endTimePicker.setEnabled(true);
    }

    private void confirmFilledForms() {
        String[] emptyData = new String[forms.size()];
        Arrays.fill(emptyData, "");
        fillForm(emptyData);
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
