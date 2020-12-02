package cz.muni.fi.pv168.freelancertimesheet.gui.tabs.task;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;
import cz.muni.fi.pv168.freelancertimesheet.gui.elements.DateTimePickerFactory;
import cz.muni.fi.pv168.freelancertimesheet.gui.elements.TextFieldFactory;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.FormModel;
import cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.WorkTypeWindow;

import javax.swing.*;
import java.awt.*;

public class WorkForm extends FormModel {

    private JLabel workTypeField;

    private JPanel buildWorkTypePicker() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        JButton workTypePicker = new JButton("Choose work type");
        workTypePicker.addActionListener(e -> WorkTypeWindow.setup());
        workTypeField = new JLabel("No work type selected");
        panel.add(workTypeField);
        panel.add(workTypePicker);
        inputFields.add(workTypePicker);
        return panel;
    }

    @Override
    public WorkForm setupNested() {
        addRow(new JLabel("Enter task name:"), TextFieldFactory.createWrappedTextField());
        addRow(new JLabel("Enter description:"), TextFieldFactory.createWrappedTextField());
        addRow(new JLabel("Enter start time:"), DateTimePickerFactory.createGenericDatePicker("Select Date"));
        addRow(new JLabel("Enter end time:"), DateTimePickerFactory.createGenericDatePicker("Select Date"));
        addRow(new JLabel("Enter task type:"), buildWorkTypePicker());
        addConfirmButton();
        disableForm();
        return this;
    }

    public void fillForm(Object[] rowData) {
        // TODO: add functionality to fill the form
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
