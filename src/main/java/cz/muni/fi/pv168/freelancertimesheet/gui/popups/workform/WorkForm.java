package cz.muni.fi.pv168.freelancertimesheet.gui.popups.workform;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import cz.muni.fi.pv168.freelancertimesheet.backend.PersistanceManager;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkImpl;
import cz.muni.fi.pv168.freelancertimesheet.backend.orm.WorkTypeImpl;
import cz.muni.fi.pv168.freelancertimesheet.gui.I18N;
import cz.muni.fi.pv168.freelancertimesheet.gui.containers.WorkContainer;
import cz.muni.fi.pv168.freelancertimesheet.gui.elements.DateTimePickerFactory;
import cz.muni.fi.pv168.freelancertimesheet.gui.elements.TextFieldFactory;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.FormModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.table.ChooseWorkTypeWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.TimeZone;

public class WorkForm extends FormModel implements iWorkTypeSetter {

    private final I18N i18n = new I18N(getClass());

    private final DatePicker datePicker;
    private final DateTimePicker startTimePicker;
    private final DateTimePicker endTimePicker;
    private final TextFieldFactory.CustomWrappedClass nameTextField;
    private final TextFieldFactory.CustomWrappedClass descriptionTextArea;

    private final JTextField workTypeTextField;
    private final JButton workTypeButton;

    private WorkType workType;

    private WorkContainer container;

    public WorkForm(WorkContainer container) {
        super();

        nameTextField = TextFieldFactory.createWrappedTextField();
        descriptionTextArea = TextFieldFactory.createWrappedTextField();

        datePicker = setupDatePicker();
        startTimePicker = DateTimePickerFactory.createGenericDateTimePicker("", "");
        endTimePicker = DateTimePickerFactory.createGenericDateTimePicker("", "");

        workTypeTextField = new JTextField(20);
        workTypeTextField.setEditable(false);
        workTypeButton = new JButton(i18n.getString("manageWorkType"));

        this.container = container;
    }

    public static WorkForm setup(WorkContainer container) {
        WorkForm form = new WorkForm(container);
        form.setupLayout()
                .setupVisuals()
                .setupNested();
        return form;
    }


    @Override
    public WorkForm setupNested() {
        var workTypePanel = new JPanel();
        workTypePanel.setLayout(new GridBagLayout());
        var c = new GridBagConstraints();
        c.gridx = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.insets = new Insets(0, 0, 0, 5);
        workTypePanel.add(workTypeTextField, c);
        c.gridx = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        workTypePanel.add(workTypeButton, c);
        // TODO
        workTypeButton.addActionListener(e -> ChooseWorkTypeWindow.setup(this));

        addRow(new JLabel(i18n.getString("taskType")), workTypePanel);

//        addRow(new JLabel(i18n.getString("date")), datePicker);
        addRow(new JLabel(i18n.getString("name")), nameTextField);
        addRow(new JLabel(i18n.getString("description")), descriptionTextArea);
        addRow(new JLabel(i18n.getString("startTime")), startTimePicker);
        addRow(new JLabel(i18n.getString("endTime")), endTimePicker);

        addConfirmButton();
        makeConfirmAddData();
        return this;
    }

    @Override
    public void setWorkType(WorkType obj) {
        this.workType = obj;
        workTypeTextField.setText(workType.getName());
    }

    public void setupConfirmButtonAction(ActionListener listener) {
        confirmButton.addActionListener(listener);
    }

    private DatePicker setupDatePicker() {
        return DateTimePickerFactory.createGenericDatePicker(i18n.getString("pickDate"));
        /*
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        var datePicker = new DatePicker(dateSettings);
        datePicker.getComponentDateTextField().setEditable(false);

        JButton datePickerButton = datePicker.getComponentToggleCalendarButton();
        datePickerButton.setText(i18n.getString("pickDate"));

        return datePicker;
        */
    }

    private TimePicker setupTimePicker() {
        TimePickerSettings timeSettings = new TimePickerSettings();
        timeSettings.setColor(TimePickerSettings.TimeArea.TimePickerTextValidTime, Color.blue);
        timeSettings.setFormatForMenuTimes(PickerUtilities.createFormatterFromPatternString(
                "HH:mm", timeSettings.getLocale()));
        timeSettings.setFormatForDisplayTime(PickerUtilities.createFormatterFromPatternString(
                "HH:mm", timeSettings.getLocale()));
        timeSettings.generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.FifteenMinutes, null, null);
        var timePicker = new TimePicker(timeSettings);
        timePicker.getComponentTimeTextField().setEditable(false);
        return timePicker;
    }

    private Work prepareDataFromForms() {
        // TODO do not allow workType to be unset
        // This is just a temporary fix that uses custom default WorkType
        if (workType == null) {
            workType = WorkTypeImpl.createWorkType(
                    "TestType1",
                    "Never gonna give you up",
                    new BigDecimal("20")
            );
        }

        return WorkImpl.createWork(
                nameTextField.getText(),
                descriptionTextArea.getText(),
                startTimePicker.getDateTimeStrict().atZone(TimeZone.getDefault().toZoneId()),
                endTimePicker.getDateTimeStrict().atZone(TimeZone.getDefault().toZoneId()),
                workType
        );
    }

    private ZonedDateTime getDateTimeFromForms(LocalTime time) {
        return LocalDateTime.of(datePicker.getDate(), time).atZone(TimeZone.getDefault().toZoneId());
    }

    private void addDataToDatabase(Work work) {
        if (work == null) return;
        PersistanceManager.persistWork(work);
    }

    private void makeConfirmAddData() {
        confirmButton.addActionListener(
            (ActionEvent e) -> {
                confirmButton.setEnabled(false);
                Work work = prepareDataFromForms();
                new SwingWorker<Void, Void>() {
                    @Override
                    public Void doInBackground() {
                        addDataToDatabase(work);
                        if (container != null) container.refresh();
                        return null;
                    }

                    @Override
                    protected void done() {
                        try {
                            if (confirmCallback != null)
                                confirmCallback.call();
                        } catch (Exception ignore) {
                        } finally {
                            confirmButton.setEnabled(true);
                        }
                    }
                }.execute();
            }
        );
    }

}
