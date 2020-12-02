package cz.muni.fi.pv168.freelancertimesheet.gui.popups.worktype.forms;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;
import cz.muni.fi.pv168.freelancertimesheet.gui.models.FormModel;

import javax.swing.*;

public class WorkTypeForm extends FormModel {

    private final JTextField nameTextField = new JTextField();
    private final JTextField rateTextField = new JTextField();
    private final JTextArea descriptionTextArea = new JTextArea();
    private final JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);

    public WorkTypeForm(){
        super();
        rateTextField.setText(Double.toString(0));
    }

    public static WorkTypeForm setup() {
        WorkTypeForm form = new WorkTypeForm();
        form
                .setupLayout()
                .setupVisuals()
                .setupNested();
        return form;
    }

    @Override
    public WorkTypeForm setupNested() {
        addRow(new JLabel("Name:"), nameTextField);
        addRow(new JLabel("Rate:"), rateTextField);
        JLabel descriptionLabel = new JLabel("Description:");
        addRow(descriptionLabel, descriptionScrollPane);
        addConfirmButton();
        return this;
    }

    public void fillForm(WorkType workType) {
        nameTextField.setText(workType.getName());
        rateTextField.setText(workType.getHourlyRate().toString());
        descriptionTextArea.setText(workType.getDescription());
        enableForm();
    }

    public void resetForm() {
        nameTextField.setText("");
        rateTextField.setText(Double.toString(0));
        descriptionTextArea.setText("");
        enableForm();
    }
}
